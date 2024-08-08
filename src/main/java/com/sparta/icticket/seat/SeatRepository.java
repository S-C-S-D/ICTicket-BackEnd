package com.sparta.icticket.seat;

import com.sparta.icticket.common.enums.SeatGrade;
import com.sparta.icticket.common.enums.SeatStatus;
import com.sparta.icticket.session.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

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

    @Query(value = "SELECT s.* " +
            "FROM seats s " +
            "LEFT JOIN sessions ss ON s.session_id = ss.id " +
            "JOIN ( " +
            "    SELECT s2.seat_grade, MIN(s2.id) AS min_id " +
            "    FROM seats s2 " +
            "    LEFT JOIN sessions ss2 ON s2.session_id = ss2.id " +
            "    WHERE ss2.performance_id = :performanceId " +
            "    GROUP BY s2.seat_grade) sub ON s.id = sub.min_id", nativeQuery = true)
    List<Seat> findSeatsPerGradeByPerformanceId(Long performanceId);

    @Query(value = "select min(s.price) from seats s " +
            "left join sessions ss on s.session_id = ss.id " +
            "where ss.performance_id in :performanceIdList " +
            "group by performance_id", nativeQuery = true)
    List<Integer> getMinPricesByPerformanceIds(List<Long> performanceIdList);
}
