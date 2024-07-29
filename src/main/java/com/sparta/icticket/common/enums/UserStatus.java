package com.sparta.icticket.common.enums;
import lombok.Getter;

@Getter
public enum UserStatus {
    ACTIVATE("ACTIVATE"),
    DEACTIVATE("DEACTIVATE");

    private final String status;

    UserStatus(String status) {
        this.status = status;
    }
}
