package controllers;

import com.calendarfx.model.CalendarSource;
import com.calendarfx.view.CalendarView;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.StackPane;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import com.calendarfx.model.Calendar;

public class CalendarPageController implements Initializable {
//    @Override
//    public void start(Stage primaryStage) {
//
//    }
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
//        children.getChildren().addAll(calendarView);

        updateTimeThread.setPriority(Thread.MIN_PRIORITY);
        updateTimeThread.setDaemon(true);
        updateTimeThread.start();
    }
}
