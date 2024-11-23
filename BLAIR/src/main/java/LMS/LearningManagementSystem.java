package LMS;

public class LearningManagementSystem {
    private static LearningManagementSystem instance; // For the singleton creational DP, ensures nga usa ra ka lms across the project
    private DB.Database database;
    private User currentUser ;

    private LearningManagementSystem(DB.Database database) {
        this.database = database;
    }

    public static LearningManagementSystem getInstance(DB.Database database) {
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