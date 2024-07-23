package com.sparta.icticket.seat.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class SeatReservedRequestDto {
    private List<Long> seatIdList;
}
