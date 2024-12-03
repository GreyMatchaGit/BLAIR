package services;

import lms.User;

import java.net.URL;

public class StringService {
    public static String convertFrom(URL url) {
        return new StringBuilder(url.toString())
                .delete(0, 5)
                .toString();
    }
    public static String defaultUsername(User user) {
        return String.format(
                "%s.%s",
                user.getFirstName().replaceAll("\\s", ""),
                user.getLastName()
        ).toLowerCase();
    }

    public static String defaultPassword(User user) {
        return String.format(
                "%s.123456",
                user.getLastName()
        ).toLowerCase();
    }
}
