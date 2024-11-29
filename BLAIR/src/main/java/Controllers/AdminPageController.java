package Controllers;

import DB.Type.GSONDB;
import Services.PageNavigationService;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.io.IOException;

public class AdminPageController {
    @FXML
    private Button addStudentBtn;
    @FXML
    private Button addTeacherBtn;
    @FXML
    private Button updateBtn;

    @FXML
    public void initialize() {

        addStudentBtn.setOnAction(actionEvent -> PageNavigationService.navigateToPage(addStudentBtn, "add-student"));
        addTeacherBtn.setOnAction( actionEvent -> PageNavigationService.navigateToPage(addTeacherBtn, "add-teacher"));
        updateBtn.setOnAction(actionEvent -> {
            try {
                GSONDB.updateDatabase();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
