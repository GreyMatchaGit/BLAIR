package lms.content;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Deck {
    private String deckName;
    private ArrayList<Card> cards;

    public Deck(String deckName) {
        this.deckName = deckName;
        cards = new ArrayList<>();
    }

    public String getDeckName() {
        return this.deckName;
    }
    public ArrayList<Card> getCards() { return this.cards; }

    public int addQuestion(String question, String answer) {
        if(question.equals("") || answer.equals("")) {
            System.err.println("Questions and Answers should not be empty!");
            return 1;
        }

        for(Card c : cards) {
            if(c.getQuestion().equals(question)) {
                System.err.println("Questions should not be duplicated!");
                return 2;
            }
        }

        cards.add(new Card(question, answer));
        System.out.println("Question has been added to the deck successfully!");
        return 0;
    }

    public void removeQuestion(int index) {
        try {
            cards.remove(index);
        } catch (ArrayIndexOutOfBoundsException e) {
            System.err.println("Card does not exist!");
            e.printStackTrace();
        }

    }

    // TO FIX
    public ArrayList<Card> constructQuiz() {
        ArrayList<Card> randomizedCards = new ArrayList<>(cards);
        Collections.shuffle(randomizedCards);
        return randomizedCards;
    }

    public boolean evaluateAnswer(Card c, String userAnswer) {
        return userAnswer.equals(c.getAnswer());
    }
}