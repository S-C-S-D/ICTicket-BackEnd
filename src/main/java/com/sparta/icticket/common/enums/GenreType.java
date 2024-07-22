package com.sparta.icticket.common.enums;

import lombok.Getter;

@Getter
public enum GenreType {
    CONCERT("CONCERT"),
    MUSICAL("MUSICAL"),
    FESTIVAL("FESTIVAL"),
    SPORTS("SPORTS"),
    THEATER("THEATER"),
    EXHIBITION("EXHIBITION"),
    CLASSIC("CLASSIC");

    private final String type;

    GenreType(String type) {
        this.type = type;
    }
}
