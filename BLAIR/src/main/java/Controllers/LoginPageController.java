package Controllers;

import LMS.LearningManagementSystem;
import DB.Database;
import Services.DatabaseService;
import Services.PageNavigationService;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;

public class LoginPageController {
    @FXML
    private Button loginBtn;
    @FXML
    private ImageView imgRectangle;
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;

    @FXML
    public void initialize() {
        // This will add a smooth border radius to the ImageView kay css doesn't support border radius for ImageView
        Rectangle clip = new Rectangle();
        clip.setArcHeight(80);
        clip.setArcWidth(80);
        clip.setWidth(imgRectangle.getFitWidth());
        clip.setHeight(imgRectangle.getFitHeight());
        imgRectangle.setClip(clip);

        loginBtn.setOnAction(event -> handleLogin());
    }

    private void handleLogin() {
        // Sample Student user
//        Student student = new Student("student1");
//        student.setFullName("John", "Doe", "Smith");
//        student.setEmail("john.doe@example.com");
//        student.setCourses(new ArrayList<>());
//
//        LearningManagementSystem lms = LearningManagementSystem.getInstance(null);
//        lms.setCurrentUser(student); // Set the current user

//        PageNavigationService.navigateToPage(loginBtn, "home");

        /*
        String username = usernameField.getText();
        String password = passwordField.getText();

            Input validation logic here

           Example usage

           // First, check user inputs for type mismatches
           if (validateUserInput(username, password)) {

                // Next, check login details and validate
                User user = database.login(username, password);
                if (user != null) {
                    // If user validation succeeds, scene will now redirect to home page
                    PageNavigationService.navigateToPage(loginBtn, "home");
                }
           } else {
                 // Invalid user inputs. Prompt user to enter again.
                 // Naa toy error label na mu-show after ani, I will add it in lang later
            }

        */
        String username = usernameField.getText();
        String password = passwordField.getText();

        try {
            DatabaseService.checkDatabaseInitialization();
            LearningManagementSystem.getInstance(
                    Database.login(username, password)
            );
            PageNavigationService.navigateToPage(loginBtn, "home");
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }

    }
}