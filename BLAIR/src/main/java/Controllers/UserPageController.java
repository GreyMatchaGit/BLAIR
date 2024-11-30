package Controllers;

import DB.Database;
import LMS.LearningManagementSystem;
import LMS.User;
import Services.PageNavigationService;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.util.Duration;

import java.util.ArrayList;

import static java.util.Arrays.asList;

public class UserPageController {
    private LearningManagementSystem LMS = LearningManagementSystem.getInstance(null); // Initialize here
    private User currentUser ;

    @FXML
    private Button returnBtn;
    @FXML
    private Label userName, userID, userProgramOrDept, userYearLvl;

    @FXML
    private HBox option1, option2, option3, option4, option5;
    @FXML
    private HBox changePassOption, logoutOption;

    private ArrayList<HBox> options;

    @FXML
    public void initialize() {
        returnBtn.setOnAction(event -> PageNavigationService.navigateToPage(returnBtn, "home"));
        currentUser  = LMS.getCurrentUser ();

//        Uncomment later once user/student/teacher and db is finalized
//        if (currentUser instanceof Student) {
//            fillStudentDetails();
//        }
//        if (currentUser instanceof Teacher) {
//            fillTeacherDetails();
//        }

        userName.setText(currentUser.toString());
        userID.setText(currentUser.getId());

        options = new ArrayList<>(asList(option1, option2, option3, option4, option5, changePassOption, logoutOption));
        temporaryInitializer();
    }


    private void temporaryInitializer() {
        for (HBox option : options) {
            option.setOnMouseClicked(event -> expandOptionDetails(option));
        }
    }

    private void fillStudentDetails() {
        userName.setText(currentUser.toString());
        userID.setText(currentUser.getId());
    }

    private void fillTeacherDetails() {
        userName.setText(currentUser.toString());
        userID.setText(currentUser.getId());
    }

    @FXML
    private AnchorPane contentArea;

    private void expandOptionDetails(HBox option) {
        contentArea.getChildren().clear();

        Rectangle expandedSection = new Rectangle(500, 400);
        expandedSection.setArcHeight(95);
        expandedSection.setArcWidth(85);
        expandedSection.setFill(Paint.valueOf("#343a40"));

        double centerX = (1280 - expandedSection.getWidth()) / 2;
        double centerY = (650 - expandedSection.getHeight()) / 2;
        expandedSection.setX(centerX);
        expandedSection.setY(centerY);

        contentArea.getChildren().add(expandedSection);

        ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(200), expandedSection);
        scaleTransition.setFromX(1.0);
        scaleTransition.setFromY(1.0);
        scaleTransition.setToX(1.5);
        scaleTransition.setToY(1.2);

        scaleTransition.play();

        scaleTransition.setOnFinished(event -> {
            Button doneBtn = new Button("Done");
            doneBtn.setId("doneBtn");
            doneBtn.setPrefHeight(37);
            doneBtn.setPrefWidth(136);
            doneBtn.setStyle("-fx-background-radius: 20; -fx-background-color: #EDAA2E;");
            doneBtn.setCursor(Cursor.HAND);
            doneBtn.setFont(Font.font("Product Sans Black", 13));

            double buttonX = centerX + (expandedSection.getWidth() / 2) - doneBtn.getPrefWidth() / 2; // Centering the button
            double buttonY = centerY + (expandedSection.getHeight()) + doneBtn.getPrefHeight() + 10; // 10 pixels below the expanded section

            doneBtn.setLayoutX(buttonX);
            doneBtn.setLayoutY(buttonY);

            doneBtn.setOnAction(event1 -> PageNavigationService.navigateToPage(doneBtn, "user-profile"));

            contentArea.getChildren().add(doneBtn);
        });
    }
}
