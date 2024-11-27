package DB.Type;

import DB.Database;
import DB.UserDetails;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

public class GSONDB extends Database {
    private String JSONPath;
    public GSONDB(String JSONPath) {
        this.JSONPath = JSONPath;
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

    @Override
    public void updateDatabase() throws IOException {
        GsonBuilder builder = new GsonBuilder().setPrettyPrinting();
        Gson gson = builder.create();
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(JSONPath))) {
            for (UserDetails user : super.userDatabase) {
                String currentUserDetails = gson.toJson(user);
                bw.write(currentUserDetails);
                bw.newLine();
            }
        }
    }
}