package Services;

import javafx.animation.FadeTransition;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

public class PageNavigationService {
    public static void navigateToPage(Object sourceControl, String pageName) {
        try {
            Stage currentStage = (Stage)((javafx.scene.Node) sourceControl).getScene().getWindow();

            Parent page = FXMLLoader.load(PageNavigationService.class.getResource("/fxml/" + pageName + "-page.fxml"));

            Scene scene = new Scene(page);
            currentStage.setScene(scene);
            currentStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}