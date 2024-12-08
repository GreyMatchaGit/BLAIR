package lms.quizzler;

import services.StringService;

import java.util.ArrayList;
import java.util.Collections;

public class Deck {
    private String deckName;
    private ArrayList<Card> cards;
    private ArrayList<Card> quizCards;
    private boolean isQuiz;
    private int currentCardIndex;
    private int score;
    private String key;

    public Deck(String deckName) {
        this.deckName = deckName;
        cards = new ArrayList<>();
        isQuiz = false;
        currentCardIndex = -1;
        score = 0;
        key = StringService.generateKey(deckName);
    }

    public String getDeckName() {
        return deckName;
    }
    public void setDeckName(String deckName) { this.deckName = deckName; }

    public ArrayList<Card> getCards() { return cards; }
    public void setCards(ArrayList<Card> cards) { this.cards = cards; }

    public ArrayList<Card> getQuizCards() { return quizCards; }
    public void setQuizCards(ArrayList<Card> quizCards) { this.quizCards = quizCards; }

    public boolean isQuiz() { return isQuiz; }
    public void setQuiz(boolean quiz) { isQuiz = quiz; }

    public int getCurrentCardIndex() { return currentCardIndex; }
    public void setCurrentCardIndex(int currentCardIndex) { this.currentCardIndex = currentCardIndex; }

    public int getScore() { return score; }
    public void setScore(int score) { this.score = score; }

    public String getKey() { return key; }
    public void setKey(String deckName) { this.key = StringService.generateKey(deckName); }

    public int addCard(String question, String answer) {
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
        return 0;
    }

    public void removeCard() {
        try {
            cards.remove(currentCardIndex);
            --currentCardIndex;
            if(!cards.isEmpty()) {
                currentCardIndex = 0;
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            System.err.println("Card does not exist!");
            e.printStackTrace();
        }

    }

    public int renameCard(String question, String answer) {

        if(question.equals(currentCard().getQuestion())) {
            currentCard().setAnswer(answer);
            return 2;
        }

        if(question.equals("") || answer.equals("")) {
            System.err.println("Questions and Answers should not be empty!");
            return 0;
        }

        for(Card c : cards) {
            if(c.getQuestion().equals(question)) {
                System.err.println("Questions should not be duplicated!");
                return 1;
            }
        }

        currentCard().setQuestion(question);
        currentCard().setAnswer(answer);
        return 2;
    }

    public void previousCard() {
        --currentCardIndex;
        if (currentCardIndex < 0) {
            currentCardIndex = cards.size() - 1;
        }
    }

    public void nextCard() {
        ++currentCardIndex;
        if (currentCardIndex > cards.size()-1) {
            currentCardIndex = 0;
        }
    }

    public Card currentCard() {
        return cards.get(currentCardIndex);
    }

    public Card currentQuizCard() {
        return quizCards.get(currentCardIndex);
    }

    public void constructQuiz() {
        if(quizCards != null) {
            quizCards.clear();
        }
        quizCards = new ArrayList<>(cards);
        Collections.shuffle(quizCards);
    }

    public void evaluateAnswer(String userAnswer) {
        if(userAnswer.equals(quizCards.get(currentCardIndex).getAnswer())) {
            score++;
        }
    }

    public boolean isQuizDone() {
        return ++currentCardIndex > quizCards.size()-1;
    }
}