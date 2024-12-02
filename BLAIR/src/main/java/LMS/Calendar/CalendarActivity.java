package LMS.Calendar;

import java.time.LocalDate;

public class CalendarActivity {
    private LocalDate date;
    private String description;

    public CalendarActivity(LocalDate date, String description) {
        this.date = date;
        this.description = description;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
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
