package controllers;

import com.calendarfx.model.CalendarEvent;
import com.calendarfx.model.CalendarSource;
import com.calendarfx.model.Entry;
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
import java.util.*;
import com.calendarfx.model.Calendar;
import lms.User;
import services.DatabaseService;

public class CalendarPageController implements Initializable {
    @FXML
    private StackPane children;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        CalendarView calendarView = new CalendarView();
        calendarView.setEnableTimeZoneSupport(true);

        calendarView.setShowAddCalendarButton(false);
        calendarView.setShowPrintButton(false);
        Calendar schoolEvents = new Calendar("School Events");
        Calendar tasks = new Calendar("Tasks");
        Calendar noClasses = new Calendar("No Classes");

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
    
    }
    private void saveEntryChanges(CalendarEvent evt) {
        DatabaseService.addEntry(evt.getEntry());
        System.out.println("Entry added in school events:" + evt.getEntry().getCalendar());
    }
}
