package util;

import lms.usertype.User;
import lms.usertype.Student;

/** BUILDER PATTERN <p>
 *   This class is responsible for simplifying the
 *   creation of the Student class.
 * */

public class StudentBuilder {
    public String id;
    public String firstName;
    public String middleName;
    public String lastName;
    public String email;
    public String program;
    public String yearLevel;

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

    public StudentBuilder setProgram(String program) {
        this.program = program;
        return this;
    }

    public StudentBuilder setYearLevel(String yearLevel) {
        this.yearLevel = yearLevel;
        return this;
    }

    public User create() {
        return new Student(id, firstName, middleName, lastName, email, program, yearLevel);
    }
}
