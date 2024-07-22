package com.sparta.icticket.common.enums;

public enum UserRole {
    ROLE_USER("user"),
    ROLE_ADMIN("admin");

    private final String status;

    UserRole(String status) {
        this.status = status;
    }
}
