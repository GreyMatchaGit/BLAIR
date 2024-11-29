package Controllers;

import LMS.LearningManagementSystem;
import LMS.User;
import LMS.UserType.Admin;
import LMS.UserType.Teacher;
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
    public void initialize() {
        User currentUser  = LearningManagementSystem.getInstance().getCurrentUser ();
        if (!(currentUser  instanceof Admin)) {
            adminBtn.setVisible(false);
        }

        if (currentUser  instanceof Admin) {
            adminBanner.setVisible(true);
        }

        if (currentUser  instanceof Teacher) {
            teacherBanner.setVisible(true);
        }

        homeBtn.getStyleClass().add("selected");

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
        Button[] buttons = {homeBtn, profileBtn, courseBtn, chatBtn, calendarBtn, quizBtn, adminBtn};

        for (Button button : buttons) {
            button.getStyleClass().remove("selected");
            button.setStyle("-fx-background-color: transparent;");
        }

        selectedButton.getStyleClass().add("selected");
        selectedButton.setStyle("-fx-background-color: #656558; -fx-text-fill: white;");
    }
}