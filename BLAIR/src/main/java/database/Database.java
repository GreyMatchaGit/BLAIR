package database;

import lms.Course;
import lms.User;
import lms.content.Deck;
import lms.content.Prompt;
import lms.content.todolist.Task;

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
    public static Map<String, Prompt> promptDatabase = null;
    // public static HashMap<String, Event> calendarDatabase = null;

    public Database(HashMap<String, User> userDatabase, HashMap<String, Course> courseDatabase, HashMap<String, Deck> deckDatabase, HashMap<String, Task> taskDatabase, HashMap<String, Prompt> promptDatabase) {

        assert Database.userDatabase == null;
        assert Database.courseDatabase == null;
        assert Database.taskDatabase == null;
        assert Database.deckDatabase == null;
        assert Database.promptDatabase == null;

        Database.userDatabase = userDatabase;
        Database.courseDatabase = courseDatabase;
        Database.taskDatabase = taskDatabase;
        Database.promptDatabase = promptDatabase;
    }
}