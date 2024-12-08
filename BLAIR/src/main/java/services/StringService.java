package services;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Label;
import javafx.util.Duration;
import lms.usertype.User;

import java.net.URL;

public class StringService {

    public static String convertFrom(URL url) {
        return new StringBuilder(url.toString())
                .delete(0, 5)
                .toString();
    }

    public static String trim(String string, Integer limit) {
        if (string.length() > limit) {

            return new StringBuilder(string)
                            .delete(limit - 3, string.length())
                            .append("...")
                            .toString();
        }

        return string;
    }

    public static String generateKey(String uniqueID) {
        return UserService.getInstance().getUserName() + "@" + uniqueID;
    }

    public static String defaultUsername(User user) {
        return String.format(
                "%s.%s",
                user.getFirstName().replaceAll("\\s", ""),
                user.getLastName()
        ).toLowerCase();
    }

    public static String defaultPassword(User user) {
        return String.format(
                "%s.123456",
                user.getLastName()
        ).toLowerCase();
    }

    public static void typewriterEffect(Label label, String text) {
        label.setText("");
        Timeline timeline = new Timeline();

        for (int i = 0; i < text.length(); i++) {
            int index = i;
            KeyFrame keyFrame = new KeyFrame(Duration.seconds(i * 0.03), event -> label.setText(label.getText() + text.charAt(index)));
            timeline.getKeyFrames().add(keyFrame);
        }

        timeline.play();
    }
}
