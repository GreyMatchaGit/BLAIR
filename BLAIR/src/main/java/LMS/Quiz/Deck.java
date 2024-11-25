package LMS.Quiz;

import java.util.*;

public class Deck {
    private String deckName;
    private HashMap<Integer, String> items;
    private ArrayList<String> answerKey;

    public Deck(String deckName) {
        this.deckName = deckName;
        answerKey = new ArrayList<>();
        items = new HashMap<>();
    }

    public String getDeckName() { return deckName; }
    public void setDeckName(String deckName) { this.deckName = deckName; }

    public void addQuestion(String question, String answer) {
        items.put(answerKey.size(), question);
        answerKey.add(answer);
    }

    public void removeQuestion(int num) {
        try {
            items.remove(num);
            answerKey.remove(num-1);
        } catch (ArrayIndexOutOfBoundsException e) {
            System.err.println("Item does not exist!");
            e.printStackTrace();
        }
    }

    public void shuffleQuestions() {
        // I'm not sure with this one yet
    }

    public void removeAll() {
        items.clear();
        answerKey.clear();
    }

    public void quizMeCapisce() {
        int score=0;
        Scanner sc = new Scanner(System.in);

        for(Map.Entry<Integer, String> entry : items.entrySet()) {
            System.out.println(entry.getKey() + ". " + entry.getKey() + "?\nAnswer: ");
            String answer = sc.nextLine();
            if(answer.equalsIgnoreCase(answerKey.get(entry.getKey()-1))) {
                score++;
            }
        }
        System.out.println("Your final score is " + score + "/" + answerKey.size());
    }
}

