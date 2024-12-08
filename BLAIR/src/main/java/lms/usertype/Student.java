package lms.usertype;

import java.util.ArrayList;
import java.util.SortedMap;

public class Student extends User {

    private ArrayList<String> courses;
    private ArrayList<String> tasks;
    private ArrayList<String> decks;
    private String program;
    private String yearLevel;

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

    public ArrayList<String> getDecks() { return decks; }

    public void setDecks(ArrayList<String> decks) { this.decks = decks; }
}
