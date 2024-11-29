package DB;

import LMS.User;
import LMS.UserType.Student;

public class UserDetails {
    private String username;
    private String password;
    private User user;

    public UserDetails(User user) {
        this.user = user;
        username = user.generateUsername();
        password = user.generateDefaultPass();
    }
    
    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
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
