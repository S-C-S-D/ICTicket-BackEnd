//package com.sparta.icticket.admin.service;
//
//
//import com.sparta.icticket.admin.session.dto.CreateSessionRequestDto;
//import com.sparta.icticket.admin.session.dto.UpdateSessionRequestDto;
//import com.sparta.icticket.common.enums.ErrorType;
//import com.sparta.icticket.common.exception.CustomException;
//import com.sparta.icticket.performance.Performance;
//import com.sparta.icticket.performance.PerformanceRepository;
//import com.sparta.icticket.session.Session;
//import com.sparta.icticket.session.SessionRepository;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.time.LocalDate;
//import java.util.List;
//
//@Slf4j(topic = "AdminSessionService")
//@Service
//@Transactional
//@RequiredArgsConstructor
//public class AdminSessionService {
//
//    private final SessionRepository sessionRepository;
//    private final PerformanceRepository performanceRepository;
//
//    /**
//     * session 등록
//     * @param performanceId
//     * @param createSessionRequestDto
//     */
//    public void createSession(Long performanceId, CreateSessionRequestDto createSessionRequestDto) {
//
//
//        Performance performance = validatePerformance(performanceId);
//
//        Session session = new Session(performance, createSessionRequestDto);
//
//        LocalDate sessionDate = session.getSessionDate();
//        boolean result = sessionRepository
//                .existsByPerformanceAndSessionDateAndSessionName(performance, sessionDate, session.getSessionName());
//
//        if (result) {
//            throw new CustomException(ErrorType.ALREADY_EXISTS_SESSION_NAME);
//        }
//
//        // 날짜와 시간이 중복인 경우
//        boolean result2 = sessionRepository
//                .existsByPerformanceAndSessionDateAndSessionTime(performance, sessionDate, session.getSessionTime());
//        if (result2) {
//            throw new CustomException(ErrorType.ALREADY_EXISTS_SESSION_TIME);
//        }
//
//        sessionRepository.save(session);
//        log.info("세션 저장 완료");
//    }
//
//
//    /**
//     * session 수정
//     * @param performanceId
//     * @param sessionId
//     * @param updateSessionRequestDto
//     */
//    public void updateSession(Long performanceId, Long sessionId, UpdateSessionRequestDto updateSessionRequestDto) {
//
//        Session session = validateSession(performanceId, sessionId);
//
//        Performance performance=session.getPerformance();
//        LocalDate sessionDate = session.getSessionDate();
//        List<Session> existingSessionName = sessionRepository
//                .findByPerformanceAndSessionDateAndSessionName(performance, sessionDate, session.getSessionName());
//
//        for (Session session1 : existingSessionName) {
//            if (!session.getId().equals(sessionId)) {
//                throw new CustomException((ErrorType.ALREADY_EXISTS_SESSION_NAME));
//            }
//        }
//
//        // 날짜와 시간이 중복인 경우
//        List<Session> existingSessionTime = sessionRepository
//                .findByPerformanceAndSessionDateAndSessionTime(performance, sessionDate, session.getSessionTime());
//        for (Session session1 : existingSessionTime) {
//            if (!session.getId().equals(sessionId)) {
//                throw new CustomException((ErrorType.ALREADY_EXISTS_SESSION_TIME));
//            }
//        }
//
//        session.update(updateSessionRequestDto);
//        log.info("세션 수정 완료");
//
//    }
//
//    /**
//     * session 삭제
//     * @param performanceId
//     * @param sessionId
//     */
//    public void deleteSession(Long performanceId, Long sessionId) {
//        Session session = validateSession(performanceId, sessionId);
//        sessionRepository.deleteById(sessionId);
//        log.info("세션 삭제 완료");
//    }
//
//
//    /*메서드*/
//    private Performance validatePerformance(Long performanceId) {
//        return performanceRepository.findById(performanceId)
//                .orElseThrow(() -> new CustomException(ErrorType.NOT_FOUND_PERFORMANCE));
//    }
//
//
//    private Session validateSession(Long performanceId, Long sessionId) {
//        Session session = sessionRepository.findById(sessionId)
//                .orElseThrow(() -> new CustomException(ErrorType.NOT_FOUND_SESSION));
//
//        //유효한 경로인지 확인(session의 performanceId와 경로의 performanceId(Path variable)가 다를수있음)
//        session.checkPerformance(performanceId);
//        session.checkDate();
//
//        return session;
//    }
//
//    // create용 세션 유효성 검사
//    private void validSession(Session session) throws CustomException {
//        session
//        // 날짜와 이름이 중복인 경우
//        boolean result = sessionRepository
//                .existsByPerformanceAndSessionDateAndSessionName(performance, sessionDate, sessionName);
//
//        if (result) {
//            throw new CustomException(ErrorType.ALREADY_EXISTS_SESSION_NAME);
//        }
//
//        // 날짜와 시간이 중복인 경우
//        boolean result2 = sessionRepository
//                .existsByPerformanceAndSessionDateAndSessionTime(performance, sessionDate, sessionTime);
//        if (result2) {
//            throw new CustomException(ErrorType.ALREADY_EXISTS_SESSION_TIME);
//        }
//
//    }
//
//
//    private void validSession(Session session) throws CustomException {
//        // 날짜와 이름이 중복인 경우
//        boolean result = sessionRepository
//                .existsByPerformanceAndSessionDateAndSessionName(performance, sessionDate, sessionName);
//
//        if (result) {
//            throw new CustomException(ErrorType.ALREADY_EXISTS_SESSION_NAME);
//        }
//
//        // 날짜와 시간이 중복인 경우
//        boolean result2 = sessionRepository
//                .existsByPerformanceAndSessionDateAndSessionTime(performance, sessionDate, sessionTime);
//        if (result2) {
//            throw new CustomException(ErrorType.ALREADY_EXISTS_SESSION_TIME);
//        }
//
//    }
//
//
//}
