package Controllers;

import LMS.LearningManagementSystem;
import LMS.User;
import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import java.io.IOException;

public class HomePageController {
    @FXML
    private ImageView imgHolder;
    @FXML
    private Button prevBtn;
    @FXML
    private Button nextBtn;

    private CarouselController carouselController;

    @FXML
    public void showPrevImage() {
        if (carouselController != null) {
            carouselController.showPreviousImage();
        }
    }

    @FXML
    public void showNextImage() {
        if (carouselController != null) {
            carouselController.showNextImage();
        }
    }

    @FXML
    public void initialize() {
        carouselController = new CarouselController(imgHolder, prevBtn, nextBtn);
        User currentUser = LearningManagementSystem
                .getInstance()
                .getCurrentUser();
    }

    public static class CarouselController {
        private final ImageView imgHolder;
        private Button prevBtn;
        private Button nextBtn;
        private Image[] images;
        private int currentIndex = 0;

        public CarouselController(ImageView imgHolder, Button prevBtn, Button nextBtn) {
            this.imgHolder = imgHolder;
            this.prevBtn = prevBtn;
            this.nextBtn = nextBtn;
            initializeCarousel();
        }

        public void showPreviousImage() {
            currentIndex = (currentIndex - 1 + images.length) % images.length;
            fadeImage(images[currentIndex]);
        }

        public void showNextImage() {
            currentIndex = (currentIndex + 1) % images.length;
            fadeImage(images[currentIndex]);
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
                    new Image("media/img-carousel/gle-bldg.jpg"),
                    new Image("media/img-carousel/lib-img1.jpg"),
                    new Image("media/img-carousel/lib-img2.jpg"),
                    new Image("media/img-carousel/lib-img3.jpg")
            };

            imgHolder.setScaleX(1.0);
            imgHolder.setScaleY(1.0);
            imgHolder.setFitWidth(525.0);
            imgHolder.setFitHeight(350.0);
            imgHolder.setPreserveRatio(true);
            imgHolder.setSmooth(true);

            if (images.length > 0) {
                imgHolder.setImage(images[currentIndex]);
            }
        }
    }
}