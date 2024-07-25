package com.sparta.icticket.order;

import com.sparta.icticket.seat.Seat;
import com.sparta.icticket.session.Session;

import java.util.List;

public interface OrderRepositoryQuery {
    List<Seat> findSeatById(List<Long> seatIdList, Session session);
}
