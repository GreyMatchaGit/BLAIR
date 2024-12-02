package Controllers;

import LMS.Calendar.CalendarActivity;
import Services.ColorSelectorService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.net.URL;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.List;

public class CalendarPageController implements Initializable {
    ZonedDateTime dateFocus;
    ZonedDateTime today;
    LocalDate dateSelected;
    List<CalendarActivity> calendarActs = new ArrayList<>();
    @FXML
    private Text year;
    @FXML
    private Text month;
    @FXML
    private FlowPane calendar;
    @FXML
    private AnchorPane apPopUp;
    @FXML
    private TextField tfAddEvent;
    @FXML
    private VBox vbEvent;
    @FXML
    private ScrollPane spEvent;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        dateFocus = ZonedDateTime.now();
        today = ZonedDateTime.now();
        drawCalendar();
    }

    @FXML
    void backOneMonth(ActionEvent event) {
        dateFocus = dateFocus.minusMonths(1);
        calendar.getChildren().clear();
        drawCalendar();
    }

    @FXML
    void forwardOneMonth(ActionEvent event) {
        dateFocus = dateFocus.plusMonths(1);
        calendar.getChildren().clear();
        drawCalendar();
    }

    public void closeAddEvent(ActionEvent actionEvent) {
        if (apPopUp.isVisible()) {
            apPopUp.setVisible(false);
        }
    }

    public void addEventPanel(ActionEvent actionEvent) {
        if (dateSelected != null) {
            calendarActs.add(new CalendarActivity(dateSelected, tfAddEvent.getText()));
            System.out.println("Calendar Activity added on " + dateSelected);
            calendar.getChildren().clear();
            drawCalendar();
            drawEventBoxes(dateSelected.getDayOfMonth());
        }
    }

    private void drawCalendar() {
        year.setText(String.valueOf(dateFocus.getYear()));
        month.setText(String.valueOf(dateFocus.getMonth()));

        double calendarWidth = calendar.getPrefWidth();
        double calendarHeight = calendar.getPrefHeight();
        double strokeWidth = 1;
        double spacingH = calendar.getHgap();
        double spacingV = calendar.getVgap();

        //List of activities for a given month
        Map<Integer, List<CalendarActivity>> calendarActivityMap = getCalendarActivitiesMonth(dateFocus);

        int monthMaxDate = dateFocus.getMonth().maxLength();
        //Check for leap year
        if (dateFocus.getYear() % 4 != 0 && monthMaxDate == 29) {
            monthMaxDate = 28;
        }
        int dateOffset = ZonedDateTime.of(dateFocus.getYear(), dateFocus.getMonthValue(), 1, 0, 0, 0, 0, dateFocus.getZone()).getDayOfWeek().getValue();

        double rectangleWidth = (calendarWidth / 7) - strokeWidth - spacingH;
        double rectangleHeight = (calendarHeight / 6) - strokeWidth - spacingV;
        double borderRadius = 10; // Adjust this value for desired rounding
        double textTranslationY = -(rectangleHeight / 2) * 0.73;
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                StackPane stackPane = new StackPane();

                Rectangle rectangle = new Rectangle();
                rectangle.setFill(Color.TRANSPARENT);
                rectangle.setStroke(Color.web("#91989E"));
                rectangle.setStrokeWidth(strokeWidth);

                rectangle.setWidth(rectangleWidth);

                rectangle.setHeight(rectangleHeight);
                stackPane.getChildren().add(rectangle);

                // Set border radius (rounded corners)
                rectangle.setArcWidth(borderRadius);
                rectangle.setArcHeight(borderRadius);

                int calculatedDate = (j + 1) + (7 * i);
                if (calculatedDate > dateOffset) {
                    int currentDate = calculatedDate - dateOffset;
                    if (currentDate <= monthMaxDate) {
                        //check if the rectangle is clicked twice
                        rectangle.setOnMouseClicked(event -> {
                            if (event.getClickCount() == 2) {
                                System.out.println("Rectangle was double-clicked!");
                                apPopUp.setVisible(true);
                                dateSelected = LocalDate.of(dateFocus.getYear(), dateFocus.getMonth(), currentDate);
                                System.out.println(dateSelected.toString());
                            } else {
                                apPopUp.setVisible(false);
                            }
//                            createEventBoxes(calendarActs, currentDate);
                              drawEventBoxes(currentDate);
                        });

                        Text date = new Text(String.valueOf(currentDate));
                        date.setTranslateY(textTranslationY);
                        date.setStroke(Color.WHITE);
                        List<CalendarActivity> calendarActivities = calendarActivityMap.get(currentDate);
                        if (calendarActivities != null) {
                            createCalendarActivity(calendarActivities, rectangleHeight, rectangleWidth, stackPane);
                        }
                        if (today.getYear() == dateFocus.getYear() && today.getMonth() == dateFocus.getMonth() && today.getDayOfMonth() == currentDate) {
//                            date.setStroke(Color.WHITE);
                            Circle redCircle = new Circle();
                            redCircle.setRadius(7);
                            redCircle.setFill(Color.RED);

                            redCircle.setTranslateY(textTranslationY);
                            stackPane.getChildren().add(redCircle);
                        }
                        stackPane.getChildren().add(date);
                    }

                }
                calendar.getChildren().add(stackPane);
            }
        }
    }

    private void createCalendarActivity(List<CalendarActivity> calendarActivities, double rectangleHeight, double rectangleWidth, StackPane stackPane) {
        VBox calendarActivityBox = new VBox();
        for (int k = 0; k < calendarActivities.size(); k++) {
            if (k >= 2) {
                Text moreActivities = new Text("...");
                calendarActivityBox.getChildren().add(moreActivities);
                moreActivities.setOnMouseClicked(mouseEvent -> {
                    //On ... click print all activities for given date
                    System.out.println(calendarActivities);
                });
                break;
            }
            Text text = new Text(calendarActivities.get(k).getDescription());
            calendarActivityBox.getChildren().add(text);
            text.setOnMouseClicked(mouseEvent -> {
                //On Text clicked
                System.out.println(text.getText());
            });
        }
        calendarActivityBox.setTranslateY((rectangleHeight / 2) * 0.20);
        calendarActivityBox.setMaxWidth(rectangleWidth * 0.8);
        calendarActivityBox.setMaxHeight(rectangleHeight * 0.65);
        calendarActivityBox.setStyle("-fx-background-color:GRAY");
        stackPane.getChildren().add(calendarActivityBox);
    }

    private Map<Integer, List<CalendarActivity>> createCalendarMap(List<CalendarActivity> calendarActivities) {
        Map<Integer, List<CalendarActivity>> calendarActivityMap = new HashMap<>();

        for (CalendarActivity activity : calendarActivities) {
            int activityDate = activity.getDate().getDayOfMonth();
            if (!calendarActivityMap.containsKey(activityDate)) {
                calendarActivityMap.put(activityDate, List.of(activity));
            } else {
                List<CalendarActivity> OldListByDate = calendarActivityMap.get(activityDate);

                List<CalendarActivity> newList = new ArrayList<>(OldListByDate);
                newList.add(activity);
                calendarActivityMap.put(activityDate, newList);
            }
        }
        return calendarActivityMap;
    }

    private Map<Integer, List<CalendarActivity>> getCalendarActivitiesMonth(ZonedDateTime dateFocus) {
        List<CalendarActivity> calendarActivities = new ArrayList<>();
        int year = dateFocus.getYear();
        int month = dateFocus.getMonth().getValue();
        System.out.println(month);
        for (CalendarActivity calendarActivity : calendarActs) {
            if (calendarActivity.getDate().getYear() == year && calendarActivity.getDate().getMonth().getValue() == month) {
                System.out.println("Calendar Act Month: " + calendarActivity.getDate().getMonth().getValue());
                calendarActivities.add(calendarActivity);
                System.out.println("Found " + calendarActivity);
            }
        }
        return createCalendarMap(calendarActivities);
    }

    private void drawEventBoxes (int day) {
        vbEvent.getChildren().clear(); // Clear existing cards

        if (calendarActs.isEmpty()) {
            javafx.scene.control.Label noEventsLabel = new javafx.scene.control.Label("No events to display.");
            noEventsLabel.setStyle("-fx-font-style: italic; -fx-text-fill: gray;");
            vbEvent.getChildren().add(noEventsLabel);
            return;
        }
        int year = dateFocus.getYear();
        int month = dateFocus.getMonth().getValue();
        int i = 1;
        for (CalendarActivity calendarActivity : calendarActs) {
            if (calendarActivity.getDate().getYear() == year && calendarActivity.getDate().getMonth().getValue() == month && calendarActivity.getDate().getDayOfMonth() == day) {
                VBox eventCard = new VBox();
                eventCard.setMinHeight(100);
                eventCard.setPrefWidth(250);
                eventCard.getStyleClass().add("post-card");

                javafx.scene.control.Label postAuthor = new javafx.scene.control.Label("Event " + i);
                postAuthor.getStyleClass().add("post-author");

                javafx.scene.control.Label postContentLabel = new javafx.scene.control.Label(calendarActivity.getDescription());
                postContentLabel.getStyleClass().add("post-content");
                postContentLabel.setWrapText(true);

                eventCard.getChildren().addAll(postAuthor, postContentLabel);
                VBox.setMargin(eventCard, new Insets(10));
                vbEvent.getChildren().add(eventCard);
                i++;
            }
        }
    }

