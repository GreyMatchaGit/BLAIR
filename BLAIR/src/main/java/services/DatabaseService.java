package services;

import database.Database;
import database.type.GSONDB;
import lms.course.Course;
import lms.usertype.User;
import lms.calendar.CustomEntry;
import lms.course.Prompt;
import lms.usertype.Admin;
import lms.usertype.Student;
import lms.usertype.Teacher;
import util.CourseBuilder;
import util.StudentBuilder;
import util.TeacherBuilder;

import java.io.IOException;
import java.util.ArrayList;
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

    public static void registerTeacher(String id, String firstName, String middleName, String lastName, String email, String department) throws IOException {

        assert(Database.userDatabase != null);

        User user = new TeacherBuilder(id)
                .setFullName(firstName, middleName, lastName)
                .setEmail(email)
                .setDepartment(department)
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
        if (Database.userDatabase == null) {
            throw new RuntimeException("User database is not initialized.");
        }

        User user = Database.userDatabase.get(username);

        if (user == null) {
            throw new RuntimeException("User not found.");
        }

        String currentPassword = user.getPassword();

        if (!password.equals(currentPassword)) {
            throw new RuntimeException("Password does not match.");
        }
    }

    public static void update() {
        try {
            GSONDB.update();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void addCourse(String code, String description, String key, String year, String teacher, ArrayList<String> students) {

        assert(Database.courseDatabase != null);

        Course course = new CourseBuilder(code)
                .setDescription(description)
                .setKey(key)
                .setYear(year)
                .setTeacher(teacher)
                .setStudents(students)
                .create();

        Database.courseDatabase.put(course.getKey(), course);

        for (String studentUsernames : students) {
            User user = Database.userDatabase.get(studentUsernames);
            if (!(user instanceof Teacher) && user instanceof Student student) {
                ArrayList<String> studentCourses = student.getCourses();
                studentCourses.add(code);
                student.setCourses(studentCourses);
            }
        }

        User teacherUser = Database.userDatabase.get(teacher);
        if (teacherUser instanceof Teacher teacherObj) {
            ArrayList<String> teacherCourses = teacherObj.getCourses();
            teacherCourses.add(code);
            teacherObj.setCourses(teacherCourses);
        }
    }
    public static Map<String, Prompt> getPromptDatabase() {
        return Database.promptDatabase;
    }

    public static ArrayList<String> getStudentUsernames() {
        ArrayList<String> studentUsernames = new ArrayList<>();

        for (User user: Database.userDatabase.values()) {
            if (user instanceof Student && !(user instanceof Teacher)) {
                studentUsernames.add(user.getUsername());
            }
        }

        return studentUsernames;
    }

    public static void addEntry(CustomEntry entry) {

        assert(Database.calendarDatabase != null);

        Database.calendarDatabase.put(entry.getId(), entry);
    }
}
