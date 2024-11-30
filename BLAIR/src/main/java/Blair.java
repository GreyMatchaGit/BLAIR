import DB.Database;
import DB.Type.GSONDB;
import LMS.LearningManagementSystem;
import LMS.User;
import LMS.UserType.Admin;
import LMS.UserType.Student;
import Services.StageSetterService;
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
            // #debug
            Admin student = new Admin();
            student.setCourses(new ArrayList<>());
            LearningManagementSystem lms = LearningManagementSystem.getInstance(null);
            lms.setCurrentUser(student);
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/user-profile-page.fxml")); // Replace with fxml/page ur working on

//            Parent root = FXMLLoader.load(getClass().getResource("/fxml/splash-screen-page.fxml"));

            StageSetterService.setStage(stage, new Scene(root), "BLAIR");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        launch();
    }
}