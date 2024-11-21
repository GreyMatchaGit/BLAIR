package controllers;

import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class SceneController {
    public static void ChangeScene(Stage stage, Scene scene) {
        stage.setTitle("BLAIR");
        stage.getIcons().add(new Image("media/Icon.png"));
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }
}
