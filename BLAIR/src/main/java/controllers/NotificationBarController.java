package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import services.ButtonSelectionService;
import services.NotificationService;

public class NotificationBarController {

    @FXML
    private Button backBtn;

    @FXML
    private ScrollPane mainScrollPane;
    @FXML
    public void initialize() {

        setActionListeners();
    }

    public void setActionListeners() {

        backBtn.setOnMouseClicked(mouseEvent -> {
            NotificationService.closeBar();
        });
    }
}
