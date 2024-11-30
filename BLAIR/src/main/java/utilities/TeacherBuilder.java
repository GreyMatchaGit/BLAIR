package utilities;

import lms.User;
import lms.usertype.Teacher;

public class TeacherBuilder {
    public String id;
    public String firstName;
    public String middleName;
    public String lastName;
    public String email;

    public TeacherBuilder(String id) {
        this.id = id;
    }

    public TeacherBuilder setFullName(String firstName, String middleName, String lastName) {
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        return this;
    }

    public TeacherBuilder setEmail(String email) {
        this.email = email;
        return this;
    }

    public User create() {
        return new Teacher(id, firstName, middleName, lastName, email);
    }
}
