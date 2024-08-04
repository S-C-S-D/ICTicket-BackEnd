package com.sparta.icticket.seat.dto;

import com.sparta.icticket.common.enums.SeatGrade;
import com.sparta.icticket.common.enums.SeatStatus;
import com.sparta.icticket.seat.Seat;
import lombok.Getter;

@Getter
public class SeatInfoResponseDto {
    private Long id;
    private SeatGrade seatGrade;
    private SeatStatus seatStatus;
    private Integer price;

    public SeatInfoResponseDto(Seat seat) {
        this.id = seat.getId();
        this.seatGrade = seat.getSeatGrade();
        this.seatStatus = seat.getSeatStatus();
        this.price = seat.getPrice();
    }
}