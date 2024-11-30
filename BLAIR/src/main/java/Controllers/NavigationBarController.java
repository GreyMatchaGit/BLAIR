package Controllers;

import LMS.LearningManagementSystem;
import LMS.User;
import LMS.UserType.Admin;
import LMS.UserType.Teacher;
import Services.ButtonSelectionService;
import Services.PageNavigationService;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

public class NavigationBarController {
    @FXML
    private Button homeBtn, profileBtn, courseBtn, chatBtn, calendarBtn, quizBtn, adminBtn;

    @FXML
    private AnchorPane adminBanner, teacherBanner;
    @FXML
    private Button selButton;

    @FXML
    public void initialize() {
        User currentUser = LearningManagementSystem.getInstance().getCurrentUser();
        if (!(currentUser instanceof Admin)) {
            adminBtn.setVisible(false);
        }

        if (currentUser instanceof Admin) {
            adminBanner.setVisible(true);
        }

        if (currentUser instanceof Teacher) {
            teacherBanner.setVisible(true);
        }

        System.out.println("NavigationBarController initialized");

        // Restore previous selection or default to home
        String selectedButtonId = ButtonSelectionService.getInstance().getSelectedButtonId();
        if (selectedButtonId != null) {
            Button selectedButton = findButtonById(selectedButtonId);
            if (selectedButton != null) {
                highlightButton(selectedButton);
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
        selectedButton.setStyle("-fx-background-color: #656558; -fx-text-fill: white;");
        selButton = selectedButton;
        selButton.setDisable(true);

        // Save the currently selected button ID
        ButtonSelectionService.getInstance().setSelectedButtonId(selectedButton.getId());
    }

    private Button findButtonById(String buttonId) {
        switch (buttonId) {
            case "homeBtn":
                return homeBtn;
            case "profileBtn":
                return profileBtn;
            case "courseBtn":
                return courseBtn;
            case "chatBtn":
                return chatBtn;
            case "calendarBtn":
                return calendarBtn;
            case "quizBtn":
                return quizBtn;
            case "adminBtn":
                return adminBtn;
            default:
                return null;
        }
    }
}