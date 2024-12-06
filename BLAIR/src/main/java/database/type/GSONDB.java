package database.type;

import database.Database;
import lms.Course;
import lms.User;
import lms.content.Deck;
import lms.content.Prompt;
import util.UserAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

import java.io.*;
import java.util.HashMap;

public class GSONDB extends Database {

    private static String userJSON;
    private static String courseJSON;
    private static String deckJSON;
    private static String pathJSON;

    public GSONDB(String userJSON, String courseJSON, String deckJSON, String promptJSON) {

        super(
                loadUserDatabase(userJSON),
                loadCourseDatabase(courseJSON),
                loadDeckDatabase(deckJSON),
                loadPromptDatabase(promptJSON)
        );

        GSONDB.userJSON = userJSON;
        GSONDB.courseJSON = courseJSON;
        GSONDB.deckJSON = deckJSON;
    }

    private static HashMap<String, Deck> loadDeckDatabase(String JSONPath) {

        try {
            JsonReader reader = new JsonReader(new FileReader(JSONPath));

            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(User.class, new UserAdapter())
                    .create();

            HashMap<String, Deck> converted = gson
                    .fromJson(
                            reader,
                            new TypeToken<HashMap<String, User>>() {}
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

    private static HashMap<String, User> loadUserDatabase(String JSONPath) {

        try {
            JsonReader reader = new JsonReader(new FileReader(JSONPath));

            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(User.class, new UserAdapter())
                    .create();

            HashMap<String, User> converted = gson
                    .fromJson(
                            reader,
                            new TypeToken<HashMap<String, User>>() {}
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

    private static HashMap<String, Prompt> loadPromptDatabase(String JSONPath) {
        try {
            JsonReader reader = new JsonReader(new FileReader(JSONPath));

            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(User.class, new UserAdapter())
                    .create();

            HashMap<String, Prompt> converted = gson
                    .fromJson(
                            reader,
                            new TypeToken<HashMap<String, Prompt>>() {}.getType()
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

        try (BufferedWriter userWriter = new BufferedWriter(new FileWriter(userJSON));
             BufferedWriter courseWriter = new BufferedWriter(new FileWriter(courseJSON))) {

            userWriter.write(gson.toJson(Database.userDatabase));
            courseWriter.write(gson.toJson(Database.courseDatabase));
        }
    }
}