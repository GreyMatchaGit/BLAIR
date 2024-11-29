package LMS;

import LMS.UserType.Student;
import LMS.UserType.Teacher;

import java.util.ArrayList;

public abstract class User {
    private String type;
    private final String id;
    private String firstName;
    private String lastName;
    private String middleName;
    private String email;
    private ArrayList<Course> courses;

    protected User() {
        this.id = null;
        setFullName(null, null, null);
        email = null;
        courses = null;
    }

    public User(String id, String firstName, String middleName, String lastName, String email) {
        this.id = id;
        setFullName(firstName, middleName, lastName);
        this.email = email;
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

    @Override
    public String toString() {
        return firstName + " " + middleName + " " + lastName;
    }

    public String generateUsername() {
        return String.format(
                "%s.%s",
                firstName.replaceAll("\\s", ""),
                lastName
        ).toLowerCase();
    }

    public String generateDefaultPass() {
        return String.format(
                "%s.123456",
                lastName
        );
    }

    public ArrayList<Course> getCourses() {
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

    public void setCourses(ArrayList<Course> courses) {
        this.courses = courses;
    }
}

