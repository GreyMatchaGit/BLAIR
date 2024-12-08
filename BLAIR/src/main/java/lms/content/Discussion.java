package lms.content;

import lms.User;

public class Discussion extends Content {
    private User creator;
    public Discussion(String title, String description, User creator) {
        super(title, description);
        this.creator = creator;
    }

    public User getCreator() { return creator; }
    public void setCreator(User creator) { this.creator = creator; }
}