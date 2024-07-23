package com.sparta.icticket.admin.service;


import com.sparta.icticket.admin.session.dto.CreateSessionRequestDto;
import com.sparta.icticket.admin.session.dto.UpdateSessionRequestDto;
import com.sparta.icticket.common.enums.ErrorType;
import com.sparta.icticket.common.exception.CustomException;
import com.sparta.icticket.performance.Performance;
import com.sparta.icticket.performance.PerformanceRepository;
import com.sparta.icticket.session.Session;
import com.sparta.icticket.session.SessionRepository;
import com.sparta.icticket.user.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;

@Slf4j(topic = "AdminSessionService")
@Service
@Transactional
@RequiredArgsConstructor
public class AdminSessionService {

    private final SessionRepository sessionRepository;
    private final PerformanceRepository performanceRepository;


    /*session 등록*/
    public void createSession(User loginUser, Long performanceId, CreateSessionRequestDto createSessionRequestDto) {

        Performance performance = validatePerformance(performanceId);
        Session session = new Session(performance, createSessionRequestDto);

        validateSessionDate(session);
        checkSessionExists(session);
        log.info("유효한 세션");

        sessionRepository.save(session);
        log.info("세션 저장 완료");
    }

    /*session 수정*/
    public void updateSession(User loginUser, Long performanceId, Long sessionId, UpdateSessionRequestDto updateSessionRequestDto) {

        isPerformanceExist(performanceId);
        Session session = validateSession(performanceId, sessionId);

        validateSessionDate(session);
        checkSessionExists(session);
        log.info("유효한 세션");


        session.update(updateSessionRequestDto);
        log.info("세션 수정 완료");

    }

    /*session 삭제*/
    public void deleteSession(User loginUser, Long performanceId, Long sessionId) {

        isPerformanceExist(performanceId);

        Session session = validateSession(performanceId, sessionId);
        sessionRepository.deleteById(sessionId);
        log.info("세션 삭제 완료");
    }


    /*메서드*/
    private Session validateSession(Long performanceId, Long sessionId) {
        Session session = sessionRepository.findById(sessionId)
                .orElseThrow(() -> new CustomException(ErrorType.NOT_FOUND_SESSION));

        boolean isCorrect = session.getPerformance().getId().equals(performanceId);
        if (!isCorrect) {
            throw new CustomException(ErrorType.NOT_VALID_SESSION);
        }

        return session;
    }

    private void isPerformanceExist(Long performanceId) {
        Boolean exist = performanceRepository.existsById(performanceId);
        if (!exist) {
            throw new CustomException(ErrorType.NOT_FOUND_PERFORMANCE);
        }
    }

    private Performance validatePerformance(Long performanceId) {
        return performanceRepository.findById(performanceId)
                .orElseThrow(() -> new CustomException(ErrorType.NOT_FOUND_PERFORMANCE));
    }

    private void checkSessionExists(Session session) {
        Performance performance = session.getPerformance();
        LocalDate sessionDate = session.getSessionDate();
        LocalTime sessionTime = session.getSessionTime();
        String sessionName = session.getSessionName();

        //입력된 날짜와 시간의 세션이 이미 존재하는 경우
        boolean isSessionTimeExist = sessionRepository.existsByPerformanceAndSessionDateAndSessionTime(performance, sessionDate, sessionTime);
        if (isSessionTimeExist) {
            throw new CustomException(ErrorType.ALREADY_EXISTS_SESSION_TIME);
        }

        //입력된 날짜의 세션이름이 이미 존재하는 경우
        boolean isSessionNameExist = sessionRepository.existsBySessionDateAndSessionName(sessionDate, sessionName);
        if (isSessionNameExist) {
            throw new CustomException(ErrorType.ALREADY_EXISTS_SESSION_NAME);
        }
    }

    private void validateSessionDate(Session session) {
        LocalDate sessionDate = session.getSessionDate();
        LocalDate startDate = session.getPerformance().getStartAt();
        LocalDate endDate = session.getPerformance().getEndAt();
        if (sessionDate.isBefore(startDate) || sessionDate.isAfter(endDate)) {
            throw new CustomException(ErrorType.NOT_AVAILABLE_DATE);
        }
    }

}
