import DB.Database;
import DB.Type.GSONDB;
import LMS.User;
import Services.StageSetterService;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Blair extends Application {
    public static Database database;
    public static User mainUser;

    @Override
    public void start(Stage stage) {
        database = new GSONDB(getClass().getResource())
        try {
            // FOR DEBUGGING: You've GYATT to read this, replace the "Parent root = ..." line with
            // the lines of code enclosed in the comments to skip the splash screen and login thingy

            /* Line start -------

            // Skip the login page entirely, create the singleton LMS right away para lahos na sa page ur working on
            Student student = new Student("student1");
            student.setName("John", "Doe", "Smith");
            student.setEmail("john.doe@example.com");
            student.setCourses(new ArrayList<>());
            LearningManagementSystem lms = LearningManagementSystem.getInstance(null);
            lms.setCurrentUser(student);
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/course-page.fxml")); // Replace with fxml/page ur working on

            Line end ------- */

            Parent root = FXMLLoader.load(getClass().getResource("/fxml/splash-screen-page.fxml")); // Remove this line and replace it with the lines above para pang debug
            StageSetterService.setStage(stage, new Scene(root), "BLAIR");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        launch();
    }
}