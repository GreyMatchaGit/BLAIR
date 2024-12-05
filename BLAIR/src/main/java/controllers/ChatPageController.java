package controllers;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
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

    @FXML
    public void initialize() {
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

        // Temp output for responses
        if (response != null) {
            System.out.println("CatBot said: " + response);
        }
        userInputField.clear();
    }

    private String findBestMatch(String userInput) {
        String jsonResource = StringService.convertFrom(
                Objects.requireNonNull(DatabaseService.class.getResource("/json/"))
        );

        String promptsPath = jsonResource + "prompts.json";

        Map<String, String> prompts = loadPrompts(promptsPath);

        String bestMatchResponse = null;
        int maxMatches = 0;

        Set<String> userWords = cleanupInput(userInput);

        for (Map.Entry<String, String> entry : prompts.entrySet()) {
            String question = entry.getKey();
            System.out.println("Question is: " + question);
            Set<String> promptWords = cleanupInput  (question);

            int matchCount = countMatches(userWords, promptWords);

            if (matchCount > maxMatches) {
                maxMatches = matchCount;
                bestMatchResponse = entry.getValue();
            }
        }

        return bestMatchResponse != null ? bestMatchResponse : "Sorry, I couldn't find a matching response.";
    }

    private Map<String, String> loadPrompts(String filePath) {

        return Collections.emptyMap();
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