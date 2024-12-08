package util;

import lms.usertype.User;
import lms.usertype.Student;
import lms.usertype.Teacher;
import com.google.gson.*;

import java.lang.reflect.Type;

/** ADAPTER PATTERN
 *  <p>
 *  Gson will run into a problem when trying to deserialize
 *  an abstract class (User). So, the purpose of UserAdapter
 *  is to add some flexibility to the Deserializer by determining
 *  the specific type of the User. Then, it will be deserialized
 *  accordingly to its appropriate Concrete class.
 * */

public class UserAdapter implements JsonDeserializer<User> {
    @Override
    public Student deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        String userType = jsonObject.get("type").getAsString();

        if (userType.equals("Student")) {
            return new Gson().fromJson(jsonElement, Student.class);
        } else if (userType.equals("Teacher")) {
            return new Gson().fromJson(jsonElement, Teacher.class);
        } else {
            throw new JsonParseException("Invalid usertype");
        }
    }
}
