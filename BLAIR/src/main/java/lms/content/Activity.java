package lms.content;

import lms.usertype.Student;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;

public class Activity extends Content {
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

    static class submission {
        private Student student;
        private String attachment;

        public submission(Student student, String attachment) {
            this.student = student;
            this.attachment = attachment;
        }

        public Student getStudent() { return student; }
        public String getAttachment() { return attachment; }

        public void setStudent(Student student) { this.student = student; }
        public void setAttachment(String attachment) { this.attachment = attachment; }
    }
}
