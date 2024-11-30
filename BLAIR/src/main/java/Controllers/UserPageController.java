package Controllers;

import DB.Database;
import LMS.Course;
import LMS.LearningManagementSystem;
import LMS.User;
import LMS.UserType.Admin;
import Services.PageNavigationService;
import Utilities.CourseBuilder;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.util.Duration;

import java.util.ArrayList;

import static java.util.Arrays.asList;

public class UserPageController {
    private LearningManagementSystem LMS = LearningManagementSystem.getInstance(null); // Initialize here
    private User currentUser ;
    private ArrayList<Course> courses;

    @FXML
    private Button returnBtn;
    @FXML
    private Label userName, userID;

    @FXML
    private HBox changePassOption, logoutOption;

    private ArrayList<Course> temporaryInitializer() {
        ArrayList<Course> courses = new ArrayList<>();

        courses.add(new CourseBuilder("CSIT227").setDescription("Object-Oriented Programming 1").create());
        courses.add(new CourseBuilder("CS231").setDescription("Discrete Structures 2").create());
        courses.add(new CourseBuilder("CSIT104").setDescription("Data Structures").create());
        courses.add(new CourseBuilder("CS243F1").setDescription("Computer Organization and Architecture").create());
        courses.add(new CourseBuilder("CSIT213").setDescription("Social Issues and Professional Practices").create());
        courses.add(new CourseBuilder("CSIT300").setDescription("Web Development").create());
        courses.add(new CourseBuilder("CSIT205").setDescription("Database Management Systems").create());
        courses.add(new CourseBuilder("CSIT310").setDescription("Software Engineering").create());
        courses.add(new CourseBuilder("CSIT220").setDescription("Operating Systems").create());
        courses.add(new CourseBuilder("CSIT240").setDescription("Computer Networks").create());
        courses.add(new CourseBuilder("CSIT400").setDescription("Machine Learning").create());
        courses.add(new CourseBuilder("CSIT401").setDescription("Artificial Intelligence").create());
        courses.add(new CourseBuilder("CSIT402").setDescription("Mobile Application Development").create());
        courses.add(new CourseBuilder("CSIT403").setDescription("Human-Computer Interaction").create());
        courses.add(new CourseBuilder("CSIT404").setDescription("Cloud Computing").create());
        courses.add(new CourseBuilder("CSIT405").setDescription("Game Development").create());
        courses.add(new CourseBuilder("CSIT406").setDescription("Cybersecurity Fundamentals").create());
        courses.add(new CourseBuilder("CSIT407").setDescription("Digital Signal Processing").create());
        courses.add(new CourseBuilder("CSIT408").setDescription("Compiler Design").create());
        courses.add(new CourseBuilder("CSIT409").setDescription("Computer Graphics").create());
        courses.add(new CourseBuilder("CSIT410").setDescription("Distributed Systems").create());
        courses.add(new CourseBuilder("CSIT411").setDescription("Internet of Things").create());
        courses.add(new CourseBuilder("CSIT412").setDescription("Blockchain Technology").create());
        courses.add(new CourseBuilder("CSIT413").setDescription("Data Mining").create());
        courses.add(new CourseBuilder("CSIT414").setDescription("Big Data Analytics").create());
        courses.add(new CourseBuilder("CSIT415").setDescription("Embedded Systems").create());
        courses.add(new CourseBuilder("CSIT416").setDescription("Information Retrieval").create());
        courses.add(new CourseBuilder("CSIT417").setDescription("Natural Language Processing").create());
        courses.add(new CourseBuilder("CSIT418").setDescription("Ethical Hacking").create());
        courses.add(new CourseBuilder("CSIT419").setDescription("Quantum Computing").create());
        courses.add(new CourseBuilder("CSIT420").setDescription("Augmented Reality").create());
        courses.add(new CourseBuilder("CSIT421").setDescription("Virtual Reality").create());

        return courses;
    }

    @FXML
    public void initialize() {
        returnBtn.setOnAction(event -> PageNavigationService.navigateToPage(returnBtn, "home"));
        currentUser  = LMS.getCurrentUser ();

        if (currentUser instanceof Admin) {
            userName.setText("Admin");
            userID.setText("00-0000-000");
        } else {
            userName.setText(currentUser.toString());
            userID.setText(currentUser.getId());
        }

        changePassOption.setOnMouseClicked(event -> expandOptionDetails(changePassOption));
        logoutOption.setOnMouseClicked(event -> expandOptionDetails(changePassOption));

        courses = temporaryInitializer();
        displayCourses();
    }

    @FXML
    private VBox coursesContainer;

    private void displayCourses() {
        coursesContainer.getChildren().clear();
        for (Course c : courses) {
            Label courseLabel = new Label(c.getCode() + "   " + c.getDescription());
            courseLabel.setTextFill(Color.WHITE);
            courseLabel.setFont(Font.font("Product Sans", 15));
            courseLabel.setCursor(Cursor.HAND);

            courseLabel.setOnMouseClicked(event -> {
                PageNavigationService.navigateToPage(courseLabel, "expanded-course", c);
            });

            courseLabel.setOnMouseEntered(event -> courseLabel.setStyle("-fx-text-fill: #EDAA2E;"));
            courseLabel.setOnMouseExited(event -> courseLabel.setStyle("-fx-text-fill: WHITE;"));

            coursesContainer.getChildren().add(courseLabel);
            VBox.setMargin(courseLabel, new javafx.geometry.Insets(5, 0, 5, 0));
        }
    }

    @FXML
    private AnchorPane contentArea;

    private void expandOptionDetails(HBox option) {
        contentArea.getChildren().clear();

        Rectangle expandedSection = new Rectangle(500, 400);
        expandedSection.setArcHeight(95);
        expandedSection.setArcWidth(85);
        expandedSection.setFill(Paint.valueOf("#343a40"));

        double centerX = (1280 - expandedSection.getWidth()) / 2;
        double centerY = (600 - expandedSection.getHeight()) / 2;
        expandedSection.setX(centerX);
        expandedSection.setY(centerY);

        contentArea.getChildren().add(expandedSection);

        ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(200), expandedSection);
        scaleTransition.setFromX(1.0);
        scaleTransition.setFromY(1.0);
        scaleTransition.setToX(1.5);
        scaleTransition.setToY(1.2);

        scaleTransition.play();

        scaleTransition.setOnFinished(event -> {
            Button doneBtn = new Button("Done");
            doneBtn.setId("doneBtn");
            doneBtn.setPrefHeight(37);
            doneBtn.setPrefWidth(136);
            doneBtn.setStyle("-fx-background-radius: 20; -fx-background-color: #EDAA2E;");
            doneBtn.setCursor(Cursor.HAND);
            doneBtn.setFont(Font.font("Product Sans Black", 13));

            doneBtn.setLayoutX(573);
            doneBtn.setLayoutY(590);

            doneBtn.setOnAction(event1 -> PageNavigationService.navigateToPage(doneBtn, "user-profile"));

            contentArea.getChildren().add(doneBtn);
        });
    }
}