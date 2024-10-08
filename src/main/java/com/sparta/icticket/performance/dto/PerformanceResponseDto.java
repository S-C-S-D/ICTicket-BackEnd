package com.sparta.icticket.performance.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sparta.icticket.common.enums.AgeGroup;
import com.sparta.icticket.common.enums.GenreType;
import com.sparta.icticket.performance.Performance;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
public class PerformanceResponseDto {
    private Long id;

    private Long venueId;
    private String venueName;
    private String venueLocation;
    private Long venueTotalSeatCount;

    private String title;

    private String description;

    private GenreType genreType;

    private AgeGroup ageGroup;

    private Integer runTime;

    @JsonFormat(pattern = "yyyy-MM-dd-HH:mm")
    private LocalDateTime openAt;

    private LocalDate startAt;

    private LocalDate endAt;

    private String imageUrl;

    private Long viewCount;

    public PerformanceResponseDto(Performance performance) {
        this.id = performance.getId();

        this.venueId = performance.getVenue().getId();
        this.venueName = performance.getVenue().getVenueName();
        this.venueLocation = performance.getVenue().getLocation();
        this.venueTotalSeatCount = performance.getVenue().getTotalSeatCount();

        this.title = performance.getTitle();
        this.description = performance.getDescription();
        this.genreType = performance.getGenreType();
        this.ageGroup = performance.getAgeGroup();
        this.runTime = performance.getRunTime();
        this.openAt = performance.getOpenAt();
        this.startAt = performance.getStartAt();
        this.endAt = performance.getEndAt();
        this.imageUrl = performance.getImageUrl();
        this.viewCount = performance.getViewCount();
    }
}
