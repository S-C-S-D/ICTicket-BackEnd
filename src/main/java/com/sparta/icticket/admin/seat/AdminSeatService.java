package com.sparta.icticket.admin.seat;

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

        Session findSession = sessionRepository.findById(sessionId).orElseThrow(() ->
                new CustomException(ErrorType.NOT_FOUND_SESSION));

        Seat saveSeat = new Seat(findSession, requestDto);

        seatRepository.save(saveSeat);
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
}
