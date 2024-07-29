package com.sparta.icticket.common.enums;
import lombok.Getter;

@Getter
public enum BannerType {
    MAIN("MAIN"),
    MIDDLE("MIDDLE"),
    BOTTOM("BOTTOM");

    private final String type;

    BannerType(String type) {
        this.type = type;
    }
}
