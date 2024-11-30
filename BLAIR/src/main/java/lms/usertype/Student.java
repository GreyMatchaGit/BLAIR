package lms.usertype;

import lms.User;

import java.util.SortedMap;

public class Student extends User {
    private SortedMap<String, Integer> grades;
    private String program;
    private String yearLevel;

    public Student() {
        setType("Student");
    }

    public Student(String id, String firstName, String middleName, String lastName, String email) {
        super(id, firstName, middleName, lastName, email);
        setType("Student");
    }

    public void setProgram(String program) {
        this.program = program;
    }

    public void setYearLevel(String yearLevel) {
        this.yearLevel = yearLevel;
    }
}
