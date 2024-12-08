package controllers;

import com.calendarfx.model.*;
import com.calendarfx.model.Calendar;
import com.calendarfx.view.CalendarView;
import database.Database;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.StackPane;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.*;

import lms.LearningManagementSystem;
import lms.User;
import lms.calendar.CustomEntry;
import lms.usertype.Admin;
import lms.usertype.Student;
import lms.usertype.Teacher;
import org.jetbrains.annotations.NotNull;
import services.DatabaseService;

public class CalendarPageController implements Initializable {
    @FXML
    private StackPane children;

    private Calendar schoolCalendar;
    private Calendar activities;
    private Calendar userCalendar;

    private ArrayList<String> entries = new ArrayList<>();

    LearningManagementSystem lms;
    User currentUser;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        lms = LearningManagementSystem.getInstance();
        currentUser = lms.getCurrentUser();

        CalendarView calendarView = new CalendarView();
        calendarView.setEnableTimeZoneSupport(true);

        calendarView.setShowAddCalendarButton(false);
        calendarView.setShowPrintButton(false);

        schoolCalendar = new Calendar("School Calendar");
        activities = new Calendar("Activities");
        userCalendar = new Calendar("User");

        schoolCalendar.setShortName("SC");
        activities.setShortName("A");
        userCalendar.setShortName("U");

        schoolCalendar.setStyle(Calendar.Style.STYLE1);
        activities.setStyle(Calendar.Style.STYLE2);
        userCalendar.setStyle(Calendar.Style.STYLE3);

        CalendarSource familyCalendarSource = new CalendarSource("Family");
        familyCalendarSource.getCalendars().addAll(schoolCalendar, activities, userCalendar);

        setCalendarVisibility(currentUser);

        calendarView.getCalendarSources().setAll(familyCalendarSource);
        calendarView.setRequestedTime(LocalTime.now());

        children.getChildren().addAll(calendarView); // introPane);

        EventHandler<CalendarEvent> handler = evt -> {
            saveEntryChanges (evt);
        };

        schoolCalendar.addEventHandler(handler);
        activities.addEventHandler(handler);
        userCalendar.addEventHandler(handler);

        Thread updateTimeThread = new Thread("Calendar: Update Time Thread") {
            @Override
            public void run() {
                while (true) {
                    Platform.runLater(() -> {
                        calendarView.setToday(LocalDate.now());
                        calendarView.setTime(LocalTime.now());
                    });

                    try {
                        // update every 10 seconds
                        sleep(10000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };

        updateTimeThread.setPriority(Thread.MIN_PRIORITY);
        updateTimeThread.setDaemon(true);
        updateTimeThread.start();

        initializeEntries(currentUser);
    }

    private void saveEntryChanges(CalendarEvent evt) {
        String id = evt.getEntry().getId();
        if (evt.isEntryRemoved()) {
            System.out.println("Removing entry: " + id);
            entries.remove(id);
            Database.calendarDatabase.remove(id);
        }
        else {
            System.out.println("Entry: " + evt.getEntry().getTitle());
            DatabaseService.addEntry(new CustomEntry(evt.getEntry()));
            if (!entries.contains(id)) {
                entries.add(evt.getEntry().getId());
            }
            saveEntries();
        }
    }

    private void setCalendarVisibility (@NotNull User user) {
        userCalendar.setReadOnly(false);
        activities.setReadOnly(true);
        schoolCalendar.setReadOnly(true);

        if (user instanceof Admin) {
            System.out.println("Admin using");
            schoolCalendar.setReadOnly(false);
            userCalendar.setReadOnly(true);
        }
        else if (user instanceof Teacher) {
            activities.setReadOnly(false);
        }
    }

    public void saveEntries () {
        System.out.println("Saved " + entries.size() + " new entries");
        currentUser.setEntries(entries);
        if (currentUser instanceof Admin) {
            HashMap<String, User> users = Database.userDatabase;
            for (User user : users.values()) {
                System.out.println(user.getUsername() + " " + user.getEntries().size());
                user.setEntries(entries, true);
            }
        }
    }

    public void initializeEntries(@NotNull User user) {
        ArrayList<String> keys = user.getEntries();

        if (user instanceof Admin) {
            User adminRef = Database.userDatabase.get("s");
            keys = new ArrayList<>(adminRef.getEntries()); // Make a copy
        }

        if (keys.isEmpty()) {
            System.out.println("User has no entries");
            return;
        }
        for (String key : keys) {
            CustomEntry entry = Database.calendarDatabase.get(key);
            if (entry != null) {
                if (user instanceof Admin && entry.getCalendar().replaceAll(".*name=(.*?),.*", "$1").equals("User")) {
                    continue;
                }

                String title = entry.getTitle();
                String id = entry.getId();
                LocalDate startDate = LocalDate.parse(entry.getStartDate());
                LocalTime startTime = LocalTime.parse(entry.getStartTime());
                LocalDate endDate = LocalDate.parse(entry.getEndDate());
                LocalTime endTime = LocalTime.parse(entry.getEndTime());
                ZoneId zoneId = ZoneId.of(entry.getZoneId());
                Entry<String> entryFx = new Entry<String>(title, new Interval(startDate, startTime, endDate, endTime, zoneId), id);
                entryFx.setRecurrenceRule(entry.getRecurrenceRule());
                entryFx.setFullDay(entry.isFullDay());

                switch (entry.getCalendar().replaceAll(".*name=(.*?),.*", "$1")) {
                    case "School Calendar":
                        schoolCalendar.addEntry(entryFx);
                        break;
                    case "Activities":
                        activities.addEntry(entryFx);
                        break;
                    case "User":
                        userCalendar.addEntry(entryFx);
                        break;
                }
            }
        }
    }
}
