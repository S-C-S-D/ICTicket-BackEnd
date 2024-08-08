package com.sparta.icticket.order.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sparta.icticket.common.enums.GenreType;
import com.sparta.icticket.common.enums.OrderStatus;
import com.sparta.icticket.order.Order;
import lombok.Getter;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalTime;

@Getter
public class OrderListResponseDto {
    private Long performanceId;
    private Long orderId;
    private String imageUrl;
    private Timestamp orderDate;
    private GenreType genreType;
    private String title;
    private Timestamp startAt;
    private Timestamp endAt;
    private String address;
    private String orderNumber;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Timestamp sessionDate;
    @JsonFormat(pattern = "HH:mm")
    private Timestamp sessionTime;
    private Integer ticketCount;
    private OrderStatus orderStatus;

    public OrderListResponseDto(Order order) {
        this.performanceId = order.getSession().getPerformance().getId();
        this.orderId = order.getId();

        this.imageUrl = order.getSession().getPerformance().getImageUrl();
        this.orderDate = order.getCreatedAt();

        this.genreType = order.getSession().getPerformance().getGenreType();
        this.title = order.getSession().getPerformance().getTitle();
        this.startAt = order.getSession().getPerformance().getStartAt();
        this.endAt = order.getSession().getPerformance().getEndAt();
        this.address = order.getSession().getPerformance().getVenue().getLocation();

        this.orderNumber = order.getOrderNumber();
        this.sessionDate = order.getSession().getSessionDate();
        this.sessionTime = order.getSession().getSessionTime();
        this.ticketCount = order.getTicketCount();
        this.orderStatus = order.getOrderStatus();
    }

}
