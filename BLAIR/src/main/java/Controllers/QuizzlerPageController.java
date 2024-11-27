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
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.awt.*;
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


    LearningManagementSystem lms = LearningManagementSystem.getInstance(null);
    User currentUser  = lms.getCurrentUser();

    @FXML
    public void initialize() {
        quizBtn.getStyleClass().add("disabled");

        homeBtn.setOnAction(event -> PageNavigationService.navigateToPage(homeBtn, "home"));
        profileBtn.setOnAction(event -> PageNavigationService.navigateToPage(profileBtn, "user-profile"));
        chatBtn.setOnAction(event -> PageNavigationService.navigateToPage(chatBtn, "chat"));
        calendarBtn.setOnAction(event -> PageNavigationService.navigateToPage(calendarBtn, "calendar"));
        courseBtn.setOnAction(event -> PageNavigationService.navigateToPage(courseBtn, "course"));

        setVisibilityAddDeckPrompt(false);
        display(currentUser);
    }

    //Temp Changes

    // Elements at initialization
    @FXML
    private Button createDeckButton;

    // Elements for Add-Deck Prompt
    @FXML
    private Button addDeckButton;
    @FXML
    private Label addDeckLabel;
    @FXML
    private Line addDeckLine;
    @FXML
    private Rectangle addDeckContainer;
    @FXML
    private TextField addDeckTextField;
    @FXML
    private Button addDeckButtonX;

    private void setVisibilityAddDeckPrompt(boolean b) {
        addDeckButton.setVisible(b);
        addDeckContainer.setVisible(b);
        addDeckLine.setVisible(b);
        addDeckLabel.setVisible(b);
        addDeckTextField.setVisible(b);
        addDeckButtonX.setVisible(b);
    }

    @FXML
    private void onCreateDeckButtonClick() {
        setVisibilityAddDeckPrompt(true);
    }

    @FXML
    private void onAddDeckButtonXClick() {
        setVisibilityAddDeckPrompt(false);
        addDeckTextField.setText("");
    }

    @FXML
    private void onAddDeckButtonClick() {
        String deckName = addDeckTextField.getText();
        if(deckName.equals("")) {

        } else {
            currentUser.getQuizzler().addDeck(new Deck(deckName));
            onAddDeckButtonXClick();
        }
        display(currentUser);
    }

    // Deck-List Layout

    private void display(User user) {
        ArrayList<Deck> decks = user.getQuizzler().getDecks();
        setCreateBtnOrientation(decks);



    }

    private void setCreateBtnOrientation(ArrayList<Deck> decks) {
        if(decks.size() == 0) {
            createDeckButton.setLayoutX(137);
            createDeckButton.setLayoutY(118);
        } else {
            createDeckButton.setLayoutX(137);
            createDeckButton.setLayoutY(601);
        }
    }

}