package com.sparta.icticket.order;

import com.sparta.icticket.seat.Seat;

import java.util.List;

public interface OrderRepositoryQuery {
    List<Seat> findSeatById(List<Long> seatIdList);
}
