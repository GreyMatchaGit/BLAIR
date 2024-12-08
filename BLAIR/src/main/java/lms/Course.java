package lms;

import lms.content.Activity;
import services.DatabaseService;
import services.StringService;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Objects;

public class Course {
    private String description;
    private String code;
    private String key;
    private String year;
    private String teacher;
    private ArrayList<String> students;
    private ArrayList<String> discussions;
    private ArrayList<Activity> activities;
    private final String courseDir;

    public Course(String description, String code, String key, String year, String teacher, ArrayList<String> students) {
        this.description = description;
        this.code = code;
        this.key = key;
        this.year = year;
        this.teacher = teacher;
        this.students = students;
        activities = new ArrayList<>();
        discussions = new ArrayList<>();
        courseDir = makeCourseDir();
    }

    public String getCourseDir() { return courseDir; }

    public ArrayList<String> getDiscussions() { return discussions; }

    public void addDiscussion(String discussion) { discussions.add(discussion); }

    public void setDiscussions(ArrayList<String> discussions) { this.discussions = discussions; }

    public ArrayList<Activity> getActivities() { return activities; }

    public void setActivities(ArrayList<Activity> activities) { this.activities = activities; }

    public void addActivity(Activity activity) { this.activities.add(activity); }

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

    private String makeCourseDir() {
        String baseDir = StringService.convertFrom(
                Objects.requireNonNull(DatabaseService.class.getResource("/course-files/"))
        ).substring(1);

        Path courseDir = Paths.get(baseDir, code);

        try {
            if (!Files.exists(courseDir)) {
                Files.createDirectories(courseDir);
            }
        } catch (IOException e) {
            System.err.println("Failed to create directory: " + e.getMessage());
        }
        return baseDir + code + "/";
    }

}
