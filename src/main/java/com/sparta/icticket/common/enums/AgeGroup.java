package com.sparta.icticket.common.enums;

import lombok.Getter;

@Getter
public enum AgeGroup {
    ALL("ALL"),
    SEVEN("7"),
    TWELVE("12"),
    FIFTEEN("15"),
    NINETEEN("19");

    private final String group;

    AgeGroup(String group) {
        this.group = group;
    }
}
