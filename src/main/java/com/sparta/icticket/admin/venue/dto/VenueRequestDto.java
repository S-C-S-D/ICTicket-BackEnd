package com.sparta.icticket.admin.venue.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class VenueRequestDto {

    @NotBlank(message = "공연장 이름을 입력해 주세요.")
    private String venueName;

    @NotBlank(message = "공연장 위치를 입력해 주세요.")
    private String location;

    @NotNull(message = "전체 좌석 수를 입력해 주세요.")
    private Long totalSeatCount;


}
