import lms.LearningManagementSystem;
import lms.User;
import lms.usertype.Admin;
import services.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.ArrayList;

public class Blair extends Application {

    @Override
    public void start(Stage stage) {

        try {
            // Debugging purposes:
//            User currentUser = DatabaseService.login("admin", "123");
//            LearningManagementSystem
//                    .getInstance()
//                    .setCurrentUser(currentUser);
//
//            UserService.getInstance().setCurrentUser();
            //====================================================

            // TODO: Remove later
            // Temporary courses for every user that logs in.
//            TempService.userCourseSample();

            Parent root = FXMLLoader.load(getClass().getResource("/fxml/splash-screen-page.fxml"));
            StageSetterService.setStage(stage, new Scene(root), "BLAIR");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        launch();
    }
}