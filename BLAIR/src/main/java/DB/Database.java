package DB;

import LMS.Course;

import java.io.IOException;
import java.util.HashMap;

public abstract class Database {
    public static HashMap<String, UserDetails> userDatabase = null;
    public static HashMap<String, Course> courseDatabase = null;

    public Database(HashMap<String, UserDetails> userDatabase, HashMap<String, Course> courseDatabase) {

        if (Database.userDatabase == null)
            Database.userDatabase = userDatabase;
        else
            throw new RuntimeException("User Database already exists");

        if (Database.courseDatabase == null)
            Database.courseDatabase = courseDatabase;
        else
            throw new RuntimeException("Course database already exists");
    }
}