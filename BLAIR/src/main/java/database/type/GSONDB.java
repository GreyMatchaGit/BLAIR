package database.type;

import database.Database;
import lms.Course;
import lms.User;
import lms.calendar.CustomEntry;
import lms.content.Deck;
import lms.content.Task;
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
    private static String promptJSON;
    private static String taskJSON;
    private static String entriesJSON;

    public GSONDB(String userJSON, String courseJSON, String deckJSON, String promptJSON, String taskJSON, String entriesJSON) {

        super(
                loadDatabase(userJSON, new TypeToken<HashMap<String, User>>() {}),
                loadDatabase(courseJSON, new TypeToken<HashMap<String, Course>>() {}),
                loadDatabase(deckJSON, new TypeToken<HashMap<String, Deck>>() {}),
                loadDatabase(promptJSON, new TypeToken<HashMap<String, Prompt>>() {}),
                loadDatabase(taskJSON, new TypeToken<HashMap<String, Task>>() {}),
                loadDatabase(entriesJSON, new TypeToken<HashMap<String, CustomEntry>>() {})
        );

        GSONDB.userJSON = userJSON;
        GSONDB.courseJSON = courseJSON;
        GSONDB.deckJSON = deckJSON;
        GSONDB.taskJSON = taskJSON;
        GSONDB.promptJSON = promptJSON;
        GSONDB.entriesJSON = entriesJSON;
    }

    private static <T> HashMap<String, T> loadDatabase(String path, TypeToken<HashMap<String, T>> typeToken) {

        try {
            JsonReader reader = new JsonReader(new FileReader(path));

            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(User.class, new UserAdapter())
                    .create();

            HashMap<String, T> converted = gson
                    .fromJson(
                            reader,
                            typeToken.getType()
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

    public static void update() throws IOException {

        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .serializeNulls()
                .create();

        try (BufferedWriter userWriter = new BufferedWriter(new FileWriter(userJSON));
             BufferedWriter courseWriter = new BufferedWriter(new FileWriter(courseJSON));
             BufferedWriter taskWriter = new BufferedWriter(new FileWriter(taskJSON));
             BufferedWriter deckWriter = new BufferedWriter(new FileWriter(deckJSON));
            BufferedWriter entriesWriter = new BufferedWriter(new FileWriter(entriesJSON))) {

            userWriter.write(gson.toJson(Database.userDatabase));
            courseWriter.write(gson.toJson(Database.courseDatabase));
            taskWriter.write(gson.toJson(Database.taskDatabase));
            deckWriter.write(gson.toJson(Database.deckDatabase));
            entriesWriter.write(gson.toJson(Database.calendarDatabase));
        }
    }
}