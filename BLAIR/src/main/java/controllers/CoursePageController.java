package controllers;

import database.Database;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.effect.BoxBlur;
import javafx.scene.layout.Pane;
import lms.course.Course;
import lms.LearningManagementSystem;
import lms.usertype.Admin;
import lms.usertype.User;
import lms.usertype.Student;
import lms.usertype.Teacher;
import services.DatabaseService;
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
    private GridPane coursesGrid;
    @FXML
    private Button addCourse, addCourseBtn, cancelBtn;
    @FXML
    private Pane contentArea, addActivityPane, addCoursePane, courseTeacherPane;
    @FXML
    private TextField courseCode, courseDescription, courseKey, courseYear, courseTeacher;
    @FXML
    private VBox studentsVBox;

    private ArrayList<String> students, studentsToAdd;
    private boolean flag;

    @FXML
    public void initialize() {

        LearningManagementSystem lms = LearningManagementSystem.getInstance();
        User currentUser = lms.getCurrentUser();
        studentsToAdd = new ArrayList<>();
        flag = false;

        if (currentUser instanceof Student && !(currentUser instanceof Teacher)) {
            addCoursePane.setVisible(false);
        }

        if (!(currentUser instanceof Admin)) {
            courseTeacherPane.setVisible(false);
        }

        addCourse.setOnAction(event -> showAddCoursePane());

        addCourseBtn.setOnAction(event -> {
            addNewCourse(currentUser);
        });
        cancelBtn.setOnAction(event -> hideAddCoursePane());

        displayCourses(currentUser);
    }

    private void showAddCoursePane() {
        contentArea.setEffect(new BoxBlur());
        addActivityPane.setVisible(true);
        if (!flag) {
            initializeStudents();
            flag = true;
        }

        int i = 0;
        for (String student : students) {
            VBox studentCard = new VBox();
            int index = i;
            studentCard.setMinHeight(35);
            studentCard.setPrefWidth(450);
            studentCard.setMaxHeight(studentCard.getPrefHeight());
            studentCard.getStyleClass().add("student-card");
            studentCard.setCursor(Cursor.HAND);
            studentCard.setOnMouseClicked(event -> addStudent(student, index));

            Label studentName = new Label(student);
            studentName.getStyleClass().add("student-content");
            studentName.setMaxWidth(400);
            studentName.setWrapText(true);

            studentCard.getChildren().add(studentName);
            VBox.setMargin(studentCard, new Insets(10));

            i++;
            studentsVBox.getChildren().add(studentCard);
        }
    }

    private void hideAddCoursePane() {
        contentArea.setEffect(null);
        addActivityPane.setVisible(false);
        flag = false;
        courseCode.setText("");
        courseDescription.setText("");
        courseKey.setText("");
        courseYear.setText("");
        courseTeacher.setText("");
    }

    private void addStudent(String student, int index) {
        String firstName = student.split(" ")[0];

        studentsToAdd.add(firstName);

        if (index >= 0 && index < students.size()) {
            students.remove(index);
            studentsVBox.getChildren().clear();
            showAddCoursePane();
        }
    }

    private void addNewCourse(User user) {
        String code = courseCode.getText();
        String description = courseDescription.getText();
        String key = courseKey.getText();
        String year = courseYear.getText();

        String teacher = (user instanceof Admin) ? courseTeacher.getText() : user.getUsername();

        if (code.isEmpty() || description.isEmpty() || key.isEmpty() || year.isEmpty() || teacher.isEmpty()) {
            showAlert("Verify user inputs", "All fields are required");
        } else {
            DatabaseService.addCourse(code, description, key, year, teacher, studentsToAdd);
            showSuccessAlert();
            hideAddCoursePane();
            displayCourses(user);
        }
    }

    @FXML
    private void displayCourses(User user) {
        ArrayList<String> courses = UserService.getInstance().getCourses();
        coursesGrid.getChildren().clear();

        for (String code : courses) {
            Course c = Database.courseDatabase.get(code);
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

    private void initializeStudents() {
        students = DatabaseService.getStudentUsernames();
    }

    private void showSuccessAlert() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText("Course successfully added.");
        alert.setContentText(null);
        alert.showAndWait();
    }

    private void showAlert(String header, String content) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Warning");
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
