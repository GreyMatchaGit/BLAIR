package LMS.Quiz;

import java.util.*;

public class Quizzler {
    private ArrayList<Deck> decks;
    
    public Quizzler() {
        decks = new ArrayList<>();
    }
    
    public ArrayList<Deck> getDecks() { return decks; }
     
    public void addDeck(Deck deck) {
        decks.add(deck);
        System.out.println("Deck " + deck.getDeckName()
                + " has been added successfully to the Board of Decks");
    }

    public void removeDeck(String deckName) {
        for(Deck d : decks) {
            if(d.getDeckName().equals(deckName)) {
                decks.remove(d);
                System.out.println("Deck " + deckName
                        + " removed successfully from the Board of Decks");
                return;
            }
        }
        System.out.println(deckName + " does not exist within the Board of Decks");
    }

    public void downloadDeck() {

    }
}
