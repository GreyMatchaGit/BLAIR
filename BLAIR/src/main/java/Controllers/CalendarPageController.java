package Controllers;

import Services.PageNavigationService;
import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Arrays;

public class CalendarPageController {
    @FXML
    private ImageView imgHolder;
    @FXML
    private Button prevBtn, nextBtn;
    @FXML
    private Label monthLbl;

    private CalendarPageController.CalendarMonthController monthsController;
    private static ArrayList<String> months;

    @FXML
    public void prevMonth() {
        if (monthsController != null) {
            monthsController.prevMonth();
        }
    }

    @FXML
    public void nextMonth() {
        if (monthsController != null) {
            monthsController.nextMonth();
        }
    }

    @FXML
    public void initialize() {
        months = new ArrayList<>(Arrays.asList(
                "January", "February", "March", "April",
                "May", "June", "July", "August",
                "September", "October", "November", "December"
        ));

        if (monthLbl != null) {
            monthsController = new CalendarMonthController(imgHolder, prevBtn, nextBtn, monthLbl, months);
            monthLbl.setText(months.get(0));
        }
    }

    public static class CalendarMonthController {
        private ImageView imgHolder;
        private Button prevBtn;
        private Button nextBtn;
        private Label monthLbl;
        private Image[] images;
        private ArrayList<String> months;
        private int currentIndex = 0;

        public CalendarMonthController(ImageView imgHolder, Button prevBtn, Button nextBtn, Label monthLbl, ArrayList<String> months) {
            this.imgHolder = imgHolder;
            this.prevBtn = prevBtn;
            this.nextBtn = nextBtn;
            this.months = months;
            this.monthLbl = monthLbl;
            initializeCarousel();
        }

        public void prevMonth() {
            currentIndex = (currentIndex - 1 + images.length) % images.length;
            updateMonthDisplay();
        }

        public void nextMonth() {
            currentIndex = (currentIndex + 1) % images.length;
            updateMonthDisplay();
        }

        private void updateMonthDisplay() {
            fadeImage(images[currentIndex]);
            monthLbl.setText(months.get(currentIndex) + " 2024");
        }

        private void fadeImage(Image newImage) {
            FadeTransition fadeOut = new FadeTransition(Duration.millis(300), imgHolder);
            fadeOut.setFromValue(1.0);
            fadeOut.setToValue(0.0);

            fadeOut.setOnFinished(event -> {
                imgHolder.setImage(newImage);

                FadeTransition fadeIn = new FadeTransition(Duration.millis(300), imgHolder);
                fadeIn.setFromValue(0.0);
                fadeIn.setToValue(1.0);
                fadeIn.play();
            });

            fadeOut.play();
        }

        private void initializeCarousel() {
            images = new Image[]{
                    new Image("media/months/jan.png"),
                    new Image("media/months/feb.png"),
                    new Image("media/months/feb.png"),
                    new Image("media/months/feb.png"),
                    new Image("media/months/feb.png"),
                    new Image("media/months/feb.png"),
                    new Image("media/months/feb.png"),
                    new Image("media/months/feb.png"),
                    new Image("media/months/feb.png"),
                    new Image("media/months/feb.png"),
                    new Image("media/months/feb.png")
            };

            imgHolder.setScaleX(1.0);
            imgHolder.setScaleY(1.0);
            imgHolder.setFitWidth(421.0);
            imgHolder.setFitHeight(363.0);
            imgHolder.setPreserveRatio(true);
            imgHolder.setSmooth(true);

            if (images.length > 0) {
                imgHolder.setImage(images[currentIndex]);
            }
        }
    }
}
