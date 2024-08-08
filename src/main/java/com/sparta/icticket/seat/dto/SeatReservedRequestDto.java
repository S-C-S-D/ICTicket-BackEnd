package com.sparta.icticket.seat.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SeatReservedRequestDto {
    @NotNull(message = "좌석을 선택해 주세요.")
    @Size(min=1, max=3, message = "좌석은 최대 3개까지 선택 가능합니다.")
    private List<Long> seatIdList;
}
