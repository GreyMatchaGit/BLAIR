package Utilities;

import LMS.User;
import LMS.UserType.Student;

public class StudentBuilder {
    public String id;
    public String firstName;
    public String middleName;
    public String lastName;
    public String email;

    public StudentBuilder(String id) {
        this.id = id;
    }

    public StudentBuilder setFullName(String firstName, String middleName, String lastName) {
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        return this;
    }

    public StudentBuilder setEmail(String email) {
        this.email = email;
        return this;
    }

    public User create() {
        return new Student(id, firstName, middleName, lastName, email);
    }
}
