//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package controllers;

import lms.LearningManagementSystem;
import lms.User;
import lms.quiz.Deck;
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

    // Preview Header Components
    @FXML
    private Label deckNamePreview;
    @FXML
    private Button addCardBtn;
    @FXML
    private Button downloadBtn;
    @FXML
    private Line previewTopLine;

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

        createDeckBtn.setOnMouseClicked((event) -> {
            blurBackgroundAddDeck(true);
            setVisibilityAddDeckPrompt(true);
            setVisibilityPreview(false, false, false);
        });

        addDeckCloseBtn.setOnMouseClicked((event) -> {
            blurBackgroundAddDeck(false);
            setVisibilityAddDeckPrompt(false);
            addDeckTextField.setText("");
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
                    setVisibilityAddDeckPrompt(false);
                    addDeckTextField.setText("");
                    break;
            }
        });

        addCardBtn.setOnMouseClicked((event) -> {
            addQuestionTA.setEditable(true);
            setVisibilityAddQuestionPrompt(true);
            blurBackgroundAddQuestion(true);
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
                setVisibilityAddQuestionPrompt(false);
                blurBackgroundAddQuestion(false);
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
                        setVisibilityAddQuestionPrompt(false);
                        blurBackgroundAddQuestion(false);
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
            setVisibilityAddQuestionPrompt(true);
            displayQuestion();
            blurBackgroundAddQuestion(true);
        });

        congratsBtn.setOnMouseClicked((event) -> {
            setVisibilityCongrats(false);
            blurBackgroundCongrats(false);
        });

        isQuiz = false;
        currentCardIndex = -1;
        setVisibilityAddDeckPrompt(false);
        setVisibilityPreview(false, false, false);
        setVisibilityAddQuestionPrompt(false);
        setVisibilityCongrats(false);
        deckListGrid.setAlignment(Pos.TOP_CENTER);
        display(currentUser);
    }

    private void display(User user) {
        ArrayList<Deck> decks = user.getQuizzler().getDecks();
        if (decks.size() < 15) {
            createDeckBtn.setLayoutY((double)(118 + user.getQuizzler().getDecks().size() * 35));
        } else {
            createDeckBtn.setLayoutY(575.0);
        }
        deckListGrid.getChildren().clear();

        for(Deck d: decks) {
            HBox deckItem = new HBox();
            deckItem.setSpacing(5.0);
            deckItem.setPrefWidth(250.0);
            deckItem.getStyleClass().add("deckItem");

            Rectangle r = new Rectangle();
            r.setHeight(10.0);
            r.setWidth(30.0);
            r.setArcWidth(20.0);
            r.setArcHeight(20.0);

            Label title = new Label(d.getDeckName());
            title.getStyleClass().add("deckTitle");

            deckItem.getChildren().addAll(r, title);
            deckItem.setAlignment(Pos.CENTER_LEFT);
            deckItem.setOnMouseClicked((event) -> {
                currentDeck = d;
                previewDeck();
            });
            deckListGrid.add(deckItem, 0, deckListGrid.getChildren().size());
        }

    }

    private void addDeck(String deckName) {
        if (currentUser.getQuizzler().addDeck(new Deck(deckName))) {
            setVisibilityAddDeckPrompt(false);
            addDeckTextField.setText("");
            blurBackgroundAddDeck(false);
            display(currentUser);
        } else {
            blurBackgroundAddDeck(true);
        }

    }

    private void previewDeck() {
        setVisibilityAddDeckPrompt(false);
        boolean hasCards = !currentDeck.getItems().isEmpty();
        setVisibilityPreview(true, hasCards, hasCards);
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
            setVisibilityAddQuestionPrompt(false);
            blurBackgroundAddQuestion(false);
            addQuestionTA.setText("");
            addQuestionTF.setText("");
        }

    }

    private void displayPreviewCard() {
        previewCardTA.setText((String)currentDeck.getItems().get(currentCardIndex));
        previewCardTA.setEditable(false);
        previewCardTF.setText((String)currentDeck.getAnswerKey().get(currentCardIndex));
        previewCardTF.setEditable(false);
    }

    private void displayQuestion() {
        addQuestionTA.setText((String)quiz.get(currentCardIndex));
        addQuestionTA.setEditable(false);
        addQuestionTF.setText("");
    }

    private void setVisibilityAddDeckPrompt(boolean b) {
        addDeckBtn.setVisible(b);
        addDeckContainer.setVisible(b);
        addDeckLine.setVisible(b);
        addDeckLabel.setVisible(b);
        addDeckTextField.setVisible(b);
        addDeckCloseBtn.setVisible(b);
    }

    private void setVisibilityAddQuestionPrompt(boolean b) {
        addQuestionContainer.setVisible(b);
        addQuestionTA.setVisible(b);
        addQuestionTF.setVisible(b);
        addQuestionLine.setVisible(b);
        addQuestionSubmitBtn.setVisible(b);
        addQuestionCancelBtn.setVisible(b);
    }

    private void setVisibilityPreview(boolean b, boolean card, boolean navigation) {
        deckNamePreview.setVisible(b);
        addCardBtn.setVisible(b);
        previewTopLine.setVisible(b);
        downloadBtn.setVisible(b);

        previewCardContainer.setVisible(card);
        previewCardTA.setVisible(card);
        previewCardTF.setVisible(card);
        previewCardLine.setVisible(card);
        previewCardRemoveBtn.setVisible(card);
        playBtn.setVisible(card);
        previewCardRemoveBtn.setVisible(card);

        prevBtn.setVisible(navigation);
        nextBtn.setVisible(navigation);
    }

    private void setVisibilityCongrats(boolean b) {
        congratsContainer.setVisible(b);
        congratsLabel.setVisible(b);
        congratsLabelScore.setVisible(b);
        congratsLabelTotalScore.setVisible(b);
        congratsBtn.setVisible(b);
        congratsLine.setVisible(b);
        nerd.setVisible(b);
        winner.setVisible(b);
    }

    private void simulateQuiz() {
        quiz = currentDeck.constructQuiz();
        isQuiz = true;
        score = 0;
        currentCardIndex = 0;
        setVisibilityPreview(true, false, false);
    }

    private void submitAnswer(String question, String userAnswer) {
        if (currentDeck.evaluateAnswer(question, userAnswer)) {
            ++score;
        }

        if (++currentCardIndex < quiz.size()) {
            setVisibilityAddQuestionPrompt(true);
            displayQuestion();
            blurBackgroundAddQuestion(true);
        } else {
            endQuiz();
        }

    }

    private void endQuiz() {
        currentCardIndex = 0;
        isQuiz = false;
        addQuestionCancelBtn.setText("Cancel");
        setVisibilityAddQuestionPrompt(false);
        setVisibilityPreview(true, true, true);
        blurBackgroundAddQuestion(false);
        blurBackgroundCongrats(true);
        congratsLabelScore.setText(String.valueOf(score));
        congratsLabelTotalScore.setText(String.valueOf(currentDeck.getItems().size()));
        setVisibilityCongrats(true);
        previewDeck();
        System.out.println(score);
    }

    private void blurBackgroundAddDeck(boolean blur) {
        BoxBlur b = blur ? new BoxBlur() : null;
        rectangleBlur.setEffect(b);
        deckListRectangle.setEffect(b);
        decksHeader.setEffect(b);
        createDeckBtn.setEffect(b);
        deckListScrollPane.setEffect(b);
        deckNamePreview.setEffect(b);
        addCardBtn.setEffect(b);
        downloadBtn.setEffect(b);
        previewTopLine.setEffect(b);
    }

    private void blurBackgroundAddQuestion(boolean blur) {
        BoxBlur b = blur ? new BoxBlur() : null;
        rectangleBlur.setEffect(b);
        deckListRectangle.setEffect(b);
        decksHeader.setEffect(b);
        createDeckBtn.setEffect(b);
        deckListScrollPane.setEffect(b);
        deckNamePreview.setEffect(b);
        addCardBtn.setEffect(b);
        downloadBtn.setEffect(b);
        previewTopLine.setEffect(b);
        previewCardContainer.setEffect(b);
        previewCardTA.setEffect(b);
        previewCardTF.setEffect(b);
        previewCardLine.setEffect(b);
        nextBtn.setEffect(b);
        prevBtn.setEffect(b);
        playBtn.setEffect(b);
        previewCardRemoveBtn.setEffect(b);
        previewCardRemoveBtn.setEffect(b);
    }

    private void blurBackgroundCongrats(boolean blur) {
        BoxBlur b = blur ? new BoxBlur() : null;
        rectangleBlur.setEffect(b);
        deckListRectangle.setEffect(b);
        decksHeader.setEffect(b);
        createDeckBtn.setEffect(b);
        deckListScrollPane.setEffect(b);
        deckNamePreview.setEffect(b);
        addCardBtn.setEffect(b);
        downloadBtn.setEffect(b);
        previewTopLine.setEffect(b);
        previewCardContainer.setEffect(b);
        previewCardTA.setEffect(b);
        previewCardTF.setEffect(b);
        previewCardLine.setEffect(b);
        nextBtn.setEffect(b);
        prevBtn.setEffect(b);
        playBtn.setEffect(b);
        previewCardRemoveBtn.setEffect(b);
        previewCardRemoveBtn.setEffect(b);
    }
}
