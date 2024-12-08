package services;

import controllers.InnerCoursePageController;
import controllers.UserPageController;
import lms.course.Course;
import lms.usertype.User;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

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

    public static void navigateToPage(Object sourceControl, String fxmlFile, Course course, User user) {
        try {
            InnerCoursePageController controller = new InnerCoursePageController();
            controller.setCourse(course);
            controller.setCurrentUser(user);

            FXMLLoader loader = new FXMLLoader(PageNavigationService.class.getResource("/fxml/" + fxmlFile + "-page.fxml"));
            loader.setController(controller);

            Stage currentStage = (Stage)((javafx.scene.Node) sourceControl).getScene().getWindow();
            Parent page = loader.load();
            currentStage.setScene(new Scene(page));
            currentStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Overloaded function to pass the current user
    public static void navigateToPage(Object sourceControl, String fxmlFile, User currentUser) {
        try {
            UserPageController controller = new UserPageController();

            FXMLLoader loader = new FXMLLoader(PageNavigationService.class.getResource("/fxml/" + fxmlFile + "-page.fxml"));
            loader.setController(controller);

            Stage currentStage = (Stage)((javafx.scene.Node) sourceControl).getScene().getWindow();
            Parent page = loader.load();
            currentStage.setScene(new Scene(page));
            currentStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}