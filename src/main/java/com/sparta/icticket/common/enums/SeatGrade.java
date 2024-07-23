package com.sparta.icticket.common.enums;

import lombok.Getter;

@Getter
public enum SeatGrade {
    VIP("VIP"),
    R("R"),
    S("S"),
    A("A");

    private final String grade;

    SeatGrade(String grade) {
        this.grade = grade;
    }
}
