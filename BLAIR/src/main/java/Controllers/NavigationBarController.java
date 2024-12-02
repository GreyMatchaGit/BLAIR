package Controllers;

import LMS.LearningManagementSystem;
import LMS.User;
import LMS.UserType.Admin;
import Services.PageNavigationService;
import Services.StageSetterService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class NavigationBarController {
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
    @FXML
    private Button adminBtn;

    @FXML
    public void initialize() {
        User currentUser = LearningManagementSystem.getInstance().getCurrentUser();
        if (!(currentUser instanceof Admin))
            adminBtn.setVisible(false);

        courseBtn.setOnAction(event -> PageNavigationService.navigateToPage(courseBtn, "course"));
        profileBtn.setOnAction(event -> PageNavigationService.navigateToPage(profileBtn, "user-profile"));
        chatBtn.setOnAction(event -> PageNavigationService.navigateToPage(chatBtn, "chat"));
        calendarBtn.setOnAction(event -> PageNavigationService.navigateToPage(calendarBtn, "calendarfx"));
        quizBtn.setOnAction(event -> PageNavigationService.navigateToPage(quizBtn, "quizzler"));
        adminBtn.setOnAction(event -> PageNavigationService.navigateToPage(adminBtn, "admin"));
    }
}
