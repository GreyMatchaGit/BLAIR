package lms.content;

import java.util.ArrayList;
import java.util.Random;

public class Deck {
    private String deckName;
    private ArrayList<String> items;
    private ArrayList<String> answerKey;

    public Deck(String deckName) {
        this.deckName = deckName;
        this.answerKey = new ArrayList();
        this.items = new ArrayList();
    }

    public String getDeckName() {
        return this.deckName;
    }

    public ArrayList<String> getItems() { return this.items; }
    public ArrayList<String> getAnswerKey() { return this.answerKey; }

    public void setDeckName(String deckName) { this.deckName = deckName; }
    public void setItems(ArrayList<String> items) { this.items = items; }

    public void setAnswerKey(ArrayList<String> answerKey) { this.answerKey = answerKey; }

    public void addQuestion(String question, String answer) {
        this.items.add(question);
        this.answerKey.add(answer);
        System.out.println("Question has been added to the deck successfully!");
    }

    public void removeQuestion(int index) {
        try {
            this.items.remove(index);
            this.answerKey.remove(index);
        } catch (ArrayIndexOutOfBoundsException var3) {
            ArrayIndexOutOfBoundsException e = var3;
            System.err.println("Item does not exist!");
            e.printStackTrace();
        }

    }

    public void removeAll() {
        this.items.clear();
        this.answerKey.clear();
    }

    public ArrayList<String> constructQuiz() {
        ArrayList<String> randomizedItems = new ArrayList();
        Random random = new Random();

        for(int i = 0; i < items.size(); ++i) {
            int index;
            while(true) {
                index = random.nextInt(items.size());
                if(!randomizedItems.contains(items.get(index))) {
                    randomizedItems.add(items.get(index));
                }
            }
        }

        return randomizedItems;
    }

    public boolean evaluateAnswer(String question, String userAnswer) {
        int index = items.indexOf(question);
        return (answerKey.get(index)).equals(userAnswer);
    }
}