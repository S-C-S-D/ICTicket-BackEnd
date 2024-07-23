package com.sparta.icticket.seat.dto;

import lombok.Getter;

@Getter
public class SeatCountResponseDto {
    private Integer totalSeatCount;
    private Integer restSeatCount;

    public SeatCountResponseDto(Integer totalSeatCount, Integer restSeatCount) {
        this.totalSeatCount = totalSeatCount;
        this.restSeatCount = restSeatCount;
    }
}
