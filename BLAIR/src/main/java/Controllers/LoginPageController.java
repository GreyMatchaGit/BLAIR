package Controllers;

import LMS.LearningManagementSystem;
import DB.Database;
import Services.DatabaseService;
import Services.PageNavigationService;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
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
    private Label invalidLoginLbl;
    @FXML
    private Pane invalidPasswordPrompt, invalidUsernamePrompt;

    @FXML
    public void initialize() {
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

            DatabaseService.checkDatabaseInitialization();
            LearningManagementSystem.getInstance(
                    Database.login("", "")
            );
            PageNavigationService.navigateToPage(loginBtn, "home");

//        String username = usernameField.getText();
//        String password = passwordField.getText();
//
//        if (!validateUserInput(username, password)) {
//            return;
//        }
//
//        try {
//            DatabaseService.checkDatabaseInitialization();
//            LearningManagementSystem.getInstance(
//                    Database.login(username, password)
//            );
//            PageNavigationService.navigateToPage(loginBtn, "home");
//        } catch (RuntimeException e) {
//            invalidLoginLbl.setVisible(true);
//        }

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