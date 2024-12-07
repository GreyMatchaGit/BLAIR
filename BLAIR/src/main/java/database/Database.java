package database;

import com.calendarfx.model.Entry;
import lms.Course;
import lms.User;
import lms.content.Deck;

import java.util.Calendar;
import java.util.HashMap;

public abstract class Database {

    /*
     * Database mapping details:
     *      userDatabase = Username to User
     *      courseDatabase = Course Code to Course
     *      deckDatabase = Deck Code to Deck
     *      TODO: calendarDatabase
     */

    public static HashMap<String, User> userDatabase = null;
    public static HashMap<String, Course> courseDatabase = null;
    public static HashMap<String, Deck> deckDatabase = null;
    public static HashMap<String, Entry>  calendarDatabase = null;
    // public static HashMap<String, Event> calendarDatabase = null;

    public Database(HashMap<String, User> userDatabase, HashMap<String, Course> courseDatabase, HashMap<String, Deck> deckDatabase) {

        if (Database.userDatabase == null)
            Database.userDatabase = userDatabase;
        else
            throw new RuntimeException("User Database already exists");

        if (Database.courseDatabase == null)
            Database.courseDatabase = courseDatabase;
        else
            throw new RuntimeException("Course database already exists");

        if (Database.calendarDatabase == null)
            Database.calendarDatabase = calendarDatabase;
        else
            throw new RuntimeException("Course database already exists");
    }
}