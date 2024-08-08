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
     */

    /**
     * 세션 등록
     * @param performanceId
     * @param createSessionRequestDto
     */
    public void createSession(Long performanceId, CreateSessionRequestDto createSessionRequestDto) {
        Performance performance = findPerformance(performanceId);
        Session session = new Session(performance, createSessionRequestDto);
        session.checkDate(createSessionRequestDto.getDate());

        checkSameSessionWhenCreating(session,createSessionRequestDto);
        // 이름을 수정하는 경우
        checkSessionNameWhenCreating(session, createSessionRequestDto);
        // 시간을 수정하는 경우
        checkSessionTimeWhenCreating(session, createSessionRequestDto);
        // 날짜를 수정하는 경우
        checkSessionDateWhenCreating(session, createSessionRequestDto);

        sessionRepository.save(session);
        log.info("세션 저장 완료");
    }

    /**
     * 세션 수정
     * @param performanceId
     * @param sessionId
     * @param updateSessionRequestDto
     */
    public void updateSession(Long performanceId, Long sessionId, UpdateSessionRequestDto updateSessionRequestDto) {
        Session session = findSession(performanceId, sessionId);
        session.checkDate(updateSessionRequestDto.getDate());
        checkSameSessionWhenUpdating(sessionId,session, updateSessionRequestDto);
        // 이름을 수정하는 경우
        checkSessionNameWhenUpdating(session,updateSessionRequestDto);
        // 시간을 수정하는 경우
        checkSessionTimeWhenUpdating(session,updateSessionRequestDto);
        // 날짜를 수정하는 경우
        checkSessionDateWhenUpdating(session,updateSessionRequestDto);

        session.update(updateSessionRequestDto);
        log.info("세션 수정 완료");

    }

    /**
     * 세션 삭제
     * @param performanceId
     * @param sessionId
     */
    public void deleteSession(Long performanceId, Long sessionId) {
        Session session = findSession(performanceId, sessionId);
        sessionRepository.deleteById(sessionId);
        log.info("세션 삭제 완료");
    }

    /**
     * 세션이름 검증메서드(create용)
     * @param session
     * @param createSessionRequestDto
     * @description 해당 공연의 세션들 중 사용자(admin)가 입력한 세션이름과 중복된 세션이 있으면 예외처리해주었다
     */
    private void checkSessionNameWhenCreating(Session session, CreateSessionRequestDto createSessionRequestDto) {
        boolean result = sessionRepository.existsByPerformanceAndSessionDateAndSessionName(
                session.getPerformance(),
                createSessionRequestDto.getDate(),
                createSessionRequestDto.getName()
        );
        if (result) {
            throw new CustomException(ErrorType.ALREADY_EXISTS_SESSION_NAME);
        }
    }

    /**
     * 세션시간 검증메서드(create용)
     * @param session
     * @param createSessionRequestDto
     * @description 해당 공연의 세션들 중 사용자(admin)가 입력한 세션시간과 중복된 세션이 있으면 예외처리해주었다
     */
    private void checkSessionTimeWhenCreating(Session session, CreateSessionRequestDto createSessionRequestDto) {
        boolean result = sessionRepository.existsByPerformanceAndSessionDateAndSessionTime(
                session.getPerformance(),
                createSessionRequestDto.getDate(),
                createSessionRequestDto.getTime()
        );
        if (result) {
            throw new CustomException(ErrorType.ALREADY_EXISTS_SESSION_TIME);
        }
    }

    /**
     * 세션날짜 검증 메서드(create용)
     * @param session
     * @param createSessionRequestDto
     * @description 해당 공연의 세션들 중 사용자(admin)가 입력한 세션날짜과 중복된 세션이 있으면 예외처리해주었다
     */
    private void checkSessionDateWhenCreating(Session session, CreateSessionRequestDto createSessionRequestDto) {
        checkSessionNameWhenCreating(session, createSessionRequestDto);
        checkSessionTimeWhenCreating(session, createSessionRequestDto);
    }

    /**
     * 중복 세션 검증메서드
     * @param session
     * @param createSessionRequestDto
     * @description 세션 등록시 해당 공연의 세션들 중 사용자(admin)가 입력한 세션과 완전히 같은 세션이 있으면 예외처리해주었다.
     */
    private void checkSameSessionWhenCreating(Session session, CreateSessionRequestDto createSessionRequestDto) {
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
     * @description 세션 수정시 기존의 세션에서 변경사항이 없을 경우와 수정한 내용의 세션이 이미 존재하는 경우에 대해 예외처리해주었다
     */
    private void checkSameSessionWhenUpdating(Long sessionId, Session session, UpdateSessionRequestDto updateSessionRequestDto) {
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

    /**
     * 세션이름 검증 메서드(update용)
     * @param session
     * @param updateSessionRequestDto
     * @description 해당 공연의 기존세션들 중 사용자(admin)가 입력한 세션이름과 중복된 세션이 있으면 예외처리해주었다
     */
    private void checkSessionNameWhenUpdating(Session session, UpdateSessionRequestDto updateSessionRequestDto) {
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

    /**
     * 세션시간 검증 메서드(update용)
     * @param session
     * @param updateSessionRequestDto
     * @description 해당 공연의 기존세션들 중 사용자(admin)가 입력한 세션시간과 중복된 세션이 있으면 예외처리해주었다
     */
    private void checkSessionTimeWhenUpdating(Session session, UpdateSessionRequestDto updateSessionRequestDto) {
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

    /**
     * 세션날짜 검증 메서드(update용)
     * @param session
     * @param updateSessionRequestDto
     * @description 해당 공연의 기존세션들 중 사용자(admin)가 입력한 세션날짜와 중복된 세션이 있으면 예외처리해주었다
     *              날짜를 변경할 경우, 변경할 날짜와 해당 세션의 이름 조합을 가진 세션이 기존 세션들 중에 있는지 검증해야하고,
     *              변경할 날짜와 해당 세션의 시간 조합을 가진 세션이 기존 세션들 중에 있는지 또한 검증해야한다.
     */
    private void checkSessionDateWhenUpdating(Session session, UpdateSessionRequestDto updateSessionRequestDto ) {
        checkSessionNameWhenUpdating(session, updateSessionRequestDto);
        checkSessionTimeWhenUpdating(session, updateSessionRequestDto);
    }

    /**
     * 세션이 속한 공연 찾기
     * @param performanceId
     * @description 세션 생성시 url을 통해 전달해준 performanceId를 이용하여 세션이 속한 공연을 찾는다.
     */
    private Performance findPerformance(Long performanceId) {
        return performanceRepository.findById(performanceId)
                .orElseThrow(() -> new CustomException(ErrorType.NOT_FOUND_PERFORMANCE));
    }

    /**
     * 세션 찾기 및 url의 유효성 검사
     * @param performanceId
     * @param sessionId
     * @description  세션 수정과 세션 삭제시 url을 통해 전달해준 sessionId를 이용하여 사용자(admin)가 수정 혹은 삭제하기로 한 세션을 DB에서 찾는다.
     *               이때, 세션의 performanceId와 url의 performanceId를 비교하여 유효한 경로인지 확인한다
     */
    private Session findSession(Long performanceId, Long sessionId) {
        Session session = sessionRepository.findById(sessionId)
                .orElseThrow(() -> new CustomException(ErrorType.NOT_FOUND_SESSION));

        //유효한 경로인지 확인(세션의 performanceId와 경로의 performanceId(Path variable)가 다를수있음)
        session.checkPerformance(performanceId);

        return session;
    }
}
