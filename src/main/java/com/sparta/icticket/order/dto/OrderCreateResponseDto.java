package com.sparta.icticket.order.dto;

import com.sparta.icticket.session.Session;
import com.sparta.icticket.user.User;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
public class OrderCreateResponseDto {

    private String title;
    private String imageUrl;
    private LocalDate sessionDate;
    private String sessionName;
    private List<String> seatNumberList;
    private Integer totalPrice;
    private Integer discountRate;
    private String address;
    private String phoneNumber;
    private LocalDate orderDate;
    private String OrderNumber;

    public OrderCreateResponseDto(Session session, List<String> seatNumberList, Integer totalPrice, Integer discountRate, User user) {
        this.title = session.getPerformance().getTitle();
        this.imageUrl = session.getPerformance().getImageUrl();
        this.sessionDate = session.getSessionDate();
        this.sessionName = session.getSessionName();
        this.seatNumberList = seatNumberList;
        this.totalPrice = totalPrice;
        this.discountRate = discountRate;
        this.address = user.getAddress();
        this.phoneNumber = user.getPhoneNumber();
        this.orderDate = LocalDate.now();
    }
}
