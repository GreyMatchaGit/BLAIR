package Utilities;

import LMS.User;
import LMS.UserType.Student;
import LMS.UserType.Teacher;

public class UserBuilder {
    public String id;
    public String firstName;
    public String middleName;
    public String lastName;
    public String email;

    public UserBuilder(String id) {
        this.id = id;
    }

    public UserBuilder setFullName(String firstName, String middleName, String lastName) {
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        return this;
    }

    public UserBuilder setEmail(String email) {
        this.email = email;
        return this;
    }

    public User createStudent() {
        return new Student(id, firstName, middleName, lastName, email);
    }

    public User createTeacher() {
        return new Teacher(id, firstName, middleName, lastName, email);
    }
}
