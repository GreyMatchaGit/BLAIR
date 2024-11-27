package LMS;

import DB.Database;

public class LearningManagementSystem {
    private static LearningManagementSystem instance; // For the singleton creational DP, ensures nga usa ra ka lms across the project
    private Database database;
    private User currentUser ;

    private LearningManagementSystem(Database database) {
        this.database = database;
    }

    public static LearningManagementSystem getInstance(Database database) { // For the singleton creational DP, ensures nga usa ra ka lms across the project
        if (instance == null) {
            instance = new LearningManagementSystem(database);
        }
        return instance;
    }

    public void setCurrentUser (User user) {
        this.currentUser  = user;
    }

    public User getCurrentUser () {
        return currentUser ;
    }

}
