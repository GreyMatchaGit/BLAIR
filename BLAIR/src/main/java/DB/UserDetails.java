package DB;

import LMS.User;

public class UserDetails {
    private String username;
    private String password;
    private String type;
    private User user;

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String type() {
        return type;
    }

    public User getUser() {
        return user;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
