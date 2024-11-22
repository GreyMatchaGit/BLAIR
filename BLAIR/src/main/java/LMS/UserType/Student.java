package LMS.UserType;

import LMS.User;

import java.util.SortedMap;

public class Student extends User {
    private SortedMap<String, Integer> grades;

    public Student(String id) {
        super(id);
    }
}
