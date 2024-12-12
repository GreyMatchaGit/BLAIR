package controllers;

import database.Database;
import javafx.css.FontFace;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import lms.LearningManagementSystem;
import lms.notification.Notification;
import lms.usertype.User;

import java.util.ArrayList;

public class NotificationBarController {

    @FXML
    private Button backBtn;

    @FXML
    private ScrollPane mainScrollPane;

    @FXML
    private VBox notificationContent;

    @FXML
    public void initialize() {

        mainScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        mainScrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        User currentUser = LearningManagementSystem.getInstance().getCurrentUser();
        showNotifications(currentUser);

        setActionListeners();
    }

    public void showNotifications(User currentUser) {

        ArrayList<String> notificationKeys = currentUser.getNotificationKeys();

        for (String key : notificationKeys) {

            addNotificationBox(Database.notificationDatabase.get(key));
        }
    }

    public void addNotificationBox(Notification notification) {

        String title = notification.getTitle();
        String text = notification.getText();

        AnchorPane notificationBoxPane = new AnchorPane();
        notificationBoxPane.setPrefWidth(notificationContent.getPrefWidth());
        notificationBoxPane.setPrefHeight(80);

            Rectangle rectangle = new Rectangle();
            rectangle.setWidth(notificationBoxPane.getPrefWidth() - 10);
            rectangle.setHeight(notificationBoxPane.getPrefHeight() - 10);
            rectangle.setFill(Color.web("3333ff"));
            rectangle.setArcHeight(10);
            rectangle.setArcWidth(10);
            rectangle.setLayoutY(5);
            rectangle.setLayoutX(5);

            Text titleComponent = new Text(title);
            titleComponent.setFont(new Font("Product Sans", 16));
            titleComponent.setFill(Color.web("#ffffff"));
            titleComponent.setLayoutX(15);
            titleComponent.setLayoutY(25);

            Text textComponent = new Text(text);
            textComponent.setFont(new Font("Product Sans", 12));
            textComponent.setFill(Color.web("#ffffff"));
            textComponent.setLayoutX(15);
            textComponent.setLayoutY(35);

        notificationBoxPane.getChildren().addAll(rectangle, titleComponent, textComponent);

        notificationContent.getChildren().add(notificationBoxPane);

        mainScrollPane.setContent(notificationContent);
    }

    public void setActionListeners() {

    }
}
