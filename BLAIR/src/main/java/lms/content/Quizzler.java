package lms.content;

import database.Database;
import lms.User;
import lms.usertype.Student;

import java.util.ArrayList;

public class Quizzler {
    private ArrayList<Deck> decks;
    private int currentDeckIndex;

    public Quizzler() {
        decks = new ArrayList<>();
        currentDeckIndex = -1;
    }

    public void initialize(User currentUser) {
        ArrayList<String> keys = ((Student) currentUser).getDecks();

        for (String key : keys) {
            Deck d = Database.deckDatabase.get(key);
            if (d != null)
                decks.add(d);
        }
    }

    public ArrayList<Deck> getDecks() { return decks; }

    public boolean addDeck(Deck deck) {
        if (deck.getDeckName().trim().isEmpty()) {
            return false;
        } else {
            for(Deck d: decks) {
                if(d.getKey().equals(deck.getKey())) {
                    System.err.println("Deck " + deck.getDeckName() + " already exists");
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
            System.out.println(currentDeck().getDeckName());
            String prevName = currentDeck().getDeckName();
            currentDeck().setDeckName(deckName);
            currentDeck().setKey(deckName);
            System.out.println("Deck " + prevName + " has been added successfully renamed to " +
                    deckName);

            for(Deck d: decks) {
                System.out.println(d.getDeckName());
            }

            return 3;
        }
    }

    public void saveDecks(User user) {
        ArrayList<String> currentDecks = new ArrayList<>();

        for(Deck d: decks) {
            d.getquizCards().clear();
            Database.deckDatabase.put(d.getKey(), d);
            currentDecks.add(d.getKey());
        }

        decks.clear();
        ((Student)user).setDecks(currentDecks);
    }
}