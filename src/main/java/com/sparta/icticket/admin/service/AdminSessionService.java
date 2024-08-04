package com.sparta.icticket.admin.service;


import com.sparta.icticket.common.enums.ErrorType;
import com.sparta.icticket.common.exception.CustomException;
import com.sparta.icticket.performance.Performance;
import com.sparta.icticket.performance.PerformanceRepository;
import com.sparta.icticket.session.Session;
import com.sparta.icticket.session.SessionRepository;
import com.sparta.icticket.session.dto.CreateSessionRequestDto;
import com.sparta.icticket.session.dto.UpdateSessionRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j(topic = "AdminSessionService")
@Service
@Transactional
@RequiredArgsConstructor
public class AdminSessionService {

    private final SessionRepository sessionRepository;
    private final PerformanceRepository performanceRepository;


    /** 세션을 등록하고 수정할때 발생하는 예외상황
     *      1. 날짜와 이름이 같을때
     *      2. 날짜와 시간이 같을때
     *      3. 날짜, 이름, 시간이 모두 같을때
     *
     */

    /**
     * session 등록
     * @param performanceId
     * @param createSessionRequestDto
     */
    public void createSession(Long performanceId, CreateSessionRequestDto createSessionRequestDto) {
        Performance performance = validatePerformance(performanceId);
        Session session = new Session(performance, createSessionRequestDto);
        session.checkDate(createSessionRequestDto.getDate());

        checkSameSession(session,createSessionRequestDto);
        // 이름을 수정하는 경우
        checkSessionName(session, createSessionRequestDto);
        // 시간을 수정하는 경우
        checkSessionTime(session, createSessionRequestDto);
        // 날짜를 수정하는 경우
        checkSessionDate(session, createSessionRequestDto);

        sessionRepository.save(session);
        log.info("세션 저장 완료");
    }

    /**
     * session 수정
     *
     * @param performanceId
     * @param sessionId
     * @param updateSessionRequestDto
     */
    public void updateSession(Long performanceId, Long sessionId, UpdateSessionRequestDto updateSessionRequestDto) {
        log.info("=============session과 함께 performance가 불러와짐 ==============");
        Session session = validateSession(performanceId, sessionId);
        log.info("=============여기사이에 performance 관련 쿼리가 없어야함==============");
        session.checkDate(updateSessionRequestDto.getDate());
        log.info("=============여기사이에 performance 관련 쿼리가 없어야함==============");
        checkSameSession(sessionId, session, updateSessionRequestDto);
        // 이름을 수정하는 경우
        checkValidSessionName(session, updateSessionRequestDto);
        // 시간을 수정하는 경우
        checkValidSessionTime(session, updateSessionRequestDto);
        // 날짜를 수정하는 경우
        checkValidSessionDate(session, updateSessionRequestDto);

        session.update(updateSessionRequestDto);
        log.info("세션 수정 완료");

    }

    /**
     * session 삭제
     * @param performanceId
     * @param sessionId
     */
    public void deleteSession(Long performanceId, Long sessionId) {
        Session session = validateSession(performanceId, sessionId);
        sessionRepository.deleteById(sessionId);
        log.info("세션 삭제 완료");
    }

    /**
     * 세션 검증메서드(create용)
     * @param session
     * @param createSessionRequestDto
     */
    private void checkSessionName(Session session, CreateSessionRequestDto createSessionRequestDto) {
        boolean result = sessionRepository.existsByPerformanceAndSessionDateAndSessionName(
                session.getPerformance(),
                createSessionRequestDto.getDate(),
                createSessionRequestDto.getName()
        );
        if (result) {
            throw new CustomException(ErrorType.ALREADY_EXISTS_SESSION_NAME);
        }
    }

