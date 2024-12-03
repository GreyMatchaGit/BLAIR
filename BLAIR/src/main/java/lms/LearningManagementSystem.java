package lms;

public class LearningManagementSystem {
    private static LearningManagementSystem instance = null; // For the singleton creational DP, ensures nga usa ra ka lms across the project
    private User currentUser ;

    private LearningManagementSystem() {}

    public static LearningManagementSystem getInstance() {

        if (instance == null)
            instance = new LearningManagementSystem();

        return instance;
    }

    public void setCurrentUser (User user) {
        this.currentUser  = user;
    }

    public User getCurrentUser () {
        return currentUser ;
    }

}
