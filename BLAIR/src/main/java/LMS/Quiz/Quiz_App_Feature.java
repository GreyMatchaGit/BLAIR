package LMS.Quiz;

import java.util.ArrayList;

public class Quiz_App_Feature {
    private ArrayList<Deck> quizzes;

    public Quiz_App_Feature() {
        quizzes = new ArrayList<>();
    }

    public void add_deck(Deck deck) {
        quizzes.add(deck);
    }

    public void remove_deck(String deck_name) {

    }

    public void download_deck() {
        // how tf
    }
}
