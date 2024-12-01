//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package controllers;

import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import lms.LearningManagementSystem;
import lms.User;
import lms.content.Deck;
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
    private AnchorPane addDeckComponents;

    // Preview Header Components
    @FXML
    private Label deckNamePreview;
    @FXML
    private Button addCardBtn;
    @FXML
    private Button downloadBtn;
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

    User currentUser;
    Deck currentDeck;
    int currentCardIndex;
    ArrayList<String> quiz;
    boolean isQuiz;
    int score;

    @FXML
    public void initialize() {
        LearningManagementSystem lms = LearningManagementSystem.getInstance(null);
        currentUser = lms.getCurrentUser();
        isQuiz = false;
        currentCardIndex = -1;

        createDeckBtn.setOnMouseClicked((event) -> {
            blurBackgroundAddDeck(true);
            addDeckComponents.setVisible(true);
            previewHeaderComponents.setVisible(false);
            previewCardComponents.setVisible(false);
        });

        addDeckCloseBtn.setOnMouseClicked((event) -> {
            blurBackgroundAddDeck(false);
            addDeckTextField.setText("");
            addDeckComponents.setVisible(true);
        });

        addDeckBtn.setOnMouseClicked((event) -> {
            String deckName = addDeckTextField.getText();
            addDeck(deckName);
        });

        addDeckTextField.setOnKeyPressed((keyEvent) -> {
            switch (keyEvent.getCode()) {
                case ENTER:
                    String deckName = addDeckTextField.getText();
                    addDeck(deckName);
                    break;

                case ESCAPE:
                    blurBackgroundAddDeck(false);
                    addDeckComponents.setVisible(false);
                    addDeckTextField.setText("");
                    break;
            }
        });

        addCardBtn.setOnMouseClicked((event) -> {
            addQuestionTA.setEditable(true);
            addQuestionQuizComponents.setVisible(true);
            blurBackgroundAddQuestionQuiz(true);
        });

        addQuestionSubmitBtn.setOnMouseClicked((event) -> {
            String question = addQuestionTA.getText();
            String answerKey = addQuestionTF.getText();
            if (isQuiz) {
                submitAnswer(question, answerKey);
            } else {
                addCardToDeck(question, answerKey);
            }
        });

        addQuestionCancelBtn.setOnMouseClicked((event) -> {
            if (isQuiz) {
                String question = addQuestionTA.getText();
                submitAnswer(question, "");
            } else {
                addQuestionTA.setText("");
                addQuestionTF.setText("");
                addQuestionQuizComponents.setVisible(false);
                blurBackgroundAddQuestionQuiz(false);
                previewDeck();
            }
        });

        addQuestionTF.setOnKeyPressed((keyEvent) -> {
            String question = addQuestionTA.getText();
            switch (keyEvent.getCode()) {
                case ENTER:
                    String answerKey = addQuestionTF.getText();
                    if (isQuiz) {
                        submitAnswer(question, answerKey);
                    } else {
                        addCardToDeck(question, answerKey);
                    }
                    break;

                case ESCAPE:
                    if (isQuiz) {
                        submitAnswer(question, "");
                    } else {
                        addQuestionQuizComponents.setVisible(false);
                        blurBackgroundAddQuestionQuiz(false);
                        previewDeck();
                    }
                    break;
            }

        });

        prevBtn.setOnMouseClicked((event) -> {
            --currentCardIndex;
            if (currentCardIndex < 0) {
                currentCardIndex = currentDeck.getItems().size() - 1;
            }
            displayPreviewCard();
        });

        nextBtn.setOnMouseClicked((event) -> {
            ++currentCardIndex;
            if (currentCardIndex > currentDeck.getItems().size() - 1) {
                currentCardIndex = 0;
            }
            displayPreviewCard();
        });

        previewCardRemoveBtn.setOnMouseClicked((event) -> {
            currentDeck.removeQuestion(currentCardIndex);
            --currentCardIndex;
            if (currentCardIndex == -1 && !currentDeck.getItems().isEmpty()) {
                currentCardIndex = 0;
            }
            previewDeck();
        });

        downloadBtn.setOnMouseClicked((event) -> {

        });

        playBtn.setOnMouseClicked((event) -> {
            simulateQuiz();
            addQuestionCancelBtn.setText("Skip");
            addQuestionQuizComponents.setVisible(true);
            displayQuestion();
            blurBackgroundAddQuestionQuiz(true);
        });

        congratsBtn.setOnMouseClicked((event) -> {
            congratsComponents.setVisible(false);
            blurBackgroundAddQuestionQuiz(false);
        });

        addDeckComponents.setVisible(false);
        previewHeaderComponents.setVisible(false);
        previewCardComponents.setVisible(false);
        addQuestionQuizComponents.setVisible(false);
        congratsComponents.setVisible(false);
        deckListGrid.setAlignment(Pos.TOP_CENTER);
        display(currentUser);
    }

    private void display(User user) {
        deckListGrid.getChildren().clear();
        ArrayList<Deck> decks = user.getQuizzler().getDecks();
        if (decks.size() < 15) {
            createDeckBtn.setLayoutY(85 + user.getQuizzler().getDecks().size() * 35);
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
                currentUser.getQuizzler().removeDeck(d.getDeckName());
                display(currentUser);
            });

            deckItem.getChildren().addAll(r, title, b);

            deckItem.setOnMouseEntered(event -> {
                b.setVisible(true);
            });

            deckItem.setOnMouseExited(event -> {
                b.setVisible(false);
            });

            deckItem.setOnMouseClicked((event) -> {
                currentDeck = d;
                previewDeck();
            });
            deckListGrid.add(deckItem, 0, deckListGrid.getChildren().size());
        }

    }

    private void confirmDelete() {

    }

    private void addDeck(String deckName) {
        if (currentUser.getQuizzler().addDeck(new Deck(deckName))) {
            addDeckComponents.setVisible(false);
            addDeckTextField.setText("");
            blurBackgroundAddDeck(false);
            display(currentUser);
        } else {
            blurBackgroundAddDeck(true);
        }

    }

    private void previewDeck() {
        addDeckComponents.setVisible(false);
        boolean hasCards = !currentDeck.getItems().isEmpty();
        previewHeaderComponents.setVisible(true);
        previewCardComponents.setVisible(hasCards);
        if(currentDeck.getItems().size() == 1) {
            prevBtn.setVisible(false);
            nextBtn.setVisible(false);
        } else {
            prevBtn.setVisible(true);
            nextBtn.setVisible(true);
        }
        deckNamePreview.setText(currentDeck.getDeckName());
        if (hasCards) {
            displayPreviewCard();
        }

    }

    private void addCardToDeck(String question, String answer) {
        if (!question.equals("") && !answer.equals("")) {
            currentDeck.addQuestion(question, answer);
            currentCardIndex = currentDeck.getItems().size() - 1;
            previewDeck();
            addQuestionQuizComponents.setVisible(false);
            blurBackgroundAddQuestionQuiz(false);
            addQuestionTA.setText("");
            addQuestionTF.setText("");
        }

    }

    private void displayPreviewCard() {
        previewCardTA.setText(currentDeck.getItems().get(currentCardIndex));
        previewCardTA.setEditable(false);
        previewCardTF.setText(currentDeck.getAnswerKey().get(currentCardIndex));
        previewCardTF.setEditable(false);
    }

    private void displayQuestion() {
        addQuestionTA.setText(quiz.get(currentCardIndex));
        addQuestionTA.setEditable(false);
        addQuestionTF.setText("");
    }

    private void simulateQuiz() {
        quiz = currentDeck.constructQuiz();
        isQuiz = true;
        score = 0;
        currentCardIndex = 0;
        previewHeaderComponents.setVisible(true);
        previewCardComponents.setVisible(false);
    }

    private void submitAnswer(String question, String userAnswer) {
        if (currentDeck.evaluateAnswer(question, userAnswer)) {
            ++score;
        }

        if (++currentCardIndex < quiz.size()) {
            addQuestionQuizComponents.setVisible(true);
            displayQuestion();
            blurBackgroundAddQuestionQuiz(true);
        } else {
            endQuiz();
        }

    }

    private void endQuiz() {
        currentCardIndex = 0;
        isQuiz = false;

        addQuestionCancelBtn.setText("Cancel");
        addQuestionQuizComponents.setVisible(false);
        previewHeaderComponents.setVisible(true);
        previewCardComponents.setVisible(true);
        blurBackgroundAddQuestionQuiz(true);

        congratsLabelScore.setText(String.valueOf(score));
        congratsLabelTotalScore.setText(String.valueOf(currentDeck.getItems().size()));
        congratsComponents.setVisible(true);
        previewDeck();
        System.out.println(score);
    }

    private void blurBackgroundAddDeck(boolean blur) {
        BoxBlur b = blur ? new BoxBlur() : null;
        rectangleBlur.setEffect(b);
        deckListComponents.setEffect(b);
        previewHeaderComponents.setEffect(b);
    }

    private void blurBackgroundAddQuestionQuiz(boolean blur) {
        BoxBlur b = blur ? new BoxBlur() : null;
        rectangleBlur.setEffect(b);
        
        deckListComponents.setEffect(b);
        previewHeaderComponents.setEffect(b);
        previewCardComponents.setEffect(b);
    }
}
