package lms.calendar;

import com.calendarfx.model.Entry;

public class CustomEntry {
    private String title;
    private String id;
    private String calendar;
    private String startDate;
    private String startTime;
    private String endDate;
    private String endTime;
    private String zoneId;
    private String recurrenceRule;


    public CustomEntry (Entry<?> entry) {
        this.title = entry.getTitle();
        this.id = entry.getId();
        this.calendar = entry.getCalendar().getName();
        this.startDate = entry.getStartDate().toString();
        this.startTime = entry.getStartTime().toString();
        this.endDate = entry.getEndDate().toString();
        this.endTime = entry.getEndTime().toString();
        this.zoneId = entry.getZoneId().toString();
        this.recurrenceRule = entry.getRecurrenceRule();
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getCalendar() {
        return calendar;
    }

    public void setCalendar(String calendar) {
        this.calendar = calendar;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getZoneId() {
        return zoneId;
    }

    public void setZoneId(String zoneId) {
        this.zoneId = zoneId;
    }

    public String getRecurrenceRule() {
        return recurrenceRule;
    }

    public void setRecurrenceRule(String recurrenceRule) {
        this.recurrenceRule = recurrenceRule;
    }
}
