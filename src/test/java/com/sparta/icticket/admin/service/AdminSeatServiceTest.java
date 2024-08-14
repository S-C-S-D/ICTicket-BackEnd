package com.sparta.icticket.admin.service;

import com.sparta.icticket.common.enums.ErrorType;
import com.sparta.icticket.common.enums.SeatGrade;
import com.sparta.icticket.common.exception.CustomException;
import com.sparta.icticket.seat.Seat;
import com.sparta.icticket.seat.SeatRepository;
import com.sparta.icticket.seat.dto.SeatCreateRequestDto;
import com.sparta.icticket.session.Session;
import com.sparta.icticket.session.SessionRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@Transactional
@Rollback
class AdminSeatServiceTest {

    @Mock
    private SeatRepository seatRepository;

    @Mock
    private SessionRepository sessionRepository;

    @InjectMocks
    private AdminSeatService adminSeatService;

    @Test
    public void createSeat() {

        // given
        SeatCreateRequestDto mock = Mockito.mock(SeatCreateRequestDto.class);
        when(mock.getPrice()).thenReturn(10000);
        when(mock.getSeatNumber()).thenReturn("1");
        when(mock.getSeatGrade()).thenReturn(SeatGrade.A);
        Session session = new Session();

        when(sessionRepository.findById(any(Long.class))).thenReturn(Optional.of(session));
        when(seatRepository.existsBySessionAndSeatGradeAndSeatNumber(session, mock.getSeatGrade(), mock.getSeatNumber())).thenReturn(true);

        // when-then
        CustomException exception = assertThrows(CustomException.class, () -> {
            adminSeatService.createSeat(1L, mock);
        });

        assertEquals(ErrorType.ALREADY_EXISTS_SEAT, exception.getErrorType());
    }

    @Test
    public void deleteSeat() {
        // given
        Session session = new Session();
        SeatCreateRequestDto mock = Mockito.mock(SeatCreateRequestDto.class);
        when(mock.getPrice()).thenReturn(10000);
        when(mock.getSeatNumber()).thenReturn("1");
        when(mock.getSeatGrade()).thenReturn(SeatGrade.A);
        Seat seat = new Seat(session, mock);

        when(sessionRepository.findById(any(Long.class))).thenReturn(Optional.of(session));
        when(seatRepository.findByIdAndSession(1L, session)).thenReturn(Optional.of(seat));

        // when
        adminSeatService.deleteSeat(1L, 1L);

        // then
        verify(seatRepository, times(1)).delete(seat);
    }
}