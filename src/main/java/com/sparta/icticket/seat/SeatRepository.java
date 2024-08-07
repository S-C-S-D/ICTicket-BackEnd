package com.sparta.icticket.seat;

import com.sparta.icticket.common.enums.SeatGrade;
import com.sparta.icticket.common.enums.SeatStatus;
import com.sparta.icticket.session.Session;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SeatRepository extends JpaRepository<Seat, Long>, SeatRepositoryQuery {

    // 해당 session을 가진 seat 객체의 개수 조회
    Integer countBySession(Session findSession);

    // 해당 session과 seat_status를 가진 seat 객체의 개수 조회
    Integer countBySessionAndSeatStatus(Session findSession, SeatStatus seatStatus);

    // 해당 session, seat_grade, seat_number를 가진 seat 객체의 존재 여부 조회
    boolean existsBySessionAndSeatGradeAndSeatNumber(Session findSession, SeatGrade seatGrade, String seatNumber);

    // 해당 id와 session을 가진 seat 객체 조회
    Optional<Seat> findByIdAndSession(Long seatId, Session findSession);

    // 해당 session_id를 가진 seat 객체 조회
    List<Seat> findAllBySessionId(Long sessionId);
}
