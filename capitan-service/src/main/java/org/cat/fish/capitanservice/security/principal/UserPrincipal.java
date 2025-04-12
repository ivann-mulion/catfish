package org.cat.fish.capitanservice.security.principal;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UserPrincipal {
    private final Long id;

    public String getName() {
        return id.toString();
    }
}