//    private void createEventBoxes(List<CalendarActivity> calendarActivities, int day) {
//        vbEvent.getChildren().clear(); // Clear existing cards
//
//        if (calendarActivities.isEmpty()) {
//            javafx.scene.control.Label noEventsLabel = new javafx.scene.control.Label("No events to display.");
//            noEventsLabel.setStyle("-fx-font-style: italic; -fx-text-fill: gray;");
//            vbEvent.getChildren().add(noEventsLabel);
//            return;
//        }
//        int year = dateFocus.getYear();
//        int month = dateFocus.getMonth().getValue();
//        int i = 1;
//        for (CalendarActivity calendarActivity : calendarActivities) {
//            if (calendarActivity.getDate().getYear() == year && calendarActivity.getDate().getMonth().getValue() == month && calendarActivity.getDate().getDayOfMonth() == day) {
//                VBox eventCard = new VBox();
//                eventCard.setMinHeight(100);
//                eventCard.setPrefWidth(250);
//                eventCard.getStyleClass().add("post-card");
//
//                javafx.scene.control.Label postAuthor = new javafx.scene.control.Label("Event " + i);
//                postAuthor.getStyleClass().add("post-author");
//
//                javafx.scene.control.Label postContentLabel = new javafx.scene.control.Label(calendarActivity.getDescription());
//                postContentLabel.getStyleClass().add("post-content");
//                postContentLabel.setWrapText(true);
//
//                eventCard.getChildren().addAll(postAuthor, postContentLabel);
//                VBox.setMargin(eventCard, new Insets(10));
//                vbEvent.getChildren().add(eventCard);
//                i++;
//            }
//        }
//    }
}
