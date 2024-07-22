package com.sparta.icticket.common.enums;
import lombok.Getter;

@Getter
public enum OrderStatus {
    SUCCESS("SUCCESS"),
    CANCEL("CANCEL");

    private final String status;

    OrderStatus(String status) {
        this.status = status;
    }
}
