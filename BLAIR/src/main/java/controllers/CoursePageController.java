package controllers;

import database.Database;
import lms.Course;
import lms.LearningManagementSystem;
import lms.User;
import services.PageNavigationService;
import javafx.animation.ScaleTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import services.UserService;

import java.util.ArrayList;

public class CoursePageController {

    @FXML
    public void initialize() {

        LearningManagementSystem lms = LearningManagementSystem.getInstance();
        User currentUser = lms.getCurrentUser();

        displayCourses(currentUser);
    }

    @FXML
    private GridPane coursesGrid;

    @FXML
    private void displayCourses(User user) {
        ArrayList<String> courses = UserService.getInstance().getCourses();
        coursesGrid.getChildren().clear();

        for (String code : courses) {
            Course c = Database.courseDatabase.get(code);
            System.out.println("Course Database: " + Database.courseDatabase);
            VBox courseCard = new VBox();
            courseCard.setPrefWidth(250);
            courseCard.setPrefHeight(150);
            courseCard.getStyleClass().add("course-card");

            Label courseCode = new Label(c.getCode());
            courseCode.getStyleClass().add("course-code");

            Label courseDescription = new Label(c.getDescription());
            courseDescription.getStyleClass().add("course-description");

            Label courseTeacher = new Label(c.getTeacher());
            courseTeacher.getStyleClass().add("course-teacher");

            courseCard.getChildren().addAll(courseCode, courseDescription, courseTeacher);

            VBox.setVgrow(courseDescription, Priority.ALWAYS);

            courseCard.setOnMouseClicked(event -> PageNavigationService.navigateToPage(courseCard, "expanded-course", c, user));
            coursesGrid.add(courseCard, coursesGrid.getChildren().size() % 4, coursesGrid.getChildren().size() / 4);

            // Tis for scale transition when mouse hovers over each course cards
            ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(200), courseCard);
            scaleTransition.setFromX(1);
            scaleTransition.setFromY(1);
            scaleTransition.setToX(1.1);
            scaleTransition.setToY(1.1);
            scaleTransition.setCycleCount(1);
            scaleTransition.setAutoReverse(true);

            courseCard.setOnMouseEntered(event -> scaleTransition.playFromStart());
            courseCard.setOnMouseExited(event -> {
                scaleTransition.setRate(-1);
                scaleTransition.play();
            });
        }
    }

}
