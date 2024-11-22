package LMS.UserType;

import LMS.Course;

import java.util.ArrayList;

public class Teacher extends Student {

    public Teacher(String id) {
        super(id);
    }

    public boolean enroll_student(Student s, Course c) {
        return true;
    }

    public boolean enroll_student(ArrayList<Student> students, Course c) {
        return true;
    }

    public boolean drop_student(Student s, Course c) {
        return true;
    }

    public void mark_activity() {

    }

    public int return_score() {
        return 0;
    }
}
