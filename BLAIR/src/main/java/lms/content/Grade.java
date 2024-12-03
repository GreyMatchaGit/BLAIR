package lms.content;

public class Grade {
    private double grade;
    private String range;
    private double percentage;
    private String feedback;

    public Grade(double grade, String range, double percentage, String feedback) {
        this.grade = grade;
        this.range = range;
        this.percentage = percentage;
        this.feedback = feedback;
    }

    public double getGrade() { return grade; }
    public String getRange() { return range; }
    public double getPercentage() { return percentage; }
    public String getFeedback() { return feedback; }

    public void setGrade(double grade) { this.grade = grade; }
    public void setRange(String range) { this.range = range; }
    public void setPercentage(double percentage) { this.percentage = percentage; }
    public void setFeedback(String feedback) { this.feedback = feedback; }
}
