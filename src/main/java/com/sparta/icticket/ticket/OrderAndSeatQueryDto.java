package com.sparta.icticket.ticket;

import com.sparta.icticket.order.Order;
import com.sparta.icticket.seat.Seat;
import lombok.Getter;

@Getter
public class OrderAndSeatQueryDto {
    private Order orderOfTicket;
    private Seat seatOfTicket;
}
