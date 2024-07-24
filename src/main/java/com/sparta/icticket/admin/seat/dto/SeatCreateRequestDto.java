package com.sparta.icticket.admin.seat.dto;

import com.sparta.icticket.common.enums.SeatGrade;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;

@Getter
public class SeatCreateRequestDto {
    @NotNull(message = "금액을 입력해 주세요")
//    @Positive(message = "양수의 숫자만 입력이 가능합니다.")
    private Integer price;

    @NotNull(message = "좌석 번호 입력해 주세요.")
//    @Positive(message = "양수의 숫자만 입력이 가능합니다.")
    private String seatNumber;

    @NotNull(message = "좌석 등급을 선택해 주세요.")
    private SeatGrade seatGrade;
}
