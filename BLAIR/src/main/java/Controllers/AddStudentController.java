package Controllers;

import Services.DatabaseService;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.io.IOException;

public class AddStudentController {
    @FXML
    private Button submitBtn;
    @FXML
    private TextField idTF;
    @FXML
    private TextField firstNameTF;
    @FXML
    private TextField middleNameTF;
    @FXML
    private TextField lastNameTF;
    @FXML
    private TextField emailTF;

    @FXML
    public void initialize() {
        submitBtn.setOnMouseClicked(event -> {
            String id = idTF.getText();
            String firstName = firstNameTF.getText();
            String middleName = middleNameTF.getText();
            String lastName = lastNameTF.getText();
            String email = emailTF.getText();

            try {
                DatabaseService.registerStudent(id, firstName, middleName, lastName, email);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
