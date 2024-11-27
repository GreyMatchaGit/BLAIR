package Controllers;

import Services.PageNavigationService;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class AdminPageController {
    @FXML
    private Button addStudentBtn;
    @FXML
    private Button addTeacherBtn;

    @FXML
    public void initialize() {

        addStudentBtn.setOnAction(actionEvent -> PageNavigationService.navigateToPage(addStudentBtn, "add-student"));
        addTeacherBtn.setOnAction( actionEvent -> PageNavigationService.navigateToPage(addTeacherBtn, "add-teacher"));
    }
}
