package com.sparta.icticket.common.enums;
import lombok.Getter;

@Getter
public enum UserRole {
    USER("ROLE_USER"),
    ADMIN("ROLE_ADMIN");

    private final String authority;

    UserRole(String role) {
        this.authority = role;
    }
}
