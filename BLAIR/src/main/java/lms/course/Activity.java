package lms.course;

import lms.usertype.Student;

import java.io.File;

public class Activity extends Content {
    /*  Commented out due to possible omission

    private int marks;
    private int score;
    private Date date;
    private Time time;
    private ArrayList<String> answerKey;

    public Activity(String title, String description, int marks, int score, Date date, Time time, ArrayList<String> answerKey) {
        super(title, description);
        this.marks = marks;
        this.score = score;
        this.date = date;
        this.time = time;
        this.answerKey = answerKey;
    }

    public int getMarks() { return marks; }
    public int getScore() { return score; }
    public Date getDate() { return date; }
    public Time getTime() { return time; }
    public ArrayList<String> getAnswerKey() { return answerKey; }

    public void setMarks(int marks) { this.marks = marks; }
    public void setScore(int score) { this.score = score; }
    public void setDate(Date date) { this.date = date; }
    public void setTime(Time time) { this.time = time; }
    public void setAnswerKey(ArrayList<String> answerKey) { this.answerKey = answerKey; }
    */

    private Submission submission;

    public Activity(String title, String description) {
        super(title, description);
    }

    public void attachSubmission(Student student, File file) {
        this.submission = new Submission(student, file.getAbsolutePath());
    }

    public Submission getSubmission() {
        return submission;
    }

    public static class Submission {
        private Student student;
        private String attachment;

        public Submission(Student student, String attachment) {
            this.student = student;
            this.attachment = attachment;
        }

        public Student getStudent() { return student; }
        public String getAttachment() { return attachment; }

        public void setStudent(Student student) { this.student = student; }
        public void setAttachment(String attachment) { this.attachment = attachment; }
    }
}
