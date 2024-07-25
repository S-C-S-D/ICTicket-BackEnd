package com.sparta.icticket.performance.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sparta.icticket.common.enums.AgeGroup;
import com.sparta.icticket.common.enums.GenreType;
import com.sparta.icticket.performance.Performance;
import com.sparta.icticket.sales.Sales;
import jakarta.persistence.Column;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
public class DiscountPerformanceResponseDto {
    private Long id;

    //공연장
    private Long venueId;
    private String venueName;
    private String venueLocation;
    private Long venueTotalSeatCount;

    // 할인 정보
    private Integer discountRate;
    private LocalDateTime discountStartAt;
    private LocalDateTime discountEndAt;

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

    public DiscountPerformanceResponseDto(Performance performance, Sales sale){
        this.id = performance.getId();

        this.venueId = performance.getVenue().getId();
        this.venueName = performance.getVenue().getVenueName();
        this.venueLocation = performance.getVenue().getLocation();
        this.venueTotalSeatCount = performance.getVenue().getTotalSeatCount();

        this.discountStartAt = sale.getStartAt();
        this.discountEndAt = sale.getEndAt();
        this.discountRate = sale.getDiscountRate();

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
