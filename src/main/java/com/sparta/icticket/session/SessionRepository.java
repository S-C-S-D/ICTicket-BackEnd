package com.sparta.icticket.session;

import com.sparta.icticket.performance.Performance;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public interface SessionRepository extends JpaRepository<Session, Long> {

    // 해당공연의 기존세션들 중 세션날짜와 세션시간이 중복되는 세션이 있는지 검사
    boolean existsByPerformanceAndSessionDateAndSessionTime(Performance performance, Timestamp sessionDate, Timestamp sessionTime);

    // 해당공연의 기존세션들 중 세션날짜와 세션이름이 중복되는 세션이 있는지 검사
    boolean existsByPerformanceAndSessionDateAndSessionName(Performance performance, Timestamp sessionDate, String sessionName);

    // 해당공연의 기존세션들 중 세션날짜와 세션이름이 중복되는 세션 가져오기
    List<Session> findByPerformanceAndSessionDateAndSessionName(Performance performance, Timestamp sessionDate, String sessionName);

    // performanceId를 이용하여 sort에 있는 요소 순서대로 해당공연의 세션(들) 가져오기
    Optional<List<Session>> findByPerformanceId(Long performanceId, Sort sort);

    // 해당공연의 기존세션들 중 세션날짜, 세션이름,세션 시간 모두 같은 세션 가져오기
    List<Session> findByPerformanceAndSessionDateAndSessionNameAndSessionTime(Performance performance, Timestamp date, String name, Timestamp time);

    // 해당공연의 기존세션들 중 세션날짜, 세션이름,세션 시간 모두 같은 세션이 있는지 검사
    boolean existsByPerformanceAndSessionDateAndSessionNameAndSessionTime(Performance performance, Timestamp date, String name, Timestamp time);

}
