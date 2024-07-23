package com.sparta.icticket.seat;

import com.sparta.icticket.session.Session;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SeatRepository extends JpaRepository<Seat, Long> {
    Integer countBySession(Session findSession);

    Integer countBySessionAndReserved(Session findSession, boolean b);
}
