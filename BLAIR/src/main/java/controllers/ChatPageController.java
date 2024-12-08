package controllers;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import lms.LearningManagementSystem;
import lms.course.Prompt;
import services.DatabaseService;
import services.StringService;

import java.util.*;

public class ChatPageController {

    @FXML
    private TextField userInputField;
    @FXML
    private VBox contentArea;
    @FXML
    private Label welcomeLbl;
    @FXML
    private StackPane welcomeContainer;

    private static final Set<String> STOP_WORDS = new HashSet<>(Arrays.asList(
            "but", "or", "for", "it", "a", "an", "and", "the",
            "to", "of", "in", "on", "at", "with", "as", "by",
            "this", "that", "which", "who", "whom", "how",
            "where", "when", "why"
    ));

    private Map<String, Prompt> prompts;

    @FXML
    public void initialize() {

         /*
         * Things to note, prompts ranging from the ff are categorized:
         *
         * Prompts 1-50 = CITU, school, academics, education
         * Prompts 51-100 = General stuffs, college life, student life
         * Prompts 101-150 = Programming, software engineering, computer science
         * Prompts 151-200 = General world facts stuffs
         * Prompts 201-225+ = Filler stuffs
         */

        String currUserName = LearningManagementSystem.getInstance().getCurrentUser().getFirstName();
        StringService.typewriterEffect(welcomeLbl, "Hello, " + currUserName + "! What can I help you with?");

        prompts = DatabaseService.getPromptDatabase();

        userInputField.setOnKeyReleased(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                handleUserInput();
                event.consume();
            }
        });
    }

    @FXML
    public void handleUserInput() {
        welcomeContainer.setVisible(false);

        String userInput = userInputField.getText();

        contentArea.getChildren().addAll(displayUserBox(userInput), displayResponseBox(findBestMatch(userInput)));

        userInputField.clear();
    }

    private VBox displayUserBox(String userInput) {
        VBox userBox = new VBox();
        userBox.setAlignment(Pos.CENTER_RIGHT);
        userBox.setMinHeight(60);
        userBox.setMaxHeight(60);
        VBox.setMargin(userBox, new Insets(0, 0, 10, 0));

        Label userLabel = new Label(userInput);
        userLabel.getStyleClass().add("user-message");
        userLabel.setWrapText(true);
        userLabel.setMinHeight(60);
        userLabel.setMaxWidth(500);

        userBox.getChildren().add(userLabel);
        return userBox;
    }

    private VBox displayResponseBox(String response) {
        VBox responseBox = new VBox();
        responseBox.setAlignment(Pos.CENTER_LEFT);
        responseBox.setMinHeight(60);
        responseBox.setMaxHeight(60);
        VBox.setMargin(responseBox, new Insets(0, 0, 0, 10));

        Label responseLabel = new Label(response);
        responseLabel.getStyleClass().add("bot-message");
        responseLabel.setWrapText(true);
        responseLabel.setMinHeight(60);
        responseLabel.setMaxWidth(500);

        responseBox.getChildren().add(responseLabel);
        return responseBox;
    }


    /* ------------- ChatBot Algorithms ------------- */

    private String findBestMatch(String userInput) {
        String bestMatchResponse = null;
        int maxMatches = 0;

        Set<String> userWords = cleanupInput(userInput);

        for (Map.Entry<String, Prompt> entry : prompts.entrySet()) {
            String question = entry.getValue().getQuestion();
            Set<String> promptWords = cleanupInput(question);

            int matchCount = countMatches(userWords, promptWords);

            if (matchCount > maxMatches) {
                maxMatches = matchCount;
                bestMatchResponse = entry.getValue().getResponse();
            }
        }

        return bestMatchResponse != null ? bestMatchResponse : "Sorry, I couldn't quite get that.";
    }

    private Set<String> cleanupInput(String input) {
        Set<String> words = new HashSet<>();
        String[] tokens = input.toLowerCase().split("\\W+");
        for (String token : tokens) {
            if (!STOP_WORDS.contains(token) && !token.isEmpty()) {
                words.add(token);
            }
        }
        return words;
    }

    private int countMatches(Set<String> userWords, Set<String> promptWords) {
        int count = 0;
        for (String word : userWords) {
            if (promptWords.contains(word)) {
                count++;
            }
        }
        return count;
    }
}