package com.sparta.icticket.seat;

import com.sparta.icticket.common.enums.ErrorType;
import com.sparta.icticket.common.exception.CustomException;
import com.sparta.icticket.performance.Performance;
import com.sparta.icticket.performance.PerformanceRepository;
import com.sparta.icticket.seat.dto.SeatCountResponseDto;
import com.sparta.icticket.session.Session;
import com.sparta.icticket.session.SessionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class SeatService {

    private final SeatRepository seatRepository;
    private final PerformanceRepository performanceRepository;
    private final SessionRepository sessionRepository;

    /**
     * 세션별 잔여 좌석 조회
     * @param performanceId
     * @param sessionId
     * @return
     */
    public SeatCountResponseDto getSeatCount(Long performanceId, Long sessionId) {
        Performance findPerformance = checkPerformance(performanceId);
        Session findSession = checkSession(sessionId);

        // 세션이 해당 공연의 세션이 아닐 때 예외
        if(!findPerformance.equals(findSession.getPerformance())) {
            throw new CustomException(ErrorType.NOT_FOUND_SESSION);
        }

        Integer totalSeatCount = seatRepository.countBySession(findSession);
        Integer restSeatCount = seatRepository.countBySessionAndReserved(findSession, false);

        return new SeatCountResponseDto(totalSeatCount, restSeatCount);
    }

    /**
     * 공연 검증
     * @param performanceId
     * @return
     */
    private Performance checkPerformance(Long performanceId) {
        return performanceRepository.findById(performanceId).orElseThrow(() ->
                new CustomException(ErrorType.NOT_FOUND_PERFORMANCE));
    }

    /**
     * 세션 검증
     * @param sessionId
     * @return
     */
    private Session checkSession(Long sessionId) {
        return sessionRepository.findById(sessionId).orElseThrow(() ->
                new CustomException(ErrorType.NOT_FOUND_SESSION));
    }
}
