package com.github.evseevda.swoyotesttask.server.security.context;

import com.github.evseevda.swoyotesttask.core.domain.user.User;

public class SecurityContext {

    private static final ThreadLocal<User> currentUser = new ThreadLocal<>();

    public static boolean isAuthenticated() {
        return getCurrentUser() != null;
    }

    public static User getCurrentUser() {
        return currentUser.get();
    }

    public static void setCurrentUser(User user) {
        currentUser.set(user);
    }

}
