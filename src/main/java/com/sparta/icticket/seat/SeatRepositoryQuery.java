package com.sparta.icticket.seat;

import java.util.List;

public interface SeatRepositoryQuery {
    void findSeatsBySeatStatus();

    List<Seat> findSeatsByIdList(List<Long> seatIdList);
}
