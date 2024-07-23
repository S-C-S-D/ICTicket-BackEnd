package com.sparta.icticket.seat;

import com.sparta.icticket.session.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface SeatRepository extends JpaRepository<Seat, Long> {
    Integer countBySession(Session findSession);

    @Query("SELECT count(*) from Seat where session = :findSession and isReserved = :b")
    Integer countBySessionAndReserved(Session findSession, boolean b);
}
