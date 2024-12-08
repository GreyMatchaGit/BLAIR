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
import lms.content.todolist.Task;
import lms.usertype.Student;
import org.jetbrains.annotations.NotNull;
import services.DatabaseService;

public class CalendarPageController implements Initializable {
    @FXML
    private StackPane children;

    private Calendar schoolEvents = new Calendar("School Events");
    private Calendar tasks = new Calendar("Tasks");
    private Calendar noClasses = new Calendar("No Classes");

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

        schoolEvents.setShortName("S");
        tasks.setShortName("T");
        noClasses.setShortName("NC");

        schoolEvents.setStyle(Calendar.Style.STYLE1);
        tasks.setStyle(Calendar.Style.STYLE2);
        noClasses.setStyle(Calendar.Style.STYLE3);

        CalendarSource familyCalendarSource = new CalendarSource("Family");
        familyCalendarSource.getCalendars().addAll(schoolEvents, tasks, noClasses);

        calendarView.getCalendarSources().setAll(familyCalendarSource);
        calendarView.setRequestedTime(LocalTime.now());

        children.getChildren().addAll(calendarView); // introPane);

        EventHandler<CalendarEvent> handler = evt -> {
            saveEntryChanges (evt);
        };

        schoolEvents.addEventHandler(handler);
        tasks.addEventHandler(handler);
        noClasses.addEventHandler(handler);

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

    public void saveEntries () {
        System.out.println("Saved " + entries.size() + " new entries");
        ((Student)currentUser).setEntries(entries);
    }

    public void initializeEntries(@NotNull User user) {
        ArrayList<String> keys = ((Student) user).getEntries();
        if (keys.isEmpty()) {
            System.out.println("User has no entries");
            return;
        }
        for (String key : keys) {
            CustomEntry entry = Database.calendarDatabase.get(key);
            if (entry != null) {
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
                    case "School Events":
                        schoolEvents.addEntry(entryFx);
                        break;
                    case "Tasks":
                        tasks.addEntry(entryFx);
                        break;
                    case "No Classes":
                        noClasses.addEntry(entryFx);
                        break;
                }
            }
        }
    }
}
