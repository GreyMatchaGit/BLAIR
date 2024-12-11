package util;

import lms.notification.Notification;

import javax.swing.text.html.ImageView;
import java.util.Date;

public class NotificationBuilder {

    private ImageView image;
    private String title;
    private String text;
    private Date datePosted;

    public NotificationBuilder(String title) {
        this.title = title;
    }

    public NotificationBuilder setDate(Date date) {
        datePosted = date;
        return this;
    }

    public NotificationBuilder setText(String text) {
        this.text = text;
        return this;
    }

    public NotificationBuilder setImage(ImageView image) {
        this.image = image;
        return this;
    }

    public Notification create() {
        return new Notification(title, text, image, datePosted);
    }
}
