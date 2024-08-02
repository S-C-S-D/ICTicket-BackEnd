package com.sparta.icticket.session;

import com.sparta.icticket.performance.Performance;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public interface SessionRepository extends JpaRepository<Session, Long> {
    boolean existsByPerformanceAndSessionDateAndSessionTime(Performance performance, LocalDate sessionDate, LocalTime sessionTime);

    boolean existsByPerformanceAndSessionDateAndSessionName(Performance performance, LocalDate sessionDate, String sessionName);

    Optional<List<Session>> findByPerformanceId(Long performanceId, Sort sort);

    List<Session> findByPerformanceAndSessionDateAndSessionName(Performance performance, LocalDate sessionDate, String sessionName);

    List<Session> findByPerformanceAndSessionDateAndSessionNameAndSessionTime(Performance performance, LocalDate date, String name, LocalTime time);

    boolean existsByPerformanceAndSessionDateAndSessionNameAndSessionTime(Performance performance, LocalDate date, String name, LocalTime time);

    @Query("select s from Session s join fetch s.performance where s.id = :sessionId")
    Optional<Session> findById(@Param("sessionId") Long sessionId);
}


