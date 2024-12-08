package util;

import com.calendarfx.model.Calendar;
import com.calendarfx.model.Entry;
import com.calendarfx.model.Interval;
import com.google.gson.*;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;

public class  EntryAdapter extends TypeAdapter<Entry<?>> {
    @Override
    public void write(JsonWriter writer, Entry entry) throws IOException {
        writer.beginObject();
        writer.name("id");
        writer.value(entry.getId());
        writer.name("title");
        writer.value(entry.getTitle());
//        writer.name("calendar");
//        writer.value(entry.getCalendar().toString());
//        writer.name("interval");
//        writer.value(entry.getInterval().toString());
        // Handle calendar serialization
        writer.name("calendar");
        writer.value(entry.getCalendar() != null ? entry.getCalendar().toString() : "null");

        // Handle interval serialization
        writer.name("interval");
        writer.value(entry.getInterval() != null ? entry.getInterval().toString() : "null");
        writer.endObject();
    }

    @Override
    public Entry read(JsonReader reader) throws IOException {
//        return null;
        Entry<?> entry = new Entry<>();
        reader.beginObject();
        String fieldname = null;

        while (reader.hasNext()) {
            JsonToken token = reader.peek();

            if (token.equals(JsonToken.NAME)) {
                //get the current token
                fieldname = reader.nextName();
            }

            if ("id".equals(fieldname)) {
                token = reader.peek();
                entry.setId(reader.nextString());
            }

            if ("calendar".equals(fieldname)) {
                token = reader.peek();
                String calendarString = reader.nextString();
                String name = calendarString.replaceAll(".*name=(.*?),.*", "$1");
                String style = calendarString.replaceAll(".*style=(.*?),.*", "$1");
                Calendar calendar = new Calendar(name);
                switch (name) {
                    case "School Events":
                        calendar.setShortName("T");
                        break;
                    case "Tasks":
                        calendar.setShortName("T");
                        break;
                    case "No Classes":
                        calendar.setShortName("NC");
                        break;
                }
                calendar.setStyle(style);
                entry.setCalendar(calendar);
            }

//            if ("interval".equals(fieldname)) {
//                String intervalString = reader.nextString();
//
//                // Extract interval information
//                String startDateStr = intervalString.replaceAll(".*startDate=(.*?),.*", "$1");
//                String endDateStr = intervalString.replaceAll(".*endDate=(.*?),.*", "$1");
//                String startTimeStr = intervalString.replaceAll(".*startTime=(.*?),.*", "$1");
//                String endTimeStr = intervalString.replaceAll(".*endTime=(.*?),.*", "$1");
//                String zoneIdStr = intervalString.replaceAll(".*zoneId=(.*?)]", "$1");
//
//                // Parse the extracted strings into respective types
//                LocalDate startDate = LocalDate.parse(startDateStr);
//                LocalDate endDate = LocalDate.parse(endDateStr);
//                LocalTime startTime = LocalTime.parse(startTimeStr);
//                LocalTime endTime = LocalTime.parse(endTimeStr);
//                ZoneId zoneId = ZoneId.of(zoneIdStr);
//
//                // Create and set the interval
//                Interval interval = new Interval(startDate, startTime, endDate, endTime, zoneId);
//                entry.setInterval(interval);  // Assign the interval to the entry
//            }
        }
        reader.endObject();
        return entry;
    }
}

