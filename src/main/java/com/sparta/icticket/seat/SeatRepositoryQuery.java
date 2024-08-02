package com.sparta.icticket.seat;

import com.sparta.icticket.session.Session;

import java.util.List;

public interface SeatRepositoryQuery {
    void findSeatsBySeatStatus();

    List<Seat> findSeatsByIdList(List<Long> seatIdList);

    List<Seat> findSeatById(List<Long> seatIdList, Session session);
}