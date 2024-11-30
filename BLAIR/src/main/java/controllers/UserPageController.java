package controllers;

import database.Database;
import lms.Course;
import lms.LearningManagementSystem;
import lms.User;
import lms.usertype.Admin;
import services.PageNavigationService;
import javafx.animation.ScaleTransition;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.util.Duration;

import java.util.ArrayList;

import static javafx.scene.Cursor.HAND;

public class UserPageController {
    private final LearningManagementSystem LMS = LearningManagementSystem.getInstance(null); // Initialize here
    private User currentUser ;
    private ArrayList<String> courses;

    @FXML
    private Button returnBtn;
    @FXML
    private Label userName, userID;

    @FXML
    private HBox changePassOption, logoutOption;

    @FXML
    public void initialize() {
        returnBtn.setOnAction(event -> PageNavigationService.navigateToPage(returnBtn, "home"));
        currentUser = LMS.getCurrentUser ();

        if (currentUser instanceof Admin) {
            userName.setText("Admin");
            userID.setText("00-0000-000");
        } else {
            userName.setText(currentUser.toString());
            userID.setText(currentUser.getId());
        }

        changePassOption.setOnMouseClicked(event -> expandOptionDetails(changePassOption));
        logoutOption.setOnMouseClicked(event -> expandOptionDetails());

        courses = currentUser.getCourses();
        displayCourses();
    }

    @FXML
    private VBox coursesContainer;

    private void displayCourses() {
        coursesContainer.getChildren().clear();
        for (String code : courses) {
            Course c = Database.courseDatabase.get(code);
            Label courseLabel = new Label(c.getCode() + "   " + c.getDescription());
            courseLabel.setTextFill(Color.WHITE);
            courseLabel.setFont(Font.font("Product Sans", 15));
            courseLabel.setCursor(HAND);

            courseLabel.setOnMouseClicked(event -> PageNavigationService.navigateToPage(courseLabel, "expanded-course", c));

            courseLabel.setOnMouseEntered(event -> courseLabel.setStyle("-fx-text-fill: #EDAA2E;"));
            courseLabel.setOnMouseExited(event -> courseLabel.setStyle("-fx-text-fill: WHITE;"));

            coursesContainer.getChildren().add(courseLabel);
            VBox.setMargin(courseLabel, new javafx.geometry.Insets(5, 0, 5, 0));
        }
    }

    @FXML
    private AnchorPane contentArea;

    private void expandOptionDetails() {
        contentArea.getChildren().clear();

        Rectangle expandedSection = new Rectangle(350, 300);
        expandedSection.setArcHeight(120);
        expandedSection.setArcWidth(85);
        expandedSection.setFill(Paint.valueOf("#343a40"));

        ScaleTransition scaleTransition = scaleSection(expandedSection);
        expandedSection.setX((1280 - expandedSection.getWidth()) / 2);
        expandedSection.setY((650 - expandedSection.getHeight()) / 2);

        scaleTransition.setOnFinished(event -> {
            VBox vbox = new VBox(70);
            vbox.setAlignment(Pos.CENTER);

            Label logoutPrompt1 = new Label("Are you sure?");
            Label logoutPrompt2 = new Label(currentUser .getFirstName() + " will be logged out of BLAIR");
            logoutPrompt1.getStyleClass().add("logout-prompt1");
            logoutPrompt2.getStyleClass().add("logout-prompt2");

            Button cancelButton = new Button("Cancel");
            cancelButton.setCursor(Cursor.HAND);
            cancelButton.setPrefHeight(37);
            cancelButton.setPrefWidth(136);
            cancelButton.getStyleClass().add("cancel-button");

            Button logoutButton = new Button("Logout");
            logoutButton.setCursor(Cursor.HAND);
            logoutButton.setPrefHeight(37);
            logoutButton.setPrefWidth(136);
            logoutButton.getStyleClass().add("logout-button");

            cancelButton.setOnAction(e -> PageNavigationService.navigateToPage(cancelButton, "user-profile"));
            logoutButton.setOnAction(e -> PageNavigationService.navigateToPage(logoutButton, "login"));

            HBox buttonBox = new HBox(60, cancelButton, logoutButton);
            buttonBox.setAlignment(Pos.CENTER);

            vbox.getChildren().addAll(logoutPrompt1, logoutPrompt2, buttonBox);

            StackPane stackPane = new StackPane(vbox);
            stackPane.setPrefSize(expandedSection.getWidth(), expandedSection.getHeight());

            StackPane.setAlignment(vbox, Pos.CENTER);

            stackPane.setLayoutX(expandedSection.getX());
            stackPane.setLayoutY(expandedSection.getY());

            contentArea.getChildren().add(stackPane);
        });
    }

    // Todo: add other functionality for changing password
    private void expandOptionDetails(HBox option) {
        contentArea.getChildren().clear();

        Rectangle expandedSection = new Rectangle(500,400);
        expandedSection.setArcHeight(95);
        expandedSection.setArcWidth(85);
        expandedSection.setFill(Paint.valueOf("#343a40"));

        ScaleTransition scaleTransition = scaleSection(expandedSection);
        expandedSection.setX((1280 - expandedSection.getWidth()) / 2);
        expandedSection.setY((600 - expandedSection.getHeight()) / 2);

        scaleTransition.setOnFinished(event -> {
            Button doneBtn = new Button("Done");
            doneBtn.setId("doneBtn");
            doneBtn.setPrefHeight(37);
            doneBtn.setPrefWidth(136);
            doneBtn.setStyle("-fx-background-radius: 20; -fx-background-color: #EDAA2E;");
            doneBtn.setCursor(HAND);
            doneBtn.setFont(Font.font("Product Sans Black", 13));

            doneBtn.setLayoutX(573);
            doneBtn.setLayoutY(590);

            doneBtn.setOnAction(event1 -> PageNavigationService.navigateToPage(doneBtn, "user-profile"));

            contentArea.getChildren().add(doneBtn);
        });
    }

    private ScaleTransition scaleSection(Rectangle expandedSection) {
        contentArea.getChildren().add(expandedSection);

        ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(200), expandedSection);
        scaleTransition.setFromX(1.0);
        scaleTransition.setFromY(1.0);
        scaleTransition.setToX(1.5);
        scaleTransition.setToY(1.2);

        scaleTransition.play();

        return scaleTransition;
    }
}