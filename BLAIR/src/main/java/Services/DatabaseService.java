package Services;

import DB.Database;
import DB.Type.GSONDB;

import java.io.IOException;

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

    public static void registerStudent(String id, String firstName, String middleName, String lastName, String email) throws IOException {
        checkDatabaseInitialization();
        Database.registerStudent(id, firstName, middleName, lastName, email);
    }

    public static void registerTeacher(String id, String firstName, String middleName, String lastName, String email) throws IOException {
        checkDatabaseInitialization();
        Database.registerTeacher(id, firstName, middleName, lastName, email);
    }

    public static String prunePath(String URLPath) {
        StringBuilder sb = new StringBuilder(URLPath);
        sb.delete(0, 5);
        return sb.toString();
    }
}
