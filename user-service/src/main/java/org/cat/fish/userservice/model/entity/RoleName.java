package org.cat.fish.userservice.model.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum RoleName {
    USER,
    PROMOTER,
    ADMIN,
    MANAGER
}
