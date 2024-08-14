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

import java.time.LocalDate;
import java.time.LocalTime;
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

        when(sessionRepository.findById(any(Long.class))).thenReturn(Optional.of(session));
        doNothing().when(session).checkPerformance(any(Long.class));
        when(seatRepository.findAllBySessionId(1L)).thenReturn(list);

        when(seat.getId()).thenReturn(1L);
        when(seat.getSeatGrade()).thenReturn(SeatGrade.A);
        when(seat.getSeatStatus()).thenReturn(SeatStatus.PAYING);
        when(seat.getPrice()).thenReturn(10000);

        // when
        List<SeatInfoResponseDto> seatList = seatService.getSeats(1L, 1L);

        // then
        assertEquals(1L, seatList.get(0).getId());
        assertEquals(SeatGrade.A, seatList.get(0).getSeatGrade());
        assertEquals(SeatStatus.PAYING, seatList.get(0).getSeatStatus());
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
        when(session.getPerformance()).thenReturn(performance);

        Sales sales = Mockito.mock(Sales.class);

        when(sessionRepository.findById(any(Long.class))).thenReturn(Optional.of(session));
        when(seatRepository.findSeatsByIdList(seatIds)).thenReturn(seatList);
        doNothing().when(seat).checkSession(any(Long.class));
        doNothing().when(seat).updateSeatStatusToPaying(user);
        when(salesRepository.findByPerformance(performance)).thenReturn(Optional.of(sales));


        when(performance.getTitle()).thenReturn("title");
        when(performance.getImageUrl()).thenReturn("abcde");

        when(session.getSessionDate()).thenReturn(LocalDate.now());
        when(session.getSessionTime()).thenReturn(LocalTime.of(12, 30, 0));
        when(session.getSessionName()).thenReturn("A회차");

        when(sales.getDiscountRate()).thenReturn(10);

        List<String> seatNumberList = new ArrayList<>();
        seatNumberList.add("1");

        // when
        SeatReservedResponseDto responseDto = seatService.reserveSeat(1L, requestDto, user);

        // then
        assertEquals("title", responseDto.getTitle());
        assertEquals("abcde", responseDto.getImageUrl());
        assertEquals(LocalDate.now(), responseDto.getSessionDate());
        assertEquals(LocalTime.of(12, 30, 0), responseDto.getSessionTime());
        assertEquals("A회차", responseDto.getSessionName());
        assertEquals(seatNumberList, responseDto.getSeatNumberList());
        assertEquals(9000, responseDto.getTotalPrice());
        assertEquals(10, responseDto.getDiscountRate());
    }
}