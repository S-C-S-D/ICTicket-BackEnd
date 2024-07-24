package com.sparta.icticket.common.enums;

import lombok.Getter;

@Getter
public enum SeatStatus {
    NOT_RESERVED("NOT_RESERVED"),
    PAYING("PAYING"),
    PAYMENT_COMPLETED("PAYMENT_COMPLETED");

    private final String type;

    SeatStatus(String type) {
        this.type = type;
    }
}
