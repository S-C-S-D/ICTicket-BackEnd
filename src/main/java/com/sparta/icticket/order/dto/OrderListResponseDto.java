package com.sparta.icticket.order.dto;

import com.sparta.icticket.common.enums.GenreType;
import com.sparta.icticket.common.enums.OrderStatus;
import com.sparta.icticket.order.Order;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
public class OrderListResponseDto {

    private LocalDate orderDate;
    private GenreType genreType;
    private String title;
    private LocalDate startAt;
    private LocalDate endAt;
    private String address;
    private String orderNumber;
    private LocalDate sessionDate;
    private LocalTime sessionTime;
    private Integer ticketCount;
    private OrderStatus orderStatus;

    public OrderListResponseDto(Order order) {
        this.orderDate = order.getCreatedAt().toLocalDate();

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
