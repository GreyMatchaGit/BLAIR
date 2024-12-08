package controllers;

import lms.LearningManagementSystem;
import lms.User;
import services.DatabaseService;
import services.PageNavigationService;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import services.TempService;
import services.UserService;

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
    private Label invalidLoginLbl;
    @FXML
    private Pane invalidPasswordPrompt, invalidUsernamePrompt;

    @FXML
    public void initialize() {

        // Course Database and User Database must be
        // initialized at the splash screen, before
        // the user reaches the login page.
        assert(DatabaseService.isInitialized());

        //TempService.courseInitialize();

        // This will add a smooth border radius to the ImageView kay css doesn't support border radius for ImageView
        Rectangle clip = new Rectangle();
        clip.setArcHeight(80);
        clip.setArcWidth(80);
        clip.setWidth(imgRectangle.getFitWidth());
        clip.setHeight(imgRectangle.getFitHeight());
        imgRectangle.setClip(clip);

        usernameField.setOnKeyTyped(event -> {
            if (!event.getCharacter().equals("\n")) {
                hideErrorPrompts();
            }
        });

        usernameField.setOnKeyReleased(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                handleLogin();
                event.consume();
            }
        });

        passwordField.setOnKeyTyped(event -> {
            if (!event.getCharacter().equals("\n")) {
                hideErrorPrompts();
            }
        });

        passwordField.setOnKeyReleased(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                handleLogin();
                event.consume();
            }
        });


        loginBtn.setOnAction(event -> handleLogin());
    }

    private void handleLogin() {

        String username = usernameField.getText();
        String password = passwordField.getText();

        if (!validateUserInput(username, password)) {
            return;
        }

        try {
            User currentUser = DatabaseService.login(username, password);
            LearningManagementSystem
                    .getInstance()
                    .setCurrentUser(currentUser);

            UserService.getInstance().setCurrentUser();

            // TODO: Remove later
            // Temporary courses for every user that logs in.
            //TempService.userCourseSample();

            PageNavigationService.navigateToPage(loginBtn, "home");
        } catch (RuntimeException e) {
            System.err.println(e.getMessage());
            invalidLoginLbl.setVisible(true);
        }
    }

    private void hideErrorPrompts() {
        invalidLoginLbl.setVisible(false);
        invalidPasswordPrompt.setVisible(false);
        invalidUsernamePrompt.setVisible(false);
    }

    private boolean validateUserInput(String username, String password) {
        if (username.isEmpty() && password.isEmpty()) {
            invalidPasswordPrompt.setVisible(true);
            invalidUsernamePrompt.setVisible(true);
            return false;
        } else if (username.isEmpty()) {
            invalidPasswordPrompt.setVisible(true);
            return false;
        } else if (password.isEmpty()){
            invalidUsernamePrompt.setVisible(true);
            return false;
        }
        return true;
    }
}