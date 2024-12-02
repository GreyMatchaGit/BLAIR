package Controllers;

import LMS.Calendar.CalendarActivity;
import com.calendarfx.model.CalendarSource;
import com.calendarfx.view.CalendarView;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.util.*;
import com.calendarfx.model.Calendar;
import javafx.stage.Stage;

public class NewCalendarPageController implements Initializable {
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

        Calendar katja = new Calendar("Katja");
        Calendar dirk = new Calendar("Dirk");
        Calendar philip = new Calendar("Philip");
        Calendar jule = new Calendar("Jule");
        Calendar armin = new Calendar("Armin");
        Calendar birthdays = new Calendar("Birthdays");
        Calendar holidays = new Calendar("Holidays");

        katja.setShortName("K");
        dirk.setShortName("D");
        philip.setShortName("P");
        jule.setShortName("J");
        armin.setShortName("A");
        birthdays.setShortName("B");
        holidays.setShortName("H");

        katja.setStyle(Calendar.Style.STYLE1);
        dirk.setStyle(Calendar.Style.STYLE2);
        philip.setStyle(Calendar.Style.STYLE3);
        jule.setStyle(Calendar.Style.STYLE4);
        armin.setStyle(Calendar.Style.STYLE5);
        birthdays.setStyle(Calendar.Style.STYLE6);
        holidays.setStyle(Calendar.Style.STYLE7);

        CalendarSource familyCalendarSource = new CalendarSource("Family");
        familyCalendarSource.getCalendars().addAll(birthdays, holidays, katja, dirk, philip, jule, armin);

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
