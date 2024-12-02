package DB;

import LMS.Course;
import LMS.UserType.Teacher;
import Utilities.CourseBuilder;
import Utilities.StudentBuilder;
import Utilities.TeacherBuilder;
import LMS.User;
import LMS.UserType.Admin;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class Database {
    public static Database instance;
    public static HashMap<String, UserDetails> userDatabase = null;
    public static HashMap<String, Course> courseDatabase = null;

    public Database(HashMap<String, UserDetails> userDatabase, HashMap<String, Course> courseDatabase) {
        if (Database.userDatabase == null) {
            Database.userDatabase = userDatabase;
        } else {
            throw new RuntimeException("Database already exists");
        }

        if (Database.courseDatabase == null) {
            Database.courseDatabase = courseDatabase;
        } else {
            throw new RuntimeException("Course database already exists");
        }

        if (instance == null)
            instance = this;
    }

    public static Database getInstance() {
        return instance;
    }

    public static User login(String username, String password) {
        // Temporary admin login
        if (username.equals("admin") && password.equals("123")) {
            return new Admin();
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

        User user = new StudentBuilder(id)
                .setFullName(firstName, middleName, lastName)
                .setEmail(email)
                .create();

        UserDetails userDetails = new UserDetails(user);

        userDatabase.put(userDetails.getUsername(), userDetails);
    }

    public static void registerTeacher(String id, String firstName, String middleName, String lastName, String email) {

        User user = new TeacherBuilder(id)
                .setFullName(firstName, middleName, lastName)
                .setEmail(email)
                .create();

        UserDetails userDetails = new UserDetails(user);

        userDatabase.put(userDetails.getUsername(), userDetails);
    }

    public static void remove(String username) {
        if (userDatabase.remove(username) == null) {
            throw new RuntimeException("Username doesn't exist.");
        }
    }

    public static void addCourse(String code, String description, String key, String year, String teacher, ArrayList<String> students) {
        
        Course course = new CourseBuilder(code)
                .setDescription(description)
                .setKey(key)
                .setYear(year)
                .setTeacher(teacher)
                .setStudents(students)
                .create();
    }

}