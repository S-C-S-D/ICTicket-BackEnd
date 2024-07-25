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
import java.util.List;

@Slf4j(topic = "AdminSessionService")
@Service
@Transactional
@RequiredArgsConstructor
public class AdminSessionService {

    private final SessionRepository sessionRepository;
    private final PerformanceRepository performanceRepository;

    /**
     * session 등록
     * @param loginUser
     * @param performanceId
     * @param createSessionRequestDto
     */
    public void createSession(User loginUser, Long performanceId, CreateSessionRequestDto createSessionRequestDto) {

        Performance performance = validatePerformance(performanceId);
        Session session = new Session(performance, createSessionRequestDto);

        if (isDateAndNameAndTimeAvailable(session)) {
            log.info("유효한 세션");
        }

        sessionRepository.save(session);
        log.info("세션 저장 완료");
    }


    /**
     * session 수정
     * @param loginUser
     * @param performanceId
     * @param sessionId
     * @param updateSessionRequestDto
     */
    public void updateSession(User loginUser, Long performanceId, Long sessionId, UpdateSessionRequestDto updateSessionRequestDto) {

        Session session = validateSession(performanceId, sessionId);
        session.update(updateSessionRequestDto);

        if (isDateAndNameAndTimeAvailable(session)) {
            log.info("유효한 세션");
        }

        log.info("세션 수정 완료");

    }

    /**
     * session 삭제
     * @param loginUser
     * @param performanceId
     * @param sessionId
     */
    public void deleteSession(User loginUser, Long performanceId, Long sessionId) {
        Session session = validateSession(performanceId, sessionId);
        sessionRepository.deleteById(sessionId);
        log.info("세션 삭제 완료");
    }


    /*메서드*/
    private Performance validatePerformance(Long performanceId) {
        return performanceRepository.findById(performanceId)
                .orElseThrow(() -> new CustomException(ErrorType.NOT_FOUND_PERFORMANCE));
    }


    private Session validateSession(Long performanceId, Long sessionId) {
        Session session = sessionRepository.findById(sessionId)
                .orElseThrow(() -> new CustomException(ErrorType.NOT_FOUND_SESSION));

        //유효한 경로인지 확인(session의 performanceId와 경로의 performanceId(Path variable)가 다를수있음)
        boolean isCorrect = session.getPerformance().getId().equals(performanceId);
        if (!isCorrect) {
            throw new CustomException(ErrorType.NOT_VALID_SESSION);
        }

        return session;
    }

    private boolean isDateAndNameAndTimeAvailable(Session session) throws CustomException {
        Performance performance = session.getPerformance();
        LocalDate sessionDate = session.getSessionDate();
        LocalTime sessionTime = session.getSessionTime();
        String sessionName = session.getSessionName();

        LocalDate startDate = performance.getStartAt();
        LocalDate endDate = performance.getEndAt();

        boolean isDateNotAvailable = sessionDate.isBefore(startDate) || sessionDate.isAfter(endDate);
        if (isDateNotAvailable) {
            throw new CustomException(ErrorType.NOT_AVAILABLE_DATE);
        }

        List<Session> sessionsWithSameName = sessionRepository
                .findByPerformanceAndSessionDateAndSessionName(performance, sessionDate, sessionName);

        //수정시 검증해야하는 로직
        if (sessionsWithSameName.size()!=0) {
            for (Session existingSession : sessionsWithSameName) {
                boolean isNameUpdatable=existingSession.getId().equals(session.getId());
                if (!isNameUpdatable) {
                    throw new CustomException(ErrorType.ALREADY_EXISTS_SESSION_NAME);
                }
            }
        }

        List<Session> sessionsWithSameTime = sessionRepository.findByPerformanceAndSessionDateAndSessionTime(performance, sessionDate, sessionTime);
        if (sessionsWithSameTime.size()!=0) {
            for (Session existingSession : sessionsWithSameTime) {
                boolean isTimeUpdatable=existingSession.getId().equals(session.getId());
                if (!isTimeUpdatable) {
                    throw new CustomException(ErrorType.ALREADY_EXISTS_SESSION_TIME);
                }
            }
        }

        return true;
    }


}
