package lms.notification;

import services.StringService;

import javax.swing.text.html.ImageView;
import java.time.LocalTime;
import java.util.Date;

public class Notification {

    private ImageView image;
    private String title;
    private String text;
    private Date datePosted;
    private final String key;

    public Notification(String title, String text, ImageView image, Date datePosted) {
        this.title = title;
        this.text = text;
        this.image = image;
        this.datePosted = datePosted;
        key = StringService.generateKey(title + LocalTime.now());
    }

    public String getKey() {
        return key;
    }

    public ImageView getImage() {
        return image;
    }

    public void setImage(ImageView image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getDatePosted() {
        return datePosted;
    }

    public void setDatePosted(Date datePosted) {
        this.datePosted = datePosted;
    }
}
