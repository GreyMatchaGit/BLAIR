package controllers;

import database.Database;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
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

        StackPane notificationBoxPane = new StackPane();
        notificationBoxPane.setPrefWidth(notificationContent.getPrefWidth());
        notificationBoxPane.setPrefHeight(40);

            Rectangle rectangle = new Rectangle(notificationBoxPane.getPrefWidth(), notificationBoxPane.getHeight());
            rectangle.setArcHeight(10);
            rectangle.setArcWidth(10);

            Text titleComponent = new Text(title);
            titleComponent.setFont(new Font("Product Sans", 16));

            Text textComponent = new Text(text);
            textComponent.setFont(new Font("Product Sans", 12));

        notificationBoxPane.getChildren().addAll(rectangle, titleComponent, textComponent);

        notificationContent.getChildren().add(notificationBoxPane);

        mainScrollPane.setContent(notificationContent);
    }

    public void setActionListeners() {

    }
}
