package com.sparta.icticket.performance.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sparta.icticket.common.enums.AgeGroup;
import com.sparta.icticket.common.enums.GenreType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;


import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
public class PerformanceRequestDto {
    @NotBlank(message = "제목을 입력해 주세요.")
    private String title;

    @NotBlank(message = "해당 공연에 대한 내용을 입력해 주세요.")
    private String description;

    @NotNull(message = "공연장 Id를 등록해 주세요.")
    private Long venueId;

    @NotNull(message = "장르를 선택해 주세요.")
    private GenreType genreType;

    @NotNull(message = "관람 최소 연령을 선택해 주세요.")
    private AgeGroup ageGroup;

    @NotNull(message = "공연시간을 입력해 주세요.")
    private Integer runTime;

    private String imageUrl;

    @JsonFormat(pattern = "yyyy-MM-dd-HH:mm")
    private LocalDateTime openAt;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate startAt;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate endAt;
}