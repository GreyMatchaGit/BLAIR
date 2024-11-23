package Controllers;

import Services.PageNavigationService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class UserPageController {
    @FXML
    private Button returnBtn;

    @FXML
    public void initialize() {
        returnBtn.setOnAction(event -> PageNavigationService.navigateToPage(returnBtn, "home"));
    }
}
