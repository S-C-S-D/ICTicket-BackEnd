package com.sparta.icticket.session;

import com.sparta.icticket.performance.Performance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalTime;

public interface SessionRepository extends JpaRepository<Session, Long> {
    boolean existsByPerformanceAndSessionDateAndSessionTime(Performance performance, LocalDate sessionDate, LocalTime sessionTime);

    boolean existsBySessionDateAndSessionName(LocalDate sessionDate,String sessionName);
}
