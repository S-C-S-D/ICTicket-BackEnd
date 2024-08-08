package com.sparta.icticket.seat;

import com.sparta.icticket.common.enums.SeatGrade;
import com.sparta.icticket.common.enums.SeatStatus;
import com.sparta.icticket.session.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface SeatRepository extends JpaRepository<Seat, Long>, SeatRepositoryQuery {
    Integer countBySession(Session findSession);

    Integer countBySessionAndSeatStatus(Session findSession, SeatStatus seatStatus);

    boolean existsBySessionAndSeatGradeAndSeatNumber(Session findSession, SeatGrade seatGrade, String seatNumber);

    Optional<Seat> findByIdAndSession(Long seatId, Session findSession);

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
