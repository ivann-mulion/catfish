package org.cat.fish.tripservice.security.utils;

import org.cat.fish.tripservice.security.principal.UserPrincipal;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class SecurityUtils {

    public static Long getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        System.out.println("Authentication: " + authentication);
        if (authentication != null) {
            System.out.println("Principal: " + authentication.getPrincipal());
            System.out.println("Authorities: " + authentication.getAuthorities());
            System.out.println("Authenticated: " + authentication.isAuthenticated());
        }

        if (authentication == null) {
            throw new IllegalStateException("Authentication not found in security context");
        }

        if (!authentication.isAuthenticated()) {
            throw new IllegalStateException("User is not authenticated");
        }

        Object principal = authentication.getPrincipal();

        if (principal instanceof UserPrincipal) {
            System.out.println("Principal: " + ((UserPrincipal) principal).getId());
            return ((UserPrincipal) principal).getId();
        } else if (principal instanceof Long) {
            return (Long) principal;
        } else if (principal instanceof String) {
            try {
                return Long.parseLong((String) principal);
            } catch (NumberFormatException e) {
                throw new IllegalStateException("Invalid user ID format: " + principal);
            }
        }

        throw new IllegalStateException("Unexpected principal type: " + principal.getClass().getName());
    }


    public static String getCurrentLastName() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Object principal = authentication.getPrincipal();

        if (principal instanceof UserPrincipal) {
            System.out.println("Principal: " + ((UserPrincipal) principal).getLastName());
            return ((UserPrincipal) principal).getLastName();
        }

        throw new IllegalStateException("Unexpected principal type: " + principal.getClass().getName());
    }

    public static String getCurrentFirstName() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Object principal = authentication.getPrincipal();

        if (principal instanceof UserPrincipal) {
            System.out.println("Principal: " + ((UserPrincipal) principal).getFirstName());
            return ((UserPrincipal) principal).getFirstName();
        }

        throw new IllegalStateException("Unexpected principal type: " + principal.getClass().getName());
    }
}