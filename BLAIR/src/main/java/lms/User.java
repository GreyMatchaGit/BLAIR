package lms;

import lms.content.Quizzler;
import services.StringService;

import java.util.ArrayList;

public abstract class User {

    private final String id;
    private String type;
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String middleName;
    private String email;

    public User(String id, String firstName, String middleName, String lastName, String email) {

        setFullName(firstName, middleName, lastName);
        this.id = id;
        this.email = email;
        this.username = StringService.defaultUsername(this);
        this.password = StringService.defaultPassword(this);
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

    public String getType() {
        return type;
    }

    public String getFullName() {
        return String.format("%s %s %s", firstName, middleName, lastName);
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

    @Override
    public String toString() {
        return getFullName();
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String newPassword) {
        password = newPassword;
    }
}

