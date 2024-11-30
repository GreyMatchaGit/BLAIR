package lms;

public class LearningManagementSystem {
    private static LearningManagementSystem instance = null; // For the singleton creational DP, ensures nga usa ra ka lms across the project
    private User currentUser ;

    private LearningManagementSystem() {}

    public static LearningManagementSystem getInstance(User currentUser) { // For the singleton creational DP, ensures nga usa ra ka lms across the project

        if (instance == null) {
            instance = new LearningManagementSystem();
            instance.setCurrentUser(currentUser);
        }
        return instance;
    }

    public static LearningManagementSystem getInstance() {
        if (instance == null) {
            throw new RuntimeException("LMS hasn't been initialized yet. Provide the user in the argument to initialize.");
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
