package LMS.UserType;

import LMS.User;

import java.util.SortedMap;

public class Student extends User {
    private SortedMap<String, Integer> grades;

    public Student(String id) {
        super(id);
        setType("Student");
    }

    public Student(String id, String firstName, String middleName, String lastName, String email) {
        super(id, firstName, middleName, lastName, email);
        setType("Student");
    }
}
