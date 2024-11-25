package Controllers;

import LMS.LearningManagementSystem;
import LMS.Quiz.Deck;
import LMS.Quiz.Quizzler;
import LMS.User;
import Services.PageNavigationService;
import javafx.animation.ScaleTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class QuizzlerPageController {
    @FXML
    private Button homeBtn;
    @FXML
    private Button profileBtn;
    @FXML
    private Button courseBtn;
    @FXML
    private Button chatBtn;
    @FXML
    private Button calendarBtn;
    @FXML
    private Button quizBtn;

    private Quizzler setTempQuizzler() {
        Quizzler q = new Quizzler();
        q.addDeck(new Deck("AWAWAWAWAWAW"));
        return q;
    }

    @FXML
    public void initialize() {
        quizBtn.getStyleClass().add("disabled");
        LearningManagementSystem lms = LearningManagementSystem.getInstance(null);
        User currentUser  = lms.getCurrentUser();

        // Quizzler
        currentUser.setQuizzler(setTempQuizzler());

        homeBtn.setOnAction(event -> PageNavigationService.navigateToPage(homeBtn, "home"));
        profileBtn.setOnAction(event -> PageNavigationService.navigateToPage(profileBtn, "user-profile"));
        chatBtn.setOnAction(event -> PageNavigationService.navigateToPage(chatBtn, "chat"));
        calendarBtn.setOnAction(event -> PageNavigationService.navigateToPage(calendarBtn, "calendar"));
        courseBtn.setOnAction(event -> PageNavigationService.navigateToPage(courseBtn, "course"));

        displayDecks(currentUser);
    }

    //Temp Changes

    private void displayDecks(User user) {
        ArrayList<Deck> decks = user.getQuizzler().getDecks();



    }

}
