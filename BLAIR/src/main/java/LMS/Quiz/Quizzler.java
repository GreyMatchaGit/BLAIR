package LMS.Quiz;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class Quizzler {
    private ArrayList<Deck> decks;

    public Quizzler() {
        decks = new ArrayList();
    }

    public ArrayList<Deck> getDecks() { return this.decks; }

    public boolean addDeck(Deck deck) {

        if (deck.getDeckName().trim().isEmpty()) {
            return false;
        } else {

            for(Deck d: decks) {
                if(d.getDeckName().equals(deck.getDeckName())) {
                    System.err.println("Deck Name already exists");
                    return false;
                }
            }

            decks.add(deck);
            System.out.println("Deck " + deck.getDeckName() + " has been added successfully to the Board of Decks");
            return true;
        }
    }

    public void removeDeck(String deckName) {

        System.out.println(deckName + " does not exist within the Board of Decks");
        System.out.println("Deck " + deckName + " removed successfully from the Board of Decks");
    }

    public void downloadDeck() {
    }
}