package com.esgi.coursemanager.common;

public class EmailHelper {
    private static final String EMAIL_REGEX = "^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$";

    public static boolean isValidEmail(String email) {
        return email != null && email.matches(EMAIL_REGEX);
    }
}
