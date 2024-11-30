package Services;

import DB.Database;
import DB.Type.GSONDB;
import DB.UserDetails;
import LMS.Course;
import LMS.User;
import LMS.UserType.Admin;
import Utilities.CourseBuilder;
import Utilities.StudentBuilder;
import Utilities.TeacherBuilder;

import java.io.IOException;
import java.util.ArrayList;

public class DatabaseService {

    public static void checkDatabaseInitialization() {
        if (Database.userDatabase == null) {
            String usersPath = prunePath(DatabaseService.class.getResource("/database/users.json").toString());
            String coursesPath = prunePath(DatabaseService.class.getResource("/database/users.json").toString());
            new GSONDB(
                    usersPath,
                    coursesPath
            );
        }
    }

    public static User login(String username, String password) {

        if (username.equals("admin") && password.equals("123"))
            return new Admin();

        UserDetails currentUser  = null;

        if (Database.userDatabase.containsKey(username))
            currentUser = Database.userDatabase.get(username);

        if (currentUser  == null)
            throw new RuntimeException("The username doesn't exist in the database.");

        if (!(currentUser.getPassword().equals(password)))
            throw new RuntimeException("Password does not match.");

        return currentUser .getUser();
    }

    public static void registerStudent(String id, String firstName, String middleName, String lastName, String email) throws IOException {
        checkDatabaseInitialization();

        User user = new StudentBuilder(id)
                .setFullName(firstName, middleName, lastName)
                .setEmail(email)
                .create();

        UserDetails userDetails = new UserDetails(user);

        Database.userDatabase.put(userDetails.getUsername(), userDetails);
    }

    public static void registerTeacher(String id, String firstName, String middleName, String lastName, String email) throws IOException {
        checkDatabaseInitialization();

        User user = new TeacherBuilder(id)
                .setFullName(firstName, middleName, lastName)
                .setEmail(email)
                .create();

        UserDetails userDetails = new UserDetails(user);

        Database.userDatabase.put(userDetails.getUsername(), userDetails);
    }

    public static void removeUser(String username) {
        if (Database.userDatabase.remove(username) == null) {
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

        Database.courseDatabase.put(course.getKey(), course);
    }

    public static String prunePath(String URLPath) {
        StringBuilder sb = new StringBuilder(URLPath);
        sb.delete(0, 5);
        return sb.toString();
    }
}
