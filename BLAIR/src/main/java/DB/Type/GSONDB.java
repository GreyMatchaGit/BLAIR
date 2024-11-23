package DB.Type;

import DB.UserDetails;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;

public class GSONDB extends DB.Database {

    public GSONDB(String JSONPath) {
        super(loadUserDatabase(JSONPath));
    }

    private static ArrayList<UserDetails> loadUserDatabase(String JSONPath) {
        try {
            Gson gson = new Gson();
            JsonReader reader = new JsonReader(new FileReader(JSONPath));
            UserDetails[] rawArray = gson.fromJson(reader, UserDetails[].class);
            return new ArrayList<>(Arrays.asList(rawArray));
        } catch (FileNotFoundException e) {
            System.err.println("JSON file not found: " + e.getMessage());
            return new ArrayList<>();
        }
    }
}