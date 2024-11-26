package Services;

import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class StageSetterService {
    public static void setStage(Stage stage, Scene scene, String title) {
        stage.setTitle(title);
        stage.getIcons().add(new Image("media/Icon.png"));
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }
}
