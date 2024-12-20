package services;

import lms.LearningManagementSystem;
import lms.usertype.User;
import lms.usertype.Student;

import java.util.ArrayList;

public class UserService {

    /*
        This service is responsible for all the getters and setters from
        the current user. The main goal is to provide the user with what it
        can do from here. Apart from that, the service reduces the verbosity
        from the controller codes by doing all the casting from here.
     */

    private static UserService instance;
    private User currentUser;

    private UserService() {}

    public static UserService getInstance() {

        if (instance == null) {
            instance = new UserService();
        }

        return instance;
    }

    public void setCurrentUser() {

        currentUser = LearningManagementSystem
                .getInstance()
                .getCurrentUser();
    }

    public ArrayList<String> getTasks() {
        setCurrentUser();
        return ((Student) currentUser).getTasks();
    }

    public ArrayList<String> getCourses() {
        return ((Student)currentUser).getCourses();
    }

    public String getUserName() {
        return currentUser.getUsername();
    }

    public void setCourses(ArrayList<String> courses) {
        ((Student) currentUser).setCourses(courses);
    }

    public ArrayList<String> getDecks() { return ((Student)currentUser).getDecks(); }

    public void setDecks(ArrayList<String> decks) {
        ((Student) currentUser).setDecks(decks);
    }
}
