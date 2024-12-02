package controllers;

import services.DatabaseService;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import services.PageNavigationService;

import java.io.IOException;

public class AddTeacherController {
    @FXML
    private Button submitBtn, backBtn;
    @FXML
    private TextField userID, firstName, middleName, lastName, userEmail, department;

    @FXML
    public void initialize() {
        backBtn.setOnAction(event -> PageNavigationService.navigateToPage(backBtn, "admin"));

        submitBtn.setOnMouseClicked(event -> {
            String id = userID.getText();
            String firstName = this.firstName.getText();
            String middleName = this.middleName.getText();
            String lastName = this.lastName.getText();
            String email = userEmail.getText();

            String department = this.department.getText();

            try {
                DatabaseService.registerTeacher(id, firstName, middleName, lastName, email);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
