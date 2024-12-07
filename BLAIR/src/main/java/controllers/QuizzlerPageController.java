package controllers;

import javafx.scene.layout.AnchorPane;
import lms.LearningManagementSystem;
import lms.User;
import lms.content.Deck;
import lms.content.Card;
import java.util.ArrayList;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.effect.BoxBlur;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import lms.content.Quizzler;
import lms.usertype.Student;
import services.ColorSelectorService;

public class QuizzlerPageController {

    @FXML
    private GridPane deckListGrid;
    @FXML
    private Rectangle rectangleBlur;
    @FXML
    private Rectangle deckListRectangle;

    @FXML
    private Label decksHeader;
    @FXML
    private Button createDeckBtn;
    @FXML
    private ScrollPane deckListScrollPane;
    @FXML
    private AnchorPane deckListComponents;

    // Add Deck Components
    @FXML
    private Button addDeckBtn;
    @FXML
    private Label addDeckLabel;
    @FXML
    private Line addDeckLine;
    @FXML
    private Rectangle addDeckContainer;
    @FXML
    private TextField addDeckTextField;
    @FXML
    private Button addDeckCloseBtn;
    @FXML
    private Label addDeckError;
    @FXML
    private AnchorPane addDeckComponents;

    // Preview Header Components
    @FXML
    private Label deckNamePreview;
    @FXML
    private Button deckNameEditBtn;
    @FXML
    private Button addCardBtn;
    @FXML
    private Line previewTopLine;
    @FXML
    private AnchorPane previewHeaderComponents;

    // Add Question Components
    @FXML
    private Rectangle addQuestionContainer;
    @FXML
    private TextArea addQuestionTA;
    @FXML
    private TextField addQuestionTF;
    @FXML
    private Line addQuestionLine;
    @FXML
    private Button addQuestionSubmitBtn;
    @FXML
    private Button addQuestionCancelBtn;
    @FXML
    private Label addQuestionError;
    @FXML
    private AnchorPane addQuestionQuizComponents;

    // Preview Card Components
    @FXML
    private Rectangle previewCardContainer;
    @FXML
    private TextArea previewCardTA;
    @FXML
    private TextField previewCardTF;
    @FXML
    private Line previewCardLine;
    @FXML
    private Button previewCardRemoveBtn;
    @FXML
    private Button nextBtn;
    @FXML
    private Button prevBtn;
    @FXML
    private Button playBtn;
    @FXML
    private AnchorPane previewCardComponents;

    // Congratulations Card Components
    @FXML
    private Rectangle congratsContainer;
    @FXML
    private Label congratsLabel;
    @FXML
    private Label congratsLabelScore;
    @FXML
    private Label congratsLabelTotalScore;
    @FXML
    private Button congratsBtn;
    @FXML
    private Line congratsLine;
    @FXML
    private ImageView nerd = new ImageView();
    @FXML
    private ImageView winner = new ImageView();
    @FXML
    private AnchorPane congratsComponents;

    // Confirm Remove Deck Components
    @FXML
    private Rectangle confirmRemoveContainer;
    @FXML
    private Label removeDeckHeaderLabel;
    @FXML
    private Label removeDeckNameLabel;
    @FXML
    private Button confirmRemoveDeckBtn;
    @FXML
    private Button cancelRemoveBtn;
    @FXML
    private AnchorPane confirmRemoveDeckComponents;

    Quizzler quizzler = null;

