package Controllers;

import LMS.LearningManagementSystem;
import LMS.User;
import Services.PageNavigationService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class UserPageController {
    private final LearningManagementSystem LMS = LearningManagementSystem.getInstance(); // Initialize here
    private User currentUser ;

    @FXML
    private Button returnBtn;
    @FXML
    private Label userName, userID, userProgramOrDept, userYearLvl;

    @FXML
    public void initialize() {
        returnBtn.setOnAction(event -> PageNavigationService.navigateToPage(returnBtn, "home"));
        currentUser  = LMS.getCurrentUser();

        fillUserDetails();
    }

    private void fillUserDetails() {
        userName.setText(currentUser.toString());
        userID.setText(currentUser.getId());
        userProgramOrDept.setText("BSCS");
        userYearLvl.setText("1");
    }
}
