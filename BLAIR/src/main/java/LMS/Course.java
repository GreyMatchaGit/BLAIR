package LMS;

import LMS.UserType.Student;

import java.util.ArrayList;

public class Course {
    private String description;
    private String code;
    private String key;
    private String year;
    private ArrayList<Student> students;

    protected void setDescription(String description) { this.description = description; }
    protected void setCode(String code) { this.code = code; }
    protected void setKey(String key) { this.key = key; }
    protected void setYear(String year) { this.year = year; }
    protected void setStudents(ArrayList<Student> students) { this.students = students; }

    public String getCode() { return code; }
    public String getKey() { return key; }
    public String getYear() { return year; }
    public String getDescription() { return description; }
    public ArrayList<Student> getStudents() { return students; }
}
