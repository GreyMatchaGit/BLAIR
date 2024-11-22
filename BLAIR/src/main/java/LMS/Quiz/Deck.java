package LMS.Quiz;

import java.util.ArrayList;
import java.util.Collections;

public class Deck {
    private String deck_name;
    private ArrayList<String> items;

    public Deck(String deck_name) {
        this.deck_name = deck_name;
        items = new ArrayList<>();
    }

    public void add_question(String question) {
        items.add(question);
    }

    public void remove_question(int num) {
        items.remove(items.get(num-1));
    }

    public void shuffle_questions() {
        Collections.shuffle(items);
    }

    public void remove_all() {
        items.clear();
    }

}
