package com.sparta.icticket.order.dto;


import com.sparta.icticket.order.Order;
import lombok.Getter;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;

@Getter
public class OrderCreateResponseDto {

    private String title;
    private String imageUrl;
    private Timestamp sessionDate;
    private String sessionName;
    private List<String> seatNumberList;
    private Integer totalPrice;
    private Integer discountRate;
    private String address;
    private String phoneNumber;
    private Timestamp orderDate;
    private String OrderNumber;

    public OrderCreateResponseDto(Order order, List<String> seatNumberList, Integer discountRate) {
        this.title = order.getSession().getPerformance().getTitle();
        this.imageUrl = order.getSession().getPerformance().getImageUrl();

        this.sessionDate = order.getSession().getSessionDate();
        this.sessionName = order.getSession().getSessionName();

        this.seatNumberList = seatNumberList;

        this.totalPrice = order.getTotalPrice() - (order.getTotalPrice() * (discountRate/100));
        this.discountRate = discountRate;

        this.address = order.getUser().getAddress();
        this.phoneNumber = order.getUser().getPhoneNumber();

        this.orderDate = new Timestamp(System.currentTimeMillis());
        this.OrderNumber = order.getOrderNumber();
    }
}
