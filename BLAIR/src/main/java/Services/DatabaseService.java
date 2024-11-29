package Services;

import DB.Database;
import DB.Type.GSONDB;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class DatabaseService {

    public static void checkDatabaseIntialization() {
        if (Database.userDatabase == null) {
            String usersPath = DatabaseService.class.getResource("/database/users.json").toString();
            StringBuilder sb = new StringBuilder(usersPath);
            sb.delete(0, 5);
            usersPath = sb.toString();
            new GSONDB(usersPath);
        }
    }

    public static void registerStudent(String id, String firstName, String middleName, String lastName, String email) throws IOException {
        checkDatabaseIntialization();
        Database.registerStudent(id, firstName, middleName, lastName, email);
    }

    public static void registerTeacher(String id, String firstName, String middleName, String lastName, String email) throws IOException {
        checkDatabaseIntialization();
        Database.registerTeacher(id, firstName, middleName, lastName, email);
    }
}
