package controllers;

import services.DatabaseService;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import services.PageNavigationService;

import java.io.IOException;

public class AddStudentController {
    @FXML
    private Button submitBtn, backBtn;
    @FXML
    private TextField userID, firstName, middleName, lastName, userEmail, program, yearLvl;

    @FXML
    public void initialize() {
        backBtn.setOnAction(event -> PageNavigationService.navigateToPage(backBtn, "admin"));

        submitBtn.setOnMouseClicked(event -> {
            String id = userID.getText();
            String firstName = this.firstName.getText();
            String middleName = this.middleName.getText();
            String lastName = this.lastName.getText();
            String email = userEmail.getText();

            String program = this.program.getText();
            String yearLvl = this.yearLvl.getText();

            /* Todo:
                    - Create textfields for program and year level.
                    - Create the UI for adding students.
             */
            try {
                DatabaseService.registerStudent(id, firstName, middleName, lastName, email, "1", "BSCS");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
