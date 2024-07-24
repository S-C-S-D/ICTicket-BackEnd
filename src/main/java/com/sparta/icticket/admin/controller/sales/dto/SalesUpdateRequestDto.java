package com.sparta.icticket.admin.controller.sales.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class SalesUpdateRequestDto {
    @NotNull(message = "오픈 시간을 입력해 주세요.")
    @JsonFormat(pattern = "yyyy-MM-dd-HH:mm")
    private LocalDateTime startAt;

    @NotNull(message = "마감 시간을 입력해 주세요.")
    @JsonFormat(pattern = "yyyy-MM-dd-HH:mm")
    private LocalDateTime endAt;

    @NotNull(message = "할인율을 입력해 주세요.")
    @Positive(message = "양수의 숫자만 입력이 가능합니다.")
    private Integer discountRate;
}
