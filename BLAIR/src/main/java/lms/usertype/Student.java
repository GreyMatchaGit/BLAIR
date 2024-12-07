package lms.usertype;

import lms.User;
import lms.content.Card;
import lms.content.Deck;

import java.util.ArrayList;
import java.util.SortedMap;

public class Student extends User {

    private SortedMap<String, Integer> grades;
    private ArrayList<String> courses;
    private ArrayList<String> tasks;
    private ArrayList<Deck> decks;
    private String program;
    private String yearLevel;

    public Student() {
        setType("Student");
        decks = new ArrayList<>();
        courses = new ArrayList<>();
        tasks = new ArrayList<>();
    }

    public Student(String id, String firstName, String middleName, String lastName, String email, String program, String yearLevel) {
        super(id, firstName, middleName, lastName, email);
        setType("Student");
        this.program = program;
        this.yearLevel = yearLevel;
        decks = new ArrayList<>();
        courses = new ArrayList<>();
        tasks = new ArrayList<>();
    }

    public void setProgram(String program) {
        this.program = program;
    }

    public void setYearLevel(String yearLevel) {
        this.yearLevel = yearLevel;
    }

    public String getProgram() {
        return program;
    }

    public String getYearLevel() {
        return yearLevel;
    }

    public ArrayList<String> getCourses() {
        return courses;
    }

    public ArrayList<String> getTasks() { return tasks; }

    public void setTasks(ArrayList<String> tasks) {
        this.tasks = tasks;
    }

    public void setCourses(ArrayList<String> courses) {
        this.courses = courses;
    }

    public ArrayList<Deck> getDecks() { return decks; }

    public void setDecks(ArrayList<Deck> decks) { this.decks = decks; }
}