    private void checkSessionTime(Session session, CreateSessionRequestDto createSessionRequestDto) {
        boolean result = sessionRepository.existsByPerformanceAndSessionDateAndSessionTime(
                session.getPerformance(),
                createSessionRequestDto.getDate(),
                createSessionRequestDto.getTime()
        );
        if (result) {
            throw new CustomException(ErrorType.ALREADY_EXISTS_SESSION_TIME);
        }
    }

    private void checkSessionDate(Session session, CreateSessionRequestDto createSessionRequestDto) {
        checkSessionName(session, createSessionRequestDto);
        checkSessionTime(session, createSessionRequestDto);
    }

    private void checkSameSession(Session session,CreateSessionRequestDto createSessionRequestDto) {
        boolean existsSameSession = sessionRepository.existsByPerformanceAndSessionDateAndSessionNameAndSessionTime(
                session.getPerformance(),
                createSessionRequestDto.getDate(),
                createSessionRequestDto.getName(),
                createSessionRequestDto.getTime()
        );
        if (existsSameSession) {
            throw new CustomException(ErrorType.ALREADY_EXISTS_SESSION);
        }

    }


    /**
     * 세션 검증 메서드(update용)
     * @param sessionId
     * @param session
     * @param updateSessionRequestDto
     */
    private void checkSameSession(Long sessionId,Session session, UpdateSessionRequestDto updateSessionRequestDto) {
         List<Session> sessions = sessionRepository.findByPerformanceAndSessionDateAndSessionNameAndSessionTime(
                session.getPerformance(),
                updateSessionRequestDto.getDate(),
                updateSessionRequestDto.getName(),
                updateSessionRequestDto.getTime()
        );
        for (Session findSession : sessions) {
            if (!sessions.isEmpty()&&sessionId.equals(findSession.getId())) {
                throw new CustomException(ErrorType.NOT_FOUND_MODIFICATIONS);
            }
        }
        if (!sessions.isEmpty()) {
            throw new CustomException(ErrorType.ALREADY_EXISTS_SESSION);
        }
    }

    private void checkValidSessionName(Session session, UpdateSessionRequestDto updateSessionRequestDto) {
        if (!updateSessionRequestDto.getName().equals(session.getSessionName())) {
            List<Session> sessions = sessionRepository.findByPerformanceAndSessionDateAndSessionName(
                    session.getPerformance(),
                    updateSessionRequestDto.getDate(),
                    updateSessionRequestDto.getName()
            );
            for (Session findSession : sessions) {
                if (!findSession.getId().equals(session.getId())) {
                    throw new CustomException(ErrorType.ALREADY_EXISTS_SESSION_NAME);
                }
            }
        }
    }

    private void checkValidSessionTime(Session session, UpdateSessionRequestDto updateSessionRequestDto) {
        if (!updateSessionRequestDto.getTime().equals(session.getSessionTime())) {
            boolean result = sessionRepository.existsByPerformanceAndSessionDateAndSessionTime(
                    session.getPerformance(),
                    updateSessionRequestDto.getDate(),
                    updateSessionRequestDto.getTime()
            );
            if (result) {
                throw new CustomException(ErrorType.ALREADY_EXISTS_SESSION_TIME);
            }
        }
    }

    private void checkValidSessionDate(Session session, UpdateSessionRequestDto updateSessionRequestDto ) {
        checkValidSessionName(session, updateSessionRequestDto);
        checkValidSessionTime(session, updateSessionRequestDto);
    }


    private Performance validatePerformance(Long performanceId) {
        return performanceRepository.findById(performanceId)
                .orElseThrow(() -> new CustomException(ErrorType.NOT_FOUND_PERFORMANCE));
    }

    private Session validateSession(Long performanceId, Long sessionId) {
        Session session = sessionRepository.findById(sessionId)
                .orElseThrow(() -> new CustomException(ErrorType.NOT_FOUND_SESSION));

        //유효한 경로인지 확인(session의 performanceId와 경로의 performanceId(Path variable)가 다를수있음)
        session.checkPerformance(performanceId);

        return session;
    }






}
