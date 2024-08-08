package com.sparta.icticket.performance.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sparta.icticket.common.enums.AgeGroup;
import com.sparta.icticket.common.enums.GenreType;
import com.sparta.icticket.common.enums.SeatGrade;
import com.sparta.icticket.performance.Performance;
import com.sparta.icticket.sales.Sales;
import com.sparta.icticket.seat.Seat;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class PerformanceDetailResponseDto {
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

    // 가격 정보
    private SeatGrade[] seatGrades;
    private int[] prices;

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

    public PerformanceDetailResponseDto(Performance performance, Sales sale, List<Seat> seatList){
        this.id = performance.getId();

        this.venueId = performance.getVenue().getId();
        this.venueName = performance.getVenue().getVenueName();
        this.venueLocation = performance.getVenue().getLocation();
        this.venueTotalSeatCount = performance.getVenue().getTotalSeatCount();

        if(sale != null) {
            this.discountStartAt = sale.getStartAt();
            this.discountEndAt = sale.getEndAt();
            this.discountRate = sale.getDiscountRate();
        }

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

        this.seatGrades = new SeatGrade[seatList.size()];
        this.prices = new int[seatList.size()];
        for (int i = 0; i < seatList.size(); i++) {
            this.seatGrades[i] = seatList.get(i).getSeatGrade();
            this.prices[i] = seatList.get(i).getPrice();
        }
    }
}
