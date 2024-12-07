package lms.content;

import lms.User;
import lms.usertype.Student;

import java.util.ArrayList;

public class Quizzler {
    private ArrayList<Deck> decks;
    private int currentDeckIndex;

    public Quizzler(User currentUser) {
        assert currentUser instanceof  Student;
        decks = ((Student) currentUser).getDecks();
        currentDeckIndex = -1;
    }

    public ArrayList<Deck> getDecks() { return decks; }
    public int getCurrentDeckIndex() { return currentDeckIndex; }

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

    public void removeDeck() {
        try {
            String deckName = currentDeck().getDeckName();
            decks.remove(currentDeckIndex);
            System.out.println("Deck " + deckName + " has been successfully removed!");
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    public Deck currentDeck() {
        Deck currentDeck = null;
        try {
            currentDeck = decks.get(currentDeckIndex);
        } catch (ArrayIndexOutOfBoundsException e) {
            System.err.println(e.getMessage());
        }
        return currentDeck;
    }

    public void setCurrentDeckIndex(Deck d) {
        currentDeckIndex = decks.indexOf(d);
    }

    public int renameDeck(String deckName) {
        if(deckName.equals("")) {
            return 0;
        } else if(deckName.equals(currentDeck().getDeckName())) {
            return 1;
        } else {
            for(Deck d: decks) {
                if(d.getDeckName().equals(deckName)) {
                    System.err.println("Deck Name already exists");
                    return 2;
                }
            }
            String prevName = currentDeck().getDeckName();
            currentDeck().setDeckName(deckName);
            System.out.println("Deck " + prevName + " has been added successfully renamed to " +
                    deckName);

            for(Deck d: decks) {
                System.out.println(d.getDeckName());
            }

            return 3;
        }
    }
}