    @FXML
    public void initialize() {
        LearningManagementSystem lms = LearningManagementSystem.getInstance();

        User currentUser = lms.getCurrentUser();
        quizzler = lms.getQuizzler();

        createDeckBtn.setOnMouseClicked(event -> {
            blurBackgroundComponents(true);
            addDeckComponents.setVisible(true);
            addDeckLabel.setText("Enter deck name");
            addDeckLabel.setLayoutX(113);
            addDeckError.setVisible(false);
            addDeckBtn.setText("Add Deck");

            previewHeaderComponents.setVisible(false);
            previewCardComponents.setVisible(false);
        });

        addDeckCloseBtn.setOnMouseClicked(event -> {
            blurBackgroundComponents(false);
            addDeckTextField.setText("");
            addDeckComponents.setVisible(false);
        });

        addDeckBtn.setOnMouseClicked(event -> {
            String deckName = addDeckTextField.getText();
            if(addDeckLabel.getText().equals("Enter deck name")) {
                addDeck(deckName);
            } else {
                renameDeck(deckName);
            }
        });

        addDeckTextField.setOnKeyPressed((keyEvent) -> {
            switch (keyEvent.getCode()) {
                case ENTER:
                    String deckName = addDeckTextField.getText();
                    if(addDeckLabel.getText().equals("Enter deck name")) {
                        addDeck(deckName);
                    } else {
                        renameDeck(deckName);
                    }
                    break;

                case ESCAPE:
                    blurBackgroundComponents(false);
                    addDeckComponents.setVisible(false);
                    addDeckTextField.setText("");
                    break;
            }
        });

        deckNameEditBtn.setOnMouseClicked(event -> {
            blurBackgroundComponents(true);
            addDeckComponents.setVisible(true);
            addDeckLabel.setText("Enter new deck name");
            addDeckLabel.setLayoutX(65);
            addDeckBtn.setText("Rename Deck");
            addDeckError.setVisible(false);
        });

        addCardBtn.setOnMouseClicked(event -> {
            addQuestionTA.setEditable(true);
            addQuestionSubmitBtn.setText("Submit");
            addQuestionQuizComponents.setVisible(true);
            addQuestionError.setVisible(false);
            blurBackgroundComponents(true);
        });

        addQuestionSubmitBtn.setOnMouseClicked(event -> {
            String question = addQuestionTA.getText();
            String answer = addQuestionTF.getText();
            if (quizzler.currentDeck().isQuiz()) {
                submitAnswer(answer);
            } else if(addQuestionSubmitBtn.getText().equals("Submit")) {
                addCardToDeck(question, answer);
            } else {
                renameCardInDeck(question, answer);
            }
        });

        addQuestionCancelBtn.setOnMouseClicked(event -> {
            if (quizzler.currentDeck().isQuiz()) {
                submitAnswer("");
            } else {
                addQuestionTA.setText("");
                addQuestionTF.setText("");
                addQuestionQuizComponents.setVisible(false);
                blurBackgroundComponents(false);
                previewDeck();
            }
        });

        addQuestionTF.setOnKeyPressed(keyEvent -> {
            String question = addQuestionTA.getText();
            switch (keyEvent.getCode()) {
                case ENTER:
                    String answer = addQuestionTF.getText();
                    if (quizzler.currentDeck().isQuiz()) {
                        submitAnswer(answer);
                    } else {
                        addCardToDeck(question, answer);
                    }
                    break;

                case ESCAPE:
                    if (quizzler.currentDeck().isQuiz()) {
                        submitAnswer("");
                    } else {
                        addQuestionQuizComponents.setVisible(false);
                        blurBackgroundComponents(false);
                        previewDeck();
                    }
                    break;
            }

        });

        prevBtn.setOnMouseClicked(event -> {
            quizzler.currentDeck().previousCard();
            displayPreviewCard();
        });

        nextBtn.setOnMouseClicked(event -> {
            quizzler.currentDeck().nextCard();
            displayPreviewCard();
        });

        previewCardTA.setOnMouseClicked(event -> {
            addQuestionTA.setEditable(true);
            addQuestionTA.setText(quizzler.currentDeck().currentCard().getQuestion());
            addQuestionTF.setText(quizzler.currentDeck().currentCard().getAnswer());
            addQuestionSubmitBtn.setText("Renew");
            addQuestionQuizComponents.setVisible(true);
            addQuestionError.setVisible(false);
            blurBackgroundComponents(true);
        });

        previewCardTF.setOnMouseClicked(event -> {
            addQuestionTA.setEditable(true);
            addQuestionTA.setText(quizzler.currentDeck().currentCard().getQuestion());
            addQuestionTF.setText(quizzler.currentDeck().currentCard().getAnswer());
            addQuestionSubmitBtn.setText("Renew");
            addQuestionQuizComponents.setVisible(true);
            addQuestionError.setVisible(false);
            blurBackgroundComponents(true);
        });

        previewCardRemoveBtn.setOnMouseClicked(event -> {
            quizzler.currentDeck().removeCard();
            previewDeck();
        });

        playBtn.setOnMouseClicked(event -> {
            simulateQuiz();
            addQuestionCancelBtn.setText("Skip");
            addQuestionQuizComponents.setVisible(true);
            displayQuestion();
            blurBackgroundComponents(true);
        });

        congratsBtn.setOnMouseClicked(event -> {
            congratsComponents.setVisible(false);
            blurBackgroundComponents(false);
        });

        confirmRemoveDeckBtn.setOnMouseClicked(event -> {
            quizzler.removeDeck();

            confirmRemoveDeckComponents.setVisible(false);
            blurBackgroundComponents(false);
            previewHeaderComponents.setVisible(false);
            display();
        });
        
        cancelRemoveBtn.setOnMouseClicked(event -> {
            confirmRemoveDeckComponents.setVisible(false);
            blurBackgroundComponents(false);
            previewDeck();
        });

        addDeckComponents.setVisible(false);
        previewHeaderComponents.setVisible(false);
        previewCardComponents.setVisible(false);
        addQuestionQuizComponents.setVisible(false);
        congratsComponents.setVisible(false);
        confirmRemoveDeckComponents.setVisible(false);
        deckListGrid.setAlignment(Pos.TOP_CENTER);
        display();
    }


