package DB.Type;

import DB.Database;
import DB.UserDetails;
import LMS.User;
import Utilities.UserAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class GSONDB extends Database {

    private static String JSONPath;

    public GSONDB(String JSONPath) {
        super(loadUserDatabase(JSONPath));
        GSONDB.JSONPath = JSONPath;
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

    public static void updateDatabase() throws IOException {
        GsonBuilder builder = new GsonBuilder().setPrettyPrinting();
        Gson gson = builder.create();
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(JSONPath))) {
            bw.write(gson.toJson(Database.userDatabase));
        }
    }
}