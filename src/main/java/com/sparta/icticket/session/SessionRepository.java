package com.sparta.icticket.session;

import com.sparta.icticket.performance.Performance;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.swing.text.html.Option;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public interface SessionRepository extends JpaRepository<Session, Long> {
    List<Session> findByPerformanceAndSessionDateAndSessionTime(Performance performance, LocalDate sessionDate, LocalTime sessionTime);

    List<Session> findByPerformanceAndSessionDateAndSessionName(Performance performance,LocalDate sessionDate,String sessionName);

    boolean existsByIdAndSessionDateAndSessionTime(Long sessionId, LocalDate sessionDate, LocalTime sessionTime);

    boolean existsByIdAndSessionDateAndSessionName(Long sessionId, LocalDate sessionDate, String sessionName);

    Optional<List<Session>> findByPerformanceId(Long performanceId, Sort sort);

}
