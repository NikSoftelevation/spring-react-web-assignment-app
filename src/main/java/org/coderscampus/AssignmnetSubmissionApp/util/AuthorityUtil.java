package org.coderscampus.AssignmnetSubmissionApp.util;


import org.coderscampus.AssignmnetSubmissionApp.model.User;

public class AuthorityUtil {
    private AuthorityUtil() {
    }

    public static Boolean hasRole(String role, User user) {
        return user.getAuthorities()
                .stream()
                .filter(auth -> auth.getAuthority().equals(role))
                .count() > 0;
    }
}