    private void display() {
        deckListGrid.getChildren().clear();
        ArrayList<Deck> decks = quizzler.getDecks();
        if (decks.size() < 15) {
            createDeckBtn.setLayoutY(85 + quizzler.getDecks().size() * 35);
        } else {
            createDeckBtn.setLayoutY(575);
        }

        for(Deck d: decks) {
            AnchorPane deckItem = new AnchorPane();
            deckItem.setPrefHeight(20);
            deckItem.setPrefWidth(248);
            deckItem.getStyleClass().add("deckItem");

            Rectangle r = new Rectangle();
            r.setHeight(10.0);
            r.setWidth(50.0);
            r.setArcWidth(15.0);
            r.setArcHeight(15.0);
            r.setLayoutX(15);
            r.setLayoutY(8);
            r.setStyle("-fx-fill: #" + new ColorSelectorService().getRandomColor() + ";");

            Label title = new Label(d.getDeckName());
            title.setLayoutX(75);
            title.setLayoutY(4);
            title.getStyleClass().add("deckTitle");

            Button b = new Button("-");
            b.setLayoutX(215);
            b.setLayoutY(2);
            b.setVisible(false);
            b.getStyleClass().add("deckBtn");

            b.setOnMouseClicked(event -> {
                confirmDelete();
            });

            deckItem.getChildren().addAll(r, title, b);

            deckItem.setOnMouseEntered(event -> {
                quizzler.setCurrentDeckIndex(d);
                b.setVisible(true);
            });

            deckItem.setOnMouseExited(event -> {
                b.setVisible(false);
            });

            deckItem.setOnMouseClicked((event) -> {
                quizzler.setCurrentDeckIndex(d);
                previewDeck();
            });
            deckListGrid.add(deckItem, 0, deckListGrid.getChildren().size());
        }

    }

    private void confirmDelete() {
        removeDeckNameLabel.setText("Deck " + quizzler.currentDeck().getDeckName());
        confirmRemoveDeckComponents.setVisible(true);
        blurBackgroundComponents(true);
    }

    private void addDeck(String deckName) {
        if (quizzler.addDeck(new Deck(deckName))) {
            addDeckComponents.setVisible(false);
            addDeckTextField.setText("");
            blurBackgroundComponents(false);
            display();
        } else {
            if(deckName.equals("")) {
                addDeckError.setText("Deck name cannot be empty!");
                addDeckError.setLayoutX(180);
            } else {
                addDeckError.setText("Deck with the same name already exists!");
                addDeckError.setLayoutX(138);
            }
            blurBackgroundComponents(true);
            addDeckError.setVisible(true);
        }
    }

    private void renameDeck(String deckName) {
        switch (quizzler.renameDeck(deckName)) {
            case 0:
                addDeckError.setVisible(true);
                addDeckError.setText("Deck name cannot be empty!");
                addDeckError.setLayoutX(180);
                break;
            case 1:
            case 3:
                blurBackgroundComponents(false);
                addDeckComponents.setVisible(false);
                addDeckTextField.setText("");
                deckNamePreview.setText(deckName);
                display();
                break;
            case 2:
                addDeckError.setVisible(true);
                addDeckError.setText("Deck with the same name already exists!");
                addDeckError.setLayoutX(138);
                break;
        }
    }

