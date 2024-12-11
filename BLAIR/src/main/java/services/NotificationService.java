package services;

import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

public class NotificationService {

    private static NotificationService instance = null;
    private final AnchorPane notificationCenterPane;
    private final Button backButton;
    private Button previousButton;

    private static Boolean isOpen;

    private NotificationService(AnchorPane notificationCenterPane) {

        this.notificationCenterPane = notificationCenterPane;
        this.backButton = ((Button)notificationCenterPane.getChildren().get(1));
    }

    public static void initialize(AnchorPane notificationCenterPane) {
        instance = new NotificationService(notificationCenterPane);
    }

    public static NotificationService getInstance() {

        return instance;
    }

    public static Button closeBar() {

        instance.notificationCenterPane.setVisible(false);
        isOpen = false;
        return instance.previousButton;
    }

    public static void openBar(Button previousButton) {

        instance.previousButton = previousButton;
        instance.notificationCenterPane.setVisible(true);
        isOpen = true;
    }

    public static Button getBackButton() {
        return instance.backButton;
    }

    public static boolean isOpen() {
        return isOpen;
    }
}
