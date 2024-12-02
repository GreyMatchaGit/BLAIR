package lms.content;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Deck {
    private String deckName;
    private String owner;
    private String code;
    private ArrayList<String> items;
    private ArrayList<String> answerKey;

    public Deck(String deckName) {
        this.deckName = deckName;
        this.answerKey = new ArrayList();
        this.items = new ArrayList();
    }

    public String getCode() {
        return code;
    }

    public String getDeckName() {
        return this.deckName;
    }

    public ArrayList<String> getItems() { return this.items; }
    public ArrayList<String> getAnswerKey() { return this.answerKey; }

    public void setDeckName(String deckName) { this.deckName = deckName; }
    public void setItems(ArrayList<String> items) { this.items = items; }
    public void setAnswerKey(ArrayList<String> answerKey) { this.answerKey = answerKey; }

    public boolean addQuestion(String question, String answer) {
        if(items.contains(question) || question.equals("")) {
            System.err.println("Questions should not be duplicated");
            return false;
        }
        items.add(question);
        answerKey.add(answer);
        System.out.println("Question has been added to the deck successfully!");
        return true;
    }

    public void removeQuestion(int index) {
        try {
            items.remove(index);
            answerKey.remove(index);
        } catch (ArrayIndexOutOfBoundsException e) {
            System.err.println("Item does not exist!");
            e.printStackTrace();
        }

    }

    public ArrayList<String> constructQuiz() {
        ArrayList<String> randomizedItems = new ArrayList<>(items);
        Collections.shuffle(randomizedItems);
        return randomizedItems;
    }

    public boolean evaluateAnswer(String question, String userAnswer) {
        int index = items.indexOf(question);
        return (answerKey.get(index)).equals(userAnswer);
    }
}