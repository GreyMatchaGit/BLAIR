package LMS;

public class LearningManagementSystem {
    DB.Database database;
    User user;

    public LearningManagementSystem(DB.Database database, User currentUser) {
        this.database = database;
        this.user = currentUser;
    }
}