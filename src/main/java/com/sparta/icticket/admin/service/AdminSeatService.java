package com.sparta.icticket.admin.service;

import com.sparta.icticket.common.enums.ErrorType;
import com.sparta.icticket.common.exception.CustomException;
import com.sparta.icticket.seat.Seat;
import com.sparta.icticket.seat.SeatRepository;
import com.sparta.icticket.seat.dto.SeatCreateRequestDto;
import com.sparta.icticket.session.Session;
import com.sparta.icticket.session.SessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminSeatService {

    private final SeatRepository seatRepository;
    private final SessionRepository sessionRepository;

    /**
     * 좌석 생성
     * @param sessionId
     * @param requestDto
     */
    public void createSeat(Long sessionId, SeatCreateRequestDto requestDto) {

        Session findSession = findSession(sessionId);

        if(seatRepository.existsBySessionAndSeatGradeAndSeatNumber(findSession, requestDto.getSeatGrade(), requestDto.getSeatNumber())) {
            throw new CustomException(ErrorType.ALREADY_EXISTS_SEAT);
        }

        Seat saveSeat = new Seat(findSession, requestDto);

        seatRepository.save(saveSeat);
    }

    /**
     * 좌석 삭제
     * @param sessionId
     * @param seatId
     */
    public void deleteSeat(Long sessionId, Long seatId) {

        Session findSession = findSession(sessionId);

        // 해당 아이디를 가진 좌석이 없으면 예외
        Seat findSeat = seatRepository.findByIdAndSession(seatId, findSession).orElseThrow(() ->
                new CustomException(ErrorType.NOT_FOUND_SEAT));

        seatRepository.delete(findSeat);

    }

    /**
     * 세션 검증
     * @param sessionId
     * @return
     */
    private Session findSession(Long sessionId) {
        return sessionRepository.findById(sessionId).orElseThrow(() ->
                new CustomException(ErrorType.NOT_FOUND_SESSION));
    }

}
