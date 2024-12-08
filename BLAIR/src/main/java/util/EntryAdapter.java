package util;

import com.calendarfx.model.Entry;
import com.google.gson.*;
import javafx.beans.property.SimpleStringProperty;

import java.lang.reflect.Type;

public class EntryAdapter implements JsonSerializer<Entry<?>>, JsonDeserializer<Entry<?>> {

    @Override
    public JsonElement serialize(Entry<?> entry, Type type, JsonSerializationContext context) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("title", entry.getTitle());
//        jsonObject.addProperty("calendar", entry.getCalendar());
        // Serialize other fields as needed
        return jsonObject;
    }

    @Override
    public Entry<?> deserialize(JsonElement json, Type type, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        String title = jsonObject.get("title").getAsString();
//        String calendar = jsonObject.get("calendar").getAsString();
        // Deserialize other fields as needed
        return new Entry<>(title); // Adjust constructor as necessary
    }
}

