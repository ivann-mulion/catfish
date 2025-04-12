package org.cat.fish.capitanservice.security.utils;

import org.cat.fish.capitanservice.security.principal.UserPrincipal;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class SecurityUtils {

    public static Long getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();

            if (principal instanceof UserPrincipal) {
                return ((UserPrincipal) principal).getId();
            } else if (principal instanceof Long) {
                return (Long) principal;
            } else if (principal instanceof String) {
                return Long.parseLong((String) principal);
            }
        }

        throw new IllegalStateException("User not authenticated");
    }
}
