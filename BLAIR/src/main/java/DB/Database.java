package DB;

import LMS.User;
import LMS.UserType.Admin;
import LMS.UserType.Student;
import LMS.UserType.Teacher;

import java.util.ArrayList;

public abstract class Database {
    private static ArrayList<UserDetails> userDatabase = null;

    public Database(ArrayList<UserDetails> userDatabase) {
        if (Database.userDatabase != null) {
            Database.userDatabase = userDatabase;
        } else {
            throw new RuntimeException("User database already exists.");
        }
    }

    public User login(String username, String password) {
        // temporary
        if (username.equals("admin") && password.equals("123")) {
            return new Admin("-1");
        }

        UserDetails currentUser = null;

        // Search for the username in the database.
        for (UserDetails user: userDatabase) {
             if (user.getUsername().equals(username)) {
                 currentUser = user;
             }
        }

        if (currentUser == null) {
            // If the username doesn't exist in the database,
            // then an exception is thrown.
            throw new RuntimeException("The username doesn't exist in the database.");
        }

        if (!(currentUser.getPassword().equals(password))) {
            // If the password doesn't match with the username,
            // then an exception is thrown.
            throw new RuntimeException("Password does not match.");
        }

        return currentUser.getUser();
    }

    public void registerStudent(String id, String firstName, String middleName, String lastName, String email) {

        for (UserDetails each : userDatabase)  {
           if (each.getUser().getId().equals(id)) {
               throw new RuntimeException("User with the same ID already exists.");
           }
        }

        User user = new Student(id);
        user.setName(firstName, middleName, lastName);
        user.setEmail(email);
        user.setCourses(new ArrayList<String>());

        UserDetails userDetails = new UserDetails();
        userDetails.setUsername(user.generateUsername());
        userDetails.setPassword(user.generateDefaultPass());
        userDetails.setUser(user);

        userDatabase.add(userDetails);
    }

    public void registerTeacher(String id, String firstName, String middleName, String lastName, String email) {

        for (UserDetails each : userDatabase)  {
            if (each.getUser().getId().equals(id)) {
                throw new RuntimeException("User with the same ID already exists.");
            }
        }

        User user = new Teacher(id);
        user.setName(firstName, middleName, lastName);
        user.setEmail(email);
        user.setCourses(new ArrayList<String>());

        UserDetails userDetails = new UserDetails();
        userDetails.setUsername(user.generateUsername());
        userDetails.setPassword(user.generateDefaultPass());
        userDetails.setUser(user);

        userDatabase.add(userDetails);
    }


}