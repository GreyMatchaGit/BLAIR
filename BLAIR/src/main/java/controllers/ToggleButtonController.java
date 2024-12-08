package controllers;

import javafx.animation.Interpolator;
import javafx.animation.PathTransition;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.shape.*;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.awt.*;
import java.util.Objects;

public class ToggleButtonController {

    @FXML
    private Panel togglePanel;
    @FXML
    private Button toggleButton;
    @FXML
    private Rectangle toggleBackground;
    @FXML
    private Circle toggleCircle;

    private Boolean isToggled;

    @FXML
    public void initialize() {

        isToggled = false;

        toggleButton.setCursor(Cursor.HAND);
        toggleButton.setOnMouseClicked(mouseEvent -> {
            transition().play();
            showAlert();
            transition().play();
        });
    }

    public boolean getState() {
        return isToggled;
    }

    private PathTransition transition() {

        int radius = (int)toggleCircle.getRadius();
        int offset = (isToggled)
                ? -radius
                : radius/2;

        MoveTo point1 = new MoveTo(
                toggleCircle.getLayoutX(),
                toggleCircle.getLayoutY() - radius
        );

        LineTo point2 = new LineTo(
                toggleCircle.getLayoutX() + offset,
                toggleCircle.getLayoutY() - radius
        );

        Path path = new Path();
        path.getElements().addAll(point1, point2);

        PathTransition pt = new PathTransition(
                Duration.millis(100),
                path,
                toggleCircle
        );

        pt.setInterpolator(Interpolator.EASE_OUT);

        isToggled = !isToggled;
        return pt;
    }

    private void showAlert() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        ImageView image = new ImageView(String.valueOf(Objects.requireNonNull(getClass().getResource("/media/chill-guy.png"))));
        image.setFitHeight(200);
        image.setFitWidth(400);
        alert.setGraphic(image);
        alert.setTitle("In Progress");
        alert.setHeaderText(" ");
        alert.setContentText("Feature under construction! Use your best judgment");
        alert.setHeight(350);
        alert.showAndWait();
    }
}
