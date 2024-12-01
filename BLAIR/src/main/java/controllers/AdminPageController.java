package controllers;

import database.type.GSONDB;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import services.PageNavigationService;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.io.IOException;

public class AdminPageController {
    @FXML
    private Button addStudentBtn, addTeacherBtn, updateBtn;

    @FXML
    private Rectangle studentBox, teacherBox;
    @FXML
    private ImageView studentImg, teacherImg;
    @FXML
    private Pane studentPane, teacherPane;

    @FXML
    public void initialize() {
        addStudentBtn.setOnAction(actionEvent -> PageNavigationService.navigateToPage(addStudentBtn, "add-student"));
        addTeacherBtn.setOnAction(actionEvent -> PageNavigationService.navigateToPage(addTeacherBtn, "add-teacher"));

        updateBtn.setOnAction(actionEvent -> {
            try {
                GSONDB.updateDatabase();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        setupHoverEffect(studentPane, addStudentBtn, studentBox, studentImg);
        setupHoverEffect(teacherPane, addTeacherBtn, teacherBox, teacherImg);
    }

    private void setupHoverEffect(Pane pane, Button button, javafx.scene.Node... nodes) {
        pane.setOnMouseEntered(event -> {
            for (javafx.scene.Node node : nodes) {
                ScaleTransition scaleTransition = createScaleTransition(node, 1.15);
                scaleTransition.play();
            }
            ScaleTransition buttonScaleTransition = createScaleTransition(button, 1.1);
            buttonScaleTransition.play();

            TranslateTransition translateTransition = new TranslateTransition(Duration.millis(200), button);
            translateTransition.setByY(17);
            translateTransition.play();
        });

        pane.setOnMouseExited(event -> {
            for (javafx.scene.Node node : nodes) {
                ScaleTransition scaleTransition = createScaleTransition(node, 1.0);
                scaleTransition.play();
            }
            ScaleTransition buttonScaleTransition = createScaleTransition(button, 1.0);
            buttonScaleTransition.play();

            TranslateTransition translateTransition = new TranslateTransition(Duration.millis(200), button);
            translateTransition.setByY(-17);
            translateTransition.play();
        });
    }

    private ScaleTransition createScaleTransition(javafx.scene.Node node, double scaleFactor) {
        ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(200), node);
        scaleTransition.setToX(scaleFactor);
        scaleTransition.setToY(scaleFactor);
        scaleTransition.setInterpolator(javafx.animation.Interpolator.EASE_BOTH);
        return scaleTransition;
    }
}