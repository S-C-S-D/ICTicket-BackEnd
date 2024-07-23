package com.sparta.icticket.session;

import com.sparta.icticket.performance.Performance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface SessionRepository extends JpaRepository<Session, Long> {
    boolean existsByPerformanceAndSessionDateAndSessionTime(Performance performance, LocalDate sessionDate, LocalTime sessionTime);

    boolean existsByPerformanceAndSessionDateAndSessionName(Performance performance,LocalDate sessionDate,String sessionName);

    boolean existsByIdAndSessionDateAndSessionTime(Long sessionId, LocalDate sessionDate, LocalTime sessionTime);

    boolean existsByIdAndSessionDateAndSessionName(Long sessionId, LocalDate sessionDate, String sessionName);
}
