package database;

import lms.calendar.CustomEntry;
import lms.course.Course;
import lms.usertype.User;
import lms.quizzler.Deck;
import lms.course.Prompt;
import lms.todolist.Task;

import java.util.HashMap;
import java.util.Map;

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
    public static HashMap<String, Task> taskDatabase = null;
    public static HashMap<String, Prompt> promptDatabase = null;
    public static HashMap<String, CustomEntry> calendarDatabase = null;

    public Database(HashMap<String, User> userDatabase, HashMap<String, Course> courseDatabase, HashMap<String, Deck> deckDatabase, HashMap<String, Task> taskDatabase, HashMap<String, Prompt> promptDatabase, HashMap<String, CustomEntry> calendarDatabase) {

        assert Database.userDatabase == null;
        assert Database.courseDatabase == null;
        assert Database.taskDatabase == null;
        assert Database.deckDatabase == null;
        assert Database.promptDatabase == null;
        assert Database.calendarDatabase == null;

        Database.userDatabase = userDatabase;
        Database.courseDatabase = courseDatabase;
        Database.deckDatabase = deckDatabase;
        Database.taskDatabase = taskDatabase;
        Database.promptDatabase = promptDatabase;
        Database.calendarDatabase = calendarDatabase;

    }
}