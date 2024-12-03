package controllers;

import services.DatabaseService;
import services.PageNavigationService;
import javafx.animation.*;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

public class SplashScreenPageController {
    @FXML
    private AnchorPane splashScreenPane;

    @FXML
    private Label splashScreenTitle;

    @FXML
    private ImageView logo;

    @FXML
    public void initialize() {

        showLogo();
        DatabaseService.initialize();
        showCredits();
    }

    private void showLogo() {

        splashScreenPane.setStyle("-fx-background-image: url('media/splash-bg.png');");

        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(2), logo);
        fadeTransition.setFromValue(0.0);
        fadeTransition.setToValue(1.0);
        fadeTransition.setCycleCount(1);
        fadeTransition.play();

        ScaleTransition scaleTransition = new ScaleTransition(Duration.seconds(1.4), logo);
        scaleTransition.setFromX(1.0);
        scaleTransition.setToX(1.08);
        scaleTransition.setFromX(1.09);
        scaleTransition.setToX(1.3);
        scaleTransition.setFromY(1.0);
        scaleTransition.setToY(1.08);
        scaleTransition.setFromY(1.09);
        scaleTransition.setToY(1.3);

        scaleTransition.setCycleCount(2);
        scaleTransition.setAutoReverse(true);
        scaleTransition.play();
    }

    private void showCredits() {

        String titleText = "Capstone Project by Team ProCrammers";
        typewriterEffect(titleText);

        PauseTransition pause = new PauseTransition(Duration.seconds(4.5));
        pause.setOnFinished(event -> PageNavigationService.navigateToPage(splashScreenPane, "login"));
        pause.play();
    }

    private void typewriterEffect(String text) {

        splashScreenTitle.setText("");
        Timeline timeline = new Timeline();

        for (int i = 0; i < text.length(); i++) {
            int index = i;
            KeyFrame keyFrame = new KeyFrame(Duration.seconds(i * 0.03), event -> splashScreenTitle.setText(splashScreenTitle.getText() + text.charAt(index)));
            timeline.getKeyFrames().add(keyFrame);
        }

        timeline.play();
    }
}