package controllers;

import javafx.scene.paint.Color;
import lms.LearningManagementSystem;
import lms.User;
import lms.usertype.Admin;
import lms.usertype.Teacher;
import services.ButtonSelectionService;
import services.PageNavigationService;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.shape.Rectangle;

public class NavigationBarController {
    @FXML
    private Button homeBtn, profileBtn, courseBtn, chatBtn, calendarBtn, quizBtn, adminBtn;

    @FXML
    private Button selButton;

    @FXML
    private Rectangle userTypeBox;
    @FXML
    private Label userTypeLbl;

    @FXML
    public void initialize() {
        User currentUser = LearningManagementSystem.getInstance().getCurrentUser();

        if (!(currentUser instanceof Admin)) {
            adminBtn.setVisible(false);
        }

        if (currentUser  instanceof Admin) {
            userTypeLbl.setText("Admin");
            userTypeBox.setFill(Color.web("#697478"));
        } else if (currentUser  instanceof Teacher) {
            userTypeLbl.setText("Teacher");
            userTypeBox.setFill(Color.web("#FFC107"));
            userTypeLbl.setTextFill(Color.web("#343A40"));
        } else {
            userTypeLbl.setText("Student");
            userTypeBox.setFill(Color.web("#af4342"));
        }

        // Restore previous selection or default to home
        String selectedButtonId = ButtonSelectionService.getInstance().getSelectedButtonId();
        if (selectedButtonId != null) {
            Button selectedButton = findButtonById(selectedButtonId);
            if (selectedButton != null) {
                if (selectedButton == profileBtn) {
                    highlightButton(homeBtn);
                } else {
                    highlightButton(selectedButton);
                }
            }
        } else {
            highlightButton(homeBtn);
        }

        homeBtn.setOnAction(event -> {
            highlightButton(homeBtn);
            PageNavigationService.navigateToPage(homeBtn, "home");
        });
        courseBtn.setOnAction(event -> {
            highlightButton(courseBtn);
            PageNavigationService.navigateToPage(courseBtn, "course");
        });
        profileBtn.setOnAction(event -> {
            highlightButton(profileBtn);
            PageNavigationService.navigateToPage(profileBtn, "user-profile");
        });
        chatBtn.setOnAction(event -> {
            highlightButton(chatBtn);
            PageNavigationService.navigateToPage(chatBtn, "chat");
        });
        calendarBtn.setOnAction(event -> {
            highlightButton(calendarBtn);
            PageNavigationService.navigateToPage(calendarBtn, "calendar");
        });
        quizBtn.setOnAction(event -> {
            highlightButton(quizBtn);
            PageNavigationService.navigateToPage(quizBtn, "quizzler");
        });
        adminBtn.setOnAction(event -> {
            highlightButton(adminBtn);
            PageNavigationService.navigateToPage(adminBtn, "admin");
        });
    }

    private void highlightButton(Button selectedButton) {
        if (selButton != null) {
            selButton.getStyleClass().remove("selected");
            selButton.setStyle("-fx-background-color: transparent;");
        }

        selectedButton.getStyleClass().add("selected");
        if (selectedButton == calendarBtn) {
            selectedButton.setStyle("-fx-background-color: #656558; -fx-text-fill: white; -fx-padding: 0");
        } else {
            selectedButton.setStyle("-fx-background-color: #656558; -fx-text-fill: white;");
        }

        selButton = selectedButton;

        // Save the currently selected button ID
        ButtonSelectionService.getInstance().setSelectedButtonId(selectedButton.getId());
    }

    private Button findButtonById(String buttonId) {
        return switch (buttonId) {
            case "homeBtn" -> homeBtn;
            case "profileBtn" -> profileBtn;
            case "courseBtn" -> courseBtn;
            case "chatBtn" -> chatBtn;
            case "calendarBtn" -> calendarBtn;
            case "quizBtn" -> quizBtn;
            case "adminBtn" -> adminBtn;
            default -> null;
        };
    }
}