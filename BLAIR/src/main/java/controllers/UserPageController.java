package controllers;

import database.Database;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import lms.course.Course;
import lms.LearningManagementSystem;
import lms.usertype.User;
import lms.usertype.Admin;
import lms.usertype.Student;
import lms.usertype.Teacher;
import services.ButtonSelectionService;
import services.DatabaseService;
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
import services.UserService;

import java.util.ArrayList;

import static javafx.scene.Cursor.HAND;

public class UserPageController {

    private final LearningManagementSystem LMS = LearningManagementSystem.getInstance(); // Initialize here
    private User currentUser ;
    private ArrayList<String> courses;

    @FXML
    private Button returnBtn;
    @FXML
    private Label userName, userID, userEmail, yearLvl, yearLvlLabel;

    @FXML
    private HBox changePassOption, logoutOption;

    @FXML
    public void initialize() {

        returnBtn.setOnAction(event -> {
            ButtonSelectionService.getInstance().setSelectedButtonId(null);
            PageNavigationService.navigateToPage(returnBtn, "home");
        });
        currentUser = LMS.getCurrentUser();

        if (currentUser instanceof Admin) {
            userName.setText("Admin");
            userID.setText("00-0000-000");
            userEmail.setText("admin@cit.edu");
            yearLvl.setText("0");
        } else {
            userName.setText(currentUser.toString());
            userID.setText(currentUser.getId());
            userEmail.setText(currentUser.getEmail());
            if (currentUser instanceof Teacher) {
                yearLvl.setVisible(false);
                yearLvlLabel.setVisible(false);
            } else {
                yearLvl.setText(((Student)currentUser).getYearLevel());
            }
        }

        changePassOption.setOnMouseClicked(event -> expandChangePassOption());
        logoutOption.setOnMouseClicked(event -> expandLogoutOption());

        courses = UserService.getInstance().getCourses();
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

            courseLabel.setOnMouseClicked(event -> PageNavigationService.navigateToPage(courseLabel, "expanded-course", c, currentUser));

            courseLabel.setOnMouseEntered(event -> courseLabel.setStyle("-fx-text-fill: #EDAA2E;"));
            courseLabel.setOnMouseExited(event -> courseLabel.setStyle("-fx-text-fill: WHITE;"));

            coursesContainer.getChildren().add(courseLabel);
            VBox.setMargin(courseLabel, new javafx.geometry.Insets(5, 0, 5, 0));
        }
    }

    @FXML
    private AnchorPane contentArea;

    private void expandLogoutOption() {
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
            logoutButton.setOnAction(e -> {
                PageNavigationService.navigateToPage(logoutButton, "login");
                ButtonSelectionService.getInstance().setSelectedButtonId(null);
                DatabaseService.update();
            });

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

    private void expandChangePassOption() {
        contentArea.getChildren().clear();

        Rectangle expandedSection = new Rectangle(415, 400);
        expandedSection.setArcHeight(95);
        expandedSection.setArcWidth(85);
        expandedSection.setFill(Paint.valueOf("#343a40"));

        ScaleTransition scaleTransition = scaleSection(expandedSection);
        expandedSection.setX((1280 - expandedSection.getWidth()) / 2);
        expandedSection.setY((600 - expandedSection.getHeight()) / 2);

        scaleTransition.setOnFinished(event -> {
            Label currentPasswordLabel = new Label("Enter current password:");
            currentPasswordLabel.getStyleClass().add("password-label");
            PasswordField currentPasswordField = new PasswordField();
            currentPasswordField.getStyleClass().add("password-field");

            Label newPasswordLabel = new Label("Enter new password:");
            newPasswordLabel.getStyleClass().add("password-label");
            PasswordField newPasswordField = new PasswordField();
            newPasswordField.getStyleClass().add("password-field");

            Label confirmPasswordLabel = new Label("Confirm new password:");
            confirmPasswordLabel.getStyleClass().add("password-label");
            PasswordField confirmPasswordField = new PasswordField();
            confirmPasswordField.getStyleClass().add("password-field");

            Button saveBtn = new Button("Save");
            saveBtn.getStyleClass().add("save-button");

            double leftDist = 490;
            currentPasswordLabel.setLayoutX(leftDist);
            currentPasswordLabel.setLayoutY(110);
            currentPasswordField.setLayoutX(leftDist);
            currentPasswordField.setLayoutY(140);

            newPasswordLabel.setLayoutX(leftDist);
            newPasswordLabel.setLayoutY(240);
            newPasswordField.setLayoutX(leftDist);
            newPasswordField.setLayoutY(270);

            confirmPasswordLabel.setLayoutX(leftDist);
            confirmPasswordLabel.setLayoutY(320);
            confirmPasswordField.setLayoutX(leftDist);
            confirmPasswordField.setLayoutY(350);

            saveBtn.setLayoutX(573);
            saveBtn.setLayoutY(450);

            saveBtn.setOnAction(event1 -> {
                String currentPassword = currentPasswordField.getText();
                String newPassword = newPasswordField.getText();
                String confirmPassword = confirmPasswordField.getText();

                try {
                    DatabaseService.validatePassword(
                            currentUser.getUsername(),
                            currentPassword
                    );

                    if (newPassword.equals(confirmPassword)) {
                        DatabaseService.changePassword(
                                currentUser.getUsername(),
                                newPassword
                        );
                        showSuccessAlert();
                    } else {
                        showAlert("Verify inputs and try again", "Password and confirm password do not match!");
                    }
                } catch (RuntimeException e) {
                    showAlert("Verify inputs and try again", "Password is incorrect!");
                }

                DatabaseService.update();
            });


            Button doneBtn = new Button("Done");
            doneBtn.setId("doneBtn");
            doneBtn.setPrefHeight(37);
            doneBtn.setPrefWidth(136);
            doneBtn.setStyle("-fx-background-radius: 20; -fx-background-color: #EDAA2E;");
            doneBtn.setCursor(HAND);
            doneBtn.setFont(Font.font("Product Sans Black", 13));

            doneBtn.setLayoutX(573);
            doneBtn.setLayoutY(590);

            doneBtn.setOnAction(event2 -> PageNavigationService.navigateToPage(doneBtn, "user-profile"));

            contentArea.getChildren().addAll(
                    currentPasswordLabel, currentPasswordField,
                    newPasswordLabel, newPasswordField,
                    confirmPasswordLabel, confirmPasswordField,
                    saveBtn, doneBtn
            );
        });
    }

    private void showSuccessAlert() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText("Password Changed Successfully");
        alert.setContentText("Your password has been changed successfully!");
        alert.showAndWait();
    }

    private void showAlert(String header, String content) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Warning");
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
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