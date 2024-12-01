package LMS.Calendar;

import java.time.ZonedDateTime;

public class CalendarActivity {
    private ZonedDateTime date;
    private String description;

    public CalendarActivity(ZonedDateTime date, String description) {
        this.date = date;
        this.description = description;
    }

    public ZonedDateTime getDate() {
        return date;
    }

    public void setDate(ZonedDateTime date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    @Override
    public String toString() {
        return "CalenderActivity{" +
                "date=" + date +
                ", description='" + description
                + '}';
    }
}
