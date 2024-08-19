package com.sparta.icticket.seat.dto;

import com.sparta.icticket.performance.Performance;
import com.sparta.icticket.session.Session;
import lombok.Getter;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Getter
public class SeatReservedResponseDto {
    private String title;
    private String imageUrl;
    private LocalDate sessionDate;
    private LocalTime sessionTime;
    private String sessionName;
    private List<String> seatNumberList;
    private Integer totalPrice;
    private Integer discountRate;

    public SeatReservedResponseDto(Performance performance, Session session, List<String> seatNumberList, Integer totalPrice, Integer discountRate) {
        this.title = performance.getTitle();
        this.imageUrl = performance.getImageUrl();
        this.sessionDate = session.getSessionDate();
        this.sessionTime = session.getSessionTime();
        this.sessionName = session.getSessionName();
        this.seatNumberList = seatNumberList;
        this.totalPrice = (int) (totalPrice - (totalPrice * (discountRate / 100.0)));
        this.discountRate = discountRate;
    }
}
