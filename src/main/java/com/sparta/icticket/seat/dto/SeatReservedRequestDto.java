package com.sparta.icticket.seat.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SeatReservedRequestDto {
    @NotNull(message = "좌석을 선택해 주세요.")
    private List<Long> seatIdList;
}
