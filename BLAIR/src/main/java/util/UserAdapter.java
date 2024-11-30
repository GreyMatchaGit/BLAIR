package util;

import lms.User;
import lms.usertype.Student;
import lms.usertype.Teacher;
import com.google.gson.*;

import java.lang.reflect.Type;

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
