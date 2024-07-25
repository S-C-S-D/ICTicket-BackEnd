package com.sparta.icticket.order;

import com.sparta.icticket.seat.Seat;

import java.util.List;

public interface OrderRepositoryQuery {

    List<String> findSeatNumberById(List<Long> seatIdList);

    Integer sumTotalPrice(List<Long> seatIdList);

    List<Seat> findSeatById(List<Long> seatIdList);
}