    private void previewDeck() {
        addDeckComponents.setVisible(false);
        boolean hasCards = !quizzler.currentDeck().getCards().isEmpty();
        previewHeaderComponents.setVisible(true);
        previewCardComponents.setVisible(hasCards);
        if(quizzler.currentDeck().getCards().size() == 1) {
            prevBtn.setVisible(false);
            nextBtn.setVisible(false);
        } else {
            prevBtn.setVisible(true);
            nextBtn.setVisible(true);
        }
        deckNamePreview.setText(quizzler.currentDeck().getDeckName());
        if (hasCards) {
            displayPreviewCard();
        }

    }

    private void addCardToDeck(String question, String answer) {
        switch (quizzler.currentDeck().addCard(question, answer)) {
            case 0:
                quizzler.currentDeck().setCurrentCardIndex(quizzler.currentDeck().getCards().size() - 1);
                blurBackgroundComponents(false);
                previewDeck();

                addQuestionQuizComponents.setVisible(false);
                addQuestionTA.setText("");
                addQuestionTF.setText("");
                break;
            case 1:
                addQuestionError.setText("Please fill in both question and answer fields!");
                addQuestionError.setVisible(true);
                addQuestionError.setLayoutX(91);
                break;
            case 2:
                addQuestionError.setText("Questions should not be duplicated!");
                addQuestionError.setVisible(true);
                addQuestionError.setLayoutX(105);
                break;
        }
    }

    private void renameCardInDeck(String question, String answer) {
        switch(quizzler.currentDeck().renameCard(question, answer)) {
            case 0:
                addQuestionError.setText("Please fill in both question and answer fields!");
                addQuestionError.setVisible(true);
                addQuestionError.setLayoutX(91);
                break;
            case 1:
                addQuestionError.setText("Questions should not be duplicated!");
                addQuestionError.setVisible(true);
                addQuestionError.setLayoutX(105);
                break;
            case 2:
                blurBackgroundComponents(false);
                previewDeck();

                addQuestionQuizComponents.setVisible(false);
                addQuestionTA.setText("");
                addQuestionTF.setText("");
                break;
        }
    }

    private void displayPreviewCard() {
        previewCardTA.setText(quizzler.currentDeck().currentCard().getQuestion());
        previewCardTA.setEditable(false);
        previewCardTF.setText(quizzler.currentDeck().currentCard().getAnswer());
        previewCardTF.setEditable(false);
    }

    private void displayQuestion() {
        addQuestionTA.setText(quizzler.currentDeck().currentQuizCard().getQuestion());
        addQuestionTA.setEditable(false);
        addQuestionTF.setText("");
    }

    private void simulateQuiz() {
        quizzler.currentDeck().constructQuiz();
        quizzler.currentDeck().setQuiz(true);
        quizzler.currentDeck().setScore(0);
        quizzler.currentDeck().setCurrentCardIndex(0);
        previewCardComponents.setVisible(false);

        blurBackgroundComponents(true);
    }

    private void submitAnswer(String userAnswer) {
        quizzler.currentDeck().evaluateAnswer(userAnswer);

        if (!quizzler.currentDeck().isQuizDone()) {
            addQuestionQuizComponents.setVisible(true);
            displayQuestion();
            blurBackgroundComponents(true);
        } else {
            endQuiz();
        }
    }

    private void endQuiz() {
        quizzler.currentDeck().setCurrentCardIndex(0);
        quizzler.currentDeck().setQuiz(false);

        addQuestionTA.setText("");
        addQuestionTF.setText("");
        addQuestionCancelBtn.setText("Cancel");
        addQuestionQuizComponents.setVisible(false);
        previewHeaderComponents.setVisible(true);
        previewCardComponents.setVisible(true);
        blurBackgroundComponents(true);

        congratsLabelScore.setText(String.valueOf(quizzler.currentDeck().getScore()));
        congratsLabelTotalScore.setText(String.valueOf(quizzler.currentDeck().getCards().size()));
        congratsComponents.setVisible(true);
        previewDeck();
        blurBackgroundComponents(false);
    }

    private void blurBackgroundComponents(boolean blur) {
        BoxBlur b = blur ? new BoxBlur() : null;

        deckListComponents.setEffect(b);
        deckListComponents.setDisable(blur);

        previewHeaderComponents.setEffect(b);
        previewHeaderComponents.setDisable(blur);

        previewCardComponents.setEffect(b);
        previewCardComponents.setDisable(blur);

        rectangleBlur.setEffect(b);
    }

}
