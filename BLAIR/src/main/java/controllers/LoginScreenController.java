package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class LoginScreenController {
    private Stage stage;
    private Scene scene;
    private Parent root;

    public void switchToHomePage(ActionEvent event) {
        try {
            root = FXMLLoader.load(getClass().getResource("/fxml/home-page.fxml"));
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            SceneController.ChangeScene(stage, scene);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    // This thing is basically for clipping the image in the border radius thingy since
    // ImageViewer does not support smooth border radius
    @FXML
    private ImageView imgRectangle;

    @FXML
    public void initialize() {
        Rectangle clip = new Rectangle();
        clip.setArcHeight(80);
        clip.setArcWidth(80);
        clip.setWidth(340);
        clip.setHeight(550);

        imgRectangle.setClip(clip);
    }
}
