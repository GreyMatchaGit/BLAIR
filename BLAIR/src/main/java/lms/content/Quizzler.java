package lms.content;

import java.util.ArrayList;

public class Quizzler {
    private ArrayList<Deck> decks;

    public Quizzler() {
        decks = new ArrayList();
    }

    public ArrayList<Deck> getDecks() { return this.decks; }


    //
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
        Deck toDelete = null;
        for(Deck d: decks) {
            if(d.getDeckName().equals(deckName)) {
                toDelete = d;
            }
        }

        if(toDelete != null) {
            decks.remove(toDelete);
            System.out.println("Deck " + deckName + " removed successfully from the Board of Decks");
        } else {
            System.err.println("Deck " + deckName + " does not exist from the Board of Decks");
        }
    }

}