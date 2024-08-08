package com.sparta.icticket.performance.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sparta.icticket.common.enums.AgeGroup;
import com.sparta.icticket.common.enums.GenreType;
import com.sparta.icticket.performance.Performance;
import com.sparta.icticket.sales.Sales;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
public class PerformanceDiscountResponseDto {
    private Long id;

    //공연장
    private Long venueId;
    private String venueName;
    private String venueLocation;

    // 할인 정보
    private Integer discountRate;
    private LocalDateTime discountStartAt;
    private LocalDateTime discountEndAt;

    // 가장 낮은 등급의 가격
    private int price;

    private String title;
    private GenreType genreType;
    @JsonFormat(pattern = "yyyy-MM-dd-HH:mm")
    private LocalDateTime openAt;
    private LocalDate startAt;
    private LocalDate endAt;
    private String imageUrl;

    public PerformanceDiscountResponseDto(Performance performance, Sales sales) {
        this.id = performance.getId();
        this.venueId = performance.getVenue().getId();
        this.venueName = performance.getVenue().getVenueName();
        this.venueLocation = performance.getVenue().getLocation();

        this.discountRate = sales.getDiscountRate();
        this.discountStartAt = sales.getStartAt();
        this.discountEndAt = sales.getEndAt();

        this.title = performance.getTitle();
        this.genreType = performance.getGenreType();
        this.openAt = performance.getOpenAt();
        this.startAt = performance.getStartAt();
        this.endAt = performance.getEndAt();
        this.imageUrl = performance.getImageUrl();
    }

    public void updatePrice(int price){
        this.price = price;
    }
}
