package lms;

import lms.content.Quizzler;

import java.util.ArrayList;

public abstract class User {
    private String type;
    private final String id;
    private String firstName;
    private String lastName;
    private String middleName;
    private String email;
    private ArrayList<String> courses;
    private Quizzler quizzler;

    public User() {
        this.id = "-1";
    }

    public User(String id, String firstName, String middleName, String lastName, String email) {

        setFullName(firstName, middleName, lastName);
        this.id = id;
        this.email = email;
        courses = new ArrayList<>();
        quizzler = new Quizzler();
    }

    public String getEmail() {
        return email;
    }

    public String getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public String getFullName() {
        return String.format("%s %s %s", firstName, middleName, lastName);
    }

    public ArrayList<String> getCourses() {
        return courses;
    }

    public void setFullName(String firstName, String middleName, String lastName) {
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
    }

    protected void setType(String type) {
        this.type = type;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setCourses(ArrayList<String> courses) {
        this.courses = courses;
    }

    public Quizzler getQuizzler() { return quizzler; }

    @Override
    public String toString() {
        return getFullName();
    }
}

