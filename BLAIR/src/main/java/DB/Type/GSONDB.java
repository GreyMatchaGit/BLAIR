package DB.Type;

import DB.UserDetails;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;

public class GSONDB extends DB.Database {

    public GSONDB(String JSONPath) throws FileNotFoundException {

        Gson gson = new Gson();
        JsonReader reader = new JsonReader(new FileReader(JSONPath));
        UserDetails[] rawArray = gson.fromJson(reader, UserDetails.class);
        ArrayList<UserDetails> userDatabase = new ArrayList<>(Arrays.asList(rawArray));

        super(userDatabase);
    }
}
