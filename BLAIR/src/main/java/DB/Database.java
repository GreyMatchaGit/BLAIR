package DB;

import Utilities.UserBuilder;
import LMS.User;
import LMS.UserType.Admin;

import java.util.HashMap;

public abstract class Database {
    public static Database instance;
    public static HashMap<String, UserDetails> userDatabase = null;

    public Database(HashMap<String, UserDetails> userDatabase) {
        if (Database.userDatabase == null) {
            Database.userDatabase = userDatabase;
        } else {
            throw new RuntimeException("Database already exists");
        }

        instance = this;
    }

    public static Database getInstance() {
        return instance;
    }

    public static User login(String username, String password) {
        // Temporary login logic
        if (username.equals("admin") && password.equals("123")) {
            return new Admin("-1");
        }

        UserDetails currentUser  = null;

        // Search for the username in the database.
        if (userDatabase.containsKey(username)) {
            currentUser = userDatabase.get(username);
        }

        if (currentUser  == null) {
            throw new RuntimeException("The username doesn't exist in the database.");
        }

        if (!(currentUser.getPassword().equals(password))) {
            throw new RuntimeException("Password does not match.");
        }

        return currentUser .getUser();
    }

    public static void registerStudent(String id, String firstName, String middleName, String lastName, String email) {
        User user = new UserBuilder(id)
                .setFullName(firstName, middleName, lastName)
                .setEmail(email)
                .createStudent();

        UserDetails userDetails = new UserDetails(user);

        userDatabase.put(userDetails.getUsername(), userDetails);
    }

    public static void registerTeacher(String id, String firstName, String middleName, String lastName, String email) {
        User user = new UserBuilder(id)
                .setFullName(firstName, middleName, lastName)
                .setEmail(email)
                .createTeacher();

        UserDetails userDetails = new UserDetails(user);

        userDatabase.put(userDetails.getUsername(), userDetails);
    }
}