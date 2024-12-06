package controllers;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import lms.content.Prompt;
import services.DatabaseService;
import services.StringService;

import java.util.*;

public class ChatPageController {

    @FXML
    private TextField userInputField;
    @FXML
    private TextArea chatArea;

    private static final Set<String> STOP_WORDS = new HashSet<>(Arrays.asList(
            "is", "but", "or", "for", "it", "a", "an", "and", "the",
            "to", "of", "in", "on", "at", "with", "as", "by",
            "this", "that", "which", "who", "whom", "what",
            "where", "when", "why", "how"
    ));

    private Map<String, Prompt> prompts;

    @FXML
    public void initialize() {

        /* Things to note, prompts ranging from the ff are categorized:

           Prompts 1-50 = CITU, school, academics, education
           Prompts 51-100 = General stuffs, college life, student life
           Prompts 101-150 = Programming, software engineering, computer science
           Prompts 151-200 = General world facts stuffs
           Prompts 201-225+ = Filler stuffs
         */
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
        String userInput = userInputField.getText();
        String response = findBestMatch(userInput);

        System.out.println(response);

        userInputField.clear();
    }

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