package lms;

import lms.content.Activity;

import java.io.File;
import java.util.ArrayList;

public class Course {
    private String description;
    private String code;
    private String key;
    private String year;
    private String teacher;
    private ArrayList<String> students;
    private ArrayList<Activity> activities;
//    private ArrayList<File> files;

    public Course(String description, String code, String key, String year, String teacher, ArrayList<String> students) { //, ArrayList<File> files) {
        this.description = description;
        this.code = code;
        this.key = key;
        this.year = year;
        this.teacher = teacher;
        this.students = students;
        activities = new ArrayList<>();
//        this.files = files;
    }

//    public ArrayList<File> getFiles() {
//        return files;
//    }
//
//    public void setFiles(ArrayList<File> files) {
//        this.files = files;
//    }

    public ArrayList<Activity> getActivities() {
        return activities;
    }

    public void setActivities(ArrayList<Activity> activities) {
        this.activities = activities;
    }

    public void addActivity(Activity activity) {
        this.activities.add(activity);
    }

    public String getCode() { return code; }

    public String getKey() { return key; }

    public String getYear() { return year; }

    public String getDescription() { return description; }

    public ArrayList<String> getStudents() { return students; }

    public String getTeacher() { return teacher; }

    protected void setDescription(String description) { this.description = description; }

    protected void setCode(String code) { this.code = code; }

    protected void setKey(String key) { this.key = key; }

    protected void setYear(String year) { this.year = year; }

    protected void setStudents(ArrayList<String> students) { this.students = students; }

    protected void setTeacher(String teacher) { this.teacher = teacher; }

}
