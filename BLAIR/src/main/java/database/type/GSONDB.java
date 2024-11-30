package database.type;

import database.Database;
import database.UserDetails;
import lms.Course;
import lms.User;
import util.UserAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

import java.io.*;
import java.util.HashMap;

public class GSONDB extends Database {

    private static String UserJSON;
    private static String CourseJSON;

    public GSONDB(String UserJSON, String CourseJSON) {
        super(
                loadUserDatabase(UserJSON),
                loadCourseDatabase(CourseJSON)
        );

        GSONDB.UserJSON = UserJSON;
        GSONDB.CourseJSON = CourseJSON;
    }

    private static HashMap<String, UserDetails> loadUserDatabase(String JSONPath) {

        try {
            JsonReader reader = new JsonReader(new FileReader(JSONPath));

            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(User.class, new UserAdapter())
                    .create();

            HashMap<String, UserDetails> converted = gson
                    .fromJson(
                            reader,
                            new TypeToken<HashMap<String, UserDetails>>() {}
                                    .getType()
                    );

            if (converted == null) {
                return new HashMap<>();
            }

            return converted;
        } catch (FileNotFoundException e) {
            System.err.println("JSON file not found: " + e.getMessage());
            return new HashMap<>();
        }
    }

    private static HashMap<String, Course> loadCourseDatabase(String JSONPath) {

        try {
            JsonReader reader = new JsonReader(new FileReader(JSONPath));

            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(User.class, new UserAdapter())
                    .create();

            HashMap<String, Course> converted = gson
                    .fromJson(
                            reader,
                            new TypeToken<HashMap<String, Course>>() {}
                                    .getType()
                    );

            if (converted == null) {
                return new HashMap<>();
            }

            return converted;
        } catch (FileNotFoundException e) {
            System.err.println("JSON file not found: " + e.getMessage());
            return new HashMap<>();
        }
    }

    public static void updateDatabase() throws IOException {

        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .serializeNulls()
                .create();

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(UserJSON))) {
            bw.write(gson.toJson(Database.userDatabase));
        }
    }
}