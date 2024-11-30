package controllers;

import database.Database;
import lms.Course;
import lms.LearningManagementSystem;
import lms.User;
import services.PageNavigationService;
import util.CourseBuilder;
import javafx.animation.ScaleTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import java.util.ArrayList;

public class CoursePageController {

    private void tempInitializer() {
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

        for (Course course : courses) {
            Database.courseDatabase.put(course.getCode(), course);
        }
    }

    @FXML
    public void initialize() {
        tempInitializer();
        LearningManagementSystem lms = LearningManagementSystem.getInstance();
        User currentUser  = lms.getCurrentUser ();

        ArrayList<String> userCourses = new ArrayList<>();

        // #debug
        // Simulating adding courses to a user
        userCourses.add("CSIT227");
        userCourses.add("CSIT104");
        userCourses.add("CSIT300");
        userCourses.add("CSIT401");
        userCourses.add("CSIT410");
        currentUser.setCourses(userCourses);

        displayCourses(currentUser );
    }

    @FXML
    private GridPane coursesGrid;

    @FXML
    private void displayCourses(User user) {
        ArrayList<String> courses = user.getCourses();
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

            courseCard.setOnMouseClicked(event -> PageNavigationService.navigateToPage(courseCard, "expanded-course", c));
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
