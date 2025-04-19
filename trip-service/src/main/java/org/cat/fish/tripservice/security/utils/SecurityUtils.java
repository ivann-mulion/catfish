package org.cat.fish.tripservice.security.utils;

import org.cat.fish.tripservice.security.principal.UserPrincipal;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class SecurityUtils {

    private static SecurityUtils instance;

    public SecurityUtils() {
        instance = this;
    }

    public Long getCurrentUserId() {
        return getCurrentUser().getId();
    }

    public String getCurrentFirstName() {
        return getCurrentUser().getFirstName();
    }

    public String getCurrentLastName() {
        return getCurrentUser().getLastName();
    }

    public UserPrincipal getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated() &&
                authentication.getPrincipal() instanceof UserPrincipal) {
            return (UserPrincipal) authentication.getPrincipal();
        }
        throw new RuntimeException("User not authenticated");
    }

    public static Long currentUserId() {
        return instance.getCurrentUserId();
    }

    public static String currentFirstName() {
        return instance.getCurrentFirstName();
    }

    public static String currentLastName() {
        return instance.getCurrentLastName();
    }
}