package com.sparta.icticket.seat;

import com.sparta.icticket.common.enums.SeatStatus;
import com.sparta.icticket.session.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface SeatRepository extends JpaRepository<Seat, Long> {
    Integer countBySession(Session findSession);

    Integer countBySessionAndSeatStatus(Session findSession, SeatStatus seatStatus);
}
