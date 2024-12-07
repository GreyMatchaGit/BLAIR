package util;

import lms.Course;

import java.io.File;
import java.util.ArrayList;

public class CourseBuilder {
    private String description;
    private String code;
    private String key;
    private String year;
    private String teacher;
    private ArrayList<String> students;
//    private ArrayList<File> files;

    public CourseBuilder(String code) {
        this.code = code;
    }

    public CourseBuilder setDescription(String description) {
        this.description = description;
        return this;
    }

    public CourseBuilder setCode(String code) {
        this.code = code;
        return this;
    }

    public CourseBuilder setKey(String key) {
        this.key = key;
        return this;
    }

    public CourseBuilder setYear(String year) {
        this.year = year;
        return this;
    }

    public CourseBuilder setTeacher(String teacher) {
        this.teacher = teacher;
        return this;
    }

    public CourseBuilder setStudents(ArrayList<String> students) {
        this.students = students;
        return this;
    }

//    public CourseBuilder setFiles(ArrayList<File> files) {
//        this.files = files;
//        return this;
//    }

    public Course create() {
        return new Course(description, code, key, year, teacher, students); // ,files);
    }
}
