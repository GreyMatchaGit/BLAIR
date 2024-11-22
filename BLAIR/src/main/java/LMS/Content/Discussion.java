package LMS.Content;

import LMS.User.User;

public class Discussion extends Content {
    private User creator;
    private int number_of_replies;

    public Discussion(String title, String description, User creator, int number_of_replies) {
        super(title, description);
        this.creator = creator;
        this.number_of_replies = number_of_replies;
    }

    public User getCreator() { return creator; }
    public int getNumber_of_replies() { return number_of_replies; }

    public void setCreator(User creator) { this.creator = creator; }
    public void setNumber_of_replies(int number_of_replies) { this.number_of_replies = number_of_replies; }

    static class Comment {
        private Discussion discussion;
        private String subject;
        private String message;
        private String attachment;

        public Comment(Discussion discussion, String subject, String message, String attachment) {
            this.discussion = discussion;
            this.subject = subject;
            this.message = message;
            this.attachment = attachment;
        }

        public Discussion getDiscussion() { return discussion; }
        public String getMessage() { return message; }
        public String getAttachment() { return attachment; }
        public String getSubject() { return subject; }

        public void setDiscussion(Discussion discussion) { this.discussion = discussion; }
        public void setSubject(String subject) { this.subject = subject; }
        public void setMessage(String message) { this.message = message; }
        public void setAttachment(String attachment) { this.attachment = attachment; }
    }
}
