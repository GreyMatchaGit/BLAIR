package Controllers;

import DB.Database;
import LMS.Course;
import LMS.LearningManagementSystem;
import LMS.User;
import LMS.UserType.Student;
import Services.PageNavigationService;
import javafx.animation.ScaleTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import java.util.ArrayList;

public class CoursePageController {
    @FXML
    private Button homeBtn;
    @FXML
    private Button profileBtn;
    @FXML
    private Button courseBtn;
    @FXML
    private Button chatBtn;
    @FXML
    private Button calendarBtn;
    @FXML
    private Button quizBtn;

    // Temporary courses initializer to simulate user with different number of courses
    private ArrayList<Course> temporaryInitializeStudentCourses1(User currentUser ) {
        ArrayList<Course> courses = new ArrayList<>();

        courses.add(new Course("Object-Oriented Programming 1", "CSIT227"));
        courses.add(new Course("Discrete Structures 2", "CS231"));
        courses.add(new Course("Data Structures", "CSIT104"));

        return courses;
    }
    private ArrayList<Course> temporaryInitializeStudentCourses2(User currentUser ) {
        ArrayList<Course> courses = new ArrayList<>();

        courses.add(new Course("Object-Oriented Programming 1", "CSIT227"));
        courses.add(new Course("Discrete Structures 2", "CS231"));
        courses.add(new Course("Data Structures", "CSIT104"));
        courses.add(new Course("Computer Organization and Architecture", "CS243F1"));
        courses.add(new Course("Social Issues and Professional Practices", "CSIT213"));
        courses.add(new Course("Object-Oriented Programming 1", "CSIT227"));
        courses.add(new Course("Discrete Structures 2", "CS231"));
        courses.add(new Course("Data Structures", "CSIT104"));
        courses.add(new Course("Computer Organization and Architecture", "CS243F1"));
        courses.add(new Course("Social Issues and Professional Practices", "CSIT213"));
        courses.add(new Course("Web Development", "CSIT300"));
        courses.add(new Course("Database Management Systems", "CSIT205"));
        courses.add(new Course("Software Engineering", "CSIT310"));
        courses.add(new Course("Operating Systems", "CSIT220"));
        courses.add(new Course("Computer Networks", "CSIT240"));
        courses.add(new Course("Machine Learning", "CSIT400"));
        courses.add(new Course("Artificial Intelligence", "CSIT401"));
        courses.add(new Course("Mobile Application Development", "CSIT402"));
        courses.add(new Course("Human-Computer Interaction", "CSIT403"));
        courses.add(new Course("Cloud Computing", "CSIT404"));
        courses.add(new Course("Game Development", "CSIT405"));
        courses.add(new Course("Cybersecurity Fundamentals", "CSIT406"));
        courses.add(new Course("Digital Signal Processing", "CSIT407"));
        courses.add(new Course("Compiler Design", "CSIT408"));
        courses.add(new Course("Computer Graphics", "CSIT409"));
        courses.add(new Course("Distributed Systems", "CSIT410"));
        courses.add(new Course("Internet of Things", "CSIT411"));
        courses.add(new Course("Blockchain Technology", "CSIT412"));
        courses.add(new Course("Data Mining", "CSIT413"));
        courses.add(new Course("Big Data Analytics", "CSIT414"));
        courses.add(new Course("Embedded Systems", "CSIT415"));
        courses.add(new Course("Information Retrieval", "CSIT416"));
        courses.add(new Course("Natural Language Processing", "CSIT417"));
        courses.add(new Course("Ethical Hacking", "CSIT418"));
        courses.add(new Course("Quantum Computing", "CSIT419"));
        courses.add(new Course("Augmented Reality", "CSIT420"));
        courses.add(new Course("Virtual Reality", "CSIT421"));

        return courses;
    }

    @FXML
    public void initialize() {
        courseBtn.getStyleClass().add("disabled");
        LearningManagementSystem lms = LearningManagementSystem.getInstance();
        Database db = Database.getInstance();
        User currentUser  = new Student("123");

        // User with 2 courses
        // currentUser.setCourses(temporaryInitializeStudentCourses1(currentUser));

        // User with 30 courses, to check the behavior of scroll pane
        currentUser.setCourses(temporaryInitializeStudentCourses2(currentUser));

        homeBtn.setOnAction(event -> PageNavigationService.navigateToPage(homeBtn, "home"));
        profileBtn.setOnAction(event -> PageNavigationService.navigateToPage(profileBtn, "user-profile"));
        chatBtn.setOnAction(event -> PageNavigationService.navigateToPage(chatBtn, "chat"));
        calendarBtn.setOnAction(event -> PageNavigationService.navigateToPage(calendarBtn, "calendar"));
        quizBtn.setOnAction(event -> PageNavigationService.navigateToPage(quizBtn, "quizzler"));

        displayCourses(currentUser);
    }

    @FXML
    private GridPane coursesGrid;

    @FXML
    private void displayCourses(User user) {
        ArrayList<Course> courses = user.getCourses();
        coursesGrid.getChildren().clear();

        for (Course c : courses) {
            VBox courseCard = new VBox();
            courseCard.setPrefWidth(250);
            courseCard.setPrefHeight(150);
            courseCard.getStyleClass().add("course-card");

            Label courseCode = new Label(c.getCode());
            courseCode.getStyleClass().add("course-code");

            Label courseDescription = new Label(c.getDescription());
            courseDescription.getStyleClass().add("course-description");

            // Temporarily set all teachers to ser serats
            Label courseProfessor = new Label("Mr. Jay Vince D. Serato");
            courseProfessor.getStyleClass().add("course-professor");

            courseCard.getChildren().addAll(courseCode, courseDescription, courseProfessor);

            VBox.setVgrow(courseDescription, Priority.ALWAYS);

            courseCard.setOnMouseClicked(event -> {
                PageNavigationService.navigateToPage(courseCard, "expanded-course", c);
            });
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
