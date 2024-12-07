package services;

import com.calendarfx.model.Entry;
import database.Database;
import database.type.GSONDB;
import lms.Course;
import lms.User;
import lms.content.Prompt;
import lms.content.todolist.Task;
import lms.usertype.Admin;
import lms.usertype.Student;
import util.CourseBuilder;
import util.StudentBuilder;
import util.TeacherBuilder;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class DatabaseService {

    public static void initialize() {

        String jsonResource = StringService.convertFrom(
                Objects.requireNonNull(DatabaseService.class.getResource("/json/"))
        );

        jsonResource = jsonResource.replaceAll("%20", " ");

//        jsonResource = jsonResource.replaceAll("%20", " ");

        String usersPath = jsonResource + "users.json";
        String coursesPath = jsonResource + "courses.json";
        String decksPath = jsonResource + "decks.json";
        String promptPath = jsonResource + "prompts.json";
        String taskPath = jsonResource + "tasks.json";
        String entriesPath = jsonResource + "entries.json";

        new GSONDB(
                usersPath,
                coursesPath,
                decksPath,
                promptPath,
                taskPath,
                entriesPath
        );
    }

    public static boolean isInitialized() {

        return Database.userDatabase != null && Database.courseDatabase != null;
    }

    public static User login(String username, String password) {

        if (username.equals("admin") && password.equals("123"))
            return new Admin();

        User currentUser  = null;

        if (Database.userDatabase.containsKey(username))
            currentUser = Database.userDatabase.get(username);

        if (currentUser  == null)
            throw new RuntimeException("The username doesn't exist in the database.");

        if (!(currentUser.getPassword().equals(password)))
            throw new RuntimeException("Password does not match.");

        return currentUser;
    }

    public static void registerStudent(String id, String firstName, String middleName, String lastName, String email, String program, String yearLevel) throws IOException {

        assert(Database.userDatabase != null);

        User user = new StudentBuilder(id)
                .setFullName(firstName, middleName, lastName)
                .setEmail(email)
                .setProgram(program)
                .setYearLevel(yearLevel)
                .create();

        Database.userDatabase.put(user.getUsername(), user);
    }

    public static void registerTeacher(String id, String firstName, String middleName, String lastName, String email) throws IOException {

        assert(Database.userDatabase != null);

        User user = new TeacherBuilder(id)
                .setFullName(firstName, middleName, lastName)
                .setEmail(email)
                .create();

        Database.userDatabase.put(user.getUsername(), user);
    }

    public static void removeUser(String username) {

        if (Database.userDatabase.remove(username) == null) {
            throw new RuntimeException("Username doesn't exist.");
        }
    }

    public static void changePassword(String username, String newPassword) {

        User user = Database.userDatabase.get(username);

        if (user == null) {
            throw new RuntimeException("Username doesn't exist.");
        }

        user.setPassword(newPassword);
    }

    public static void validatePassword(String username, String password) {

        String currentPassword = Database
                .userDatabase
                .get(username)
                .getPassword();

        if (!password.equals(currentPassword)) {
            throw new RuntimeException("Password does not match.");
        }
    }

    public static void update() {
        try {
            GSONDB.updateDatabase();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void addCourse(String code, String description, String key, String year, String teacher, ArrayList<String> students) { //, ArrayList<File> files) {

        assert(Database.courseDatabase != null);

        Course course = new CourseBuilder(code)
                .setDescription(description)
                .setKey(key)
                .setYear(year)
                .setTeacher(teacher)
                .setStudents(students)
//                .setFiles(files)
                .create();

        Database.courseDatabase.put(course.getKey(), course);
    }
    public static Map<String, Prompt> getPromptDatabase() {
        return Database.promptDatabase;
    }

    public static ArrayList<String> getStudents() {
        ArrayList<String> studentFirstNames = new ArrayList<>();

        for (User  user : Database.userDatabase.values()) {
            if (user instanceof Student) {
                studentFirstNames.add(user.getFullName());
            }
        }

        return studentFirstNames;
    }

    public static void addEntry(Entry<?> entry) {

        assert(Database.calendarDatabase != null);

        Database.calendarDatabase.put(entry.getId(), (Entry<String>) entry);
    }
}
