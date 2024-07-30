package com.sparta.icticket.seat;

import com.sparta.icticket.common.enums.SeatGrade;
import com.sparta.icticket.common.enums.SeatStatus;
import com.sparta.icticket.session.Session;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SeatRepository extends JpaRepository<Seat, Long> {
    Integer countBySession(Session findSession);

    Integer countBySessionAndSeatStatus(Session findSession, SeatStatus seatStatus);

    boolean existsBySessionAndSeatGradeAndSeatNumber(Session findSession, SeatGrade seatGrade, String seatNumber);

    Optional<Seat> findByIdAndSession(Long seatId, Session findSession);
}
