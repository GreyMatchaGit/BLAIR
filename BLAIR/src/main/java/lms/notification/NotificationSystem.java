package lms.notification;

import java.util.HashMap;
import database.Database;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import lms.LearningManagementSystem;
import util.NotificationBuilder;

public class NotificationSystem {

    private HashMap<String, Notification> notifications;

    private AnchorPane notificationCenterPane;
    private AnchorPane parentPanel;
    private Button backButton;
    private Button previousButton;

    public NotificationSystem() {

        notifications = Database.notificationDatabase;
        notificationCenterPane = null;
        backButton = null;
    }

    private Boolean isOpen;

    public void setPane(AnchorPane parentPanel, AnchorPane notificationCenterPane) {

        this.parentPanel = parentPanel;
        this.notificationCenterPane = notificationCenterPane;
        this.backButton = ((Button)notificationCenterPane.getChildren().get(1));
    }

    public Button closeBar() {

        notificationCenterPane.setVisible(false);
        parentPanel.setVisible(false);
        isOpen = false;

        return previousButton;
    }

    public void openBar(Button previousButton) {

        this.previousButton = previousButton;
        notificationCenterPane.setVisible(true);
        parentPanel.setVisible(true);
        isOpen = true;
    }

    public Button getBackButton() {
        return backButton;
    }

    public void addNotification(String title, String description) {

        Notification newNotification = new NotificationBuilder(title)
                .setText(description)
                .create();

        notifications.put(newNotification.getKey(), newNotification);
        LearningManagementSystem.getInstance().getCurrentUser().addNotificationKey(newNotification.getKey());
    }

    public boolean isOpen() {
        return isOpen;
    }

}
