package com.sparta.icticket.admin.service;

import com.sparta.icticket.admin.seat.dto.SeatCreateRequestDto;
import com.sparta.icticket.common.enums.ErrorType;
import com.sparta.icticket.common.enums.UserRole;
import com.sparta.icticket.common.exception.CustomException;
import com.sparta.icticket.seat.Seat;
import com.sparta.icticket.seat.SeatRepository;
import com.sparta.icticket.session.Session;
import com.sparta.icticket.session.SessionRepository;
import com.sparta.icticket.user.User;
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
     * @param loginUser
     */
    public void createSeat(Long sessionId, SeatCreateRequestDto requestDto, User loginUser) {

        checkUserRole(loginUser.getUserRole());

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
     * @param loginUser
     */
    public void deleteSeat(Long sessionId, Long seatId, User loginUser) {

        checkUserRole(loginUser.getUserRole());

        Session findSession = findSession(sessionId);

        // 해당 아이디를 가진 좌석이 없으면 예외
        Seat findSeat = seatRepository.findById(seatId).orElseThrow(() ->
                new CustomException(ErrorType.NOT_FOUND_SEAT));

        // 경로에 있는 아이디로 찾은 세션과 좌석이 속한 세션이 같지 않을 때(해당 세션의 좌석이 아닐 때) 예외
        if(!findSession.equals(findSeat.getSession())) {
            throw new CustomException(ErrorType.NOT_FOUND_SEAT);
        }

        seatRepository.delete(findSeat);

    }

    /**
     * UserRole 검증
     * @param role
     */
    private void checkUserRole(UserRole role) {
        if(!role.equals(UserRole.ADMIN)) {
            throw new CustomException(ErrorType.NOT_AVAILABLE_PERMISSION);
        }
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
