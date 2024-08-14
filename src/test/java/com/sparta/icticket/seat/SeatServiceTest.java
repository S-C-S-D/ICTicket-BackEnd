package com.sparta.icticket.seat;

import com.sparta.icticket.common.enums.SeatGrade;
import com.sparta.icticket.common.enums.SeatStatus;
import com.sparta.icticket.performance.Performance;
import com.sparta.icticket.sales.Sales;
import com.sparta.icticket.sales.SalesRepository;
import com.sparta.icticket.seat.dto.SeatCountResponseDto;
import com.sparta.icticket.seat.dto.SeatInfoResponseDto;
import com.sparta.icticket.seat.dto.SeatReservedRequestDto;
import com.sparta.icticket.seat.dto.SeatReservedResponseDto;
import com.sparta.icticket.session.Session;
import com.sparta.icticket.session.SessionRepository;
import com.sparta.icticket.user.User;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@Transactional
@Rollback
class SeatServiceTest {

    @Mock
    private SeatRepository seatRepository;

    @Mock
    private SessionRepository sessionRepository;

    @Mock
    private SalesRepository salesRepository;

    @InjectMocks
    private SeatService seatService;

    @Test
    void getSeatCount() {

        // given
        Session session = Mockito.mock(Session.class);

        when(sessionRepository.findById(any(Long.class))).thenReturn(Optional.of(session));
        doNothing().when(session).checkPerformance(any(Long.class));

        when(seatRepository.countBySession(session)).thenReturn(2);
        when(seatRepository.countBySessionAndSeatStatus(session, SeatStatus.NOT_RESERVED)).thenReturn(1);

        // when
        SeatCountResponseDto responseDto = seatService.getSeatCount(1L, 1L);

        // then
        assertEquals(2, responseDto.getTotalSeatCount());
        assertEquals(1, responseDto.getRestSeatCount());
    }

    @Test
    void getSeats() {

        // given
        Session session = Mockito.mock(Session.class);
        Seat seat = Mockito.mock(Seat.class);
        List<Seat> list = new ArrayList<>();
        list.add(seat);

        when(seat.getSeatGrade()).thenReturn(SeatGrade.A);
        when(seat.getPrice()).thenReturn(10000);
        when(sessionRepository.findById(any(Long.class))).thenReturn(Optional.of(session));
        doNothing().when(session).checkPerformance(any(Long.class));

        when(seatRepository.findAllBySessionId(1L)).thenReturn(list);

        // when
        List<SeatInfoResponseDto> seatList = seatService.getSeats(1L, 1L);

        // then
        assertEquals(SeatGrade.A, seatList.get(0).getSeatGrade());
        assertEquals(10000, seatList.get(0).getPrice());
    }

    @Test
    void reserveSeat() {

        // given
        Session session = Mockito.mock(Session.class);
        List<Long> seatIds = new ArrayList<>();
        seatIds.add(1L);
        SeatReservedRequestDto requestDto = Mockito.mock(SeatReservedRequestDto.class);
        when(requestDto.getSeatIdList()).thenReturn(seatIds);

        Seat seat = Mockito.mock(Seat.class);
        List<Seat> seatList = new ArrayList<>();
        seatList.add(seat);
        when(seat.getPrice()).thenReturn(10000);
        when(seat.getSeatNumber()).thenReturn("1");
        when(seat.getSeatGrade()).thenReturn(SeatGrade.A);

        User user = new User();

        Performance performance = Mockito.mock(Performance.class);

        Sales sales = Mockito.mock(Sales.class);
        when(sales.getDiscountRate()).thenReturn(10);

        when(sessionRepository.findById(any(Long.class))).thenReturn(Optional.of(session));
        when(seatRepository.findSeatsByIdList(seatIds)).thenReturn(seatList);
        doNothing().when(seat).checkSession(any(Long.class));
        doNothing().when(seat).updateSeatStatusToPaying(user);
        when(session.getPerformance()).thenReturn(performance);
        when(salesRepository.findByPerformance(performance)).thenReturn(Optional.of(sales));

        // when
        SeatReservedResponseDto responseDto = seatService.reserveSeat(1L, requestDto, user);

        // then
        assertEquals(10, responseDto.getDiscountRate());
        assertEquals(10000, responseDto.getTotalPrice());
        assertEquals("1", responseDto.getSeatNumberList().get(0));
    }
}