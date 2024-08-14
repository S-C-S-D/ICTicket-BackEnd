package com.sparta.icticket.order;

import com.sparta.icticket.common.enums.ErrorType;
import com.sparta.icticket.common.enums.GenreType;
import com.sparta.icticket.common.enums.OrderStatus;
import com.sparta.icticket.common.enums.SeatStatus;
import com.sparta.icticket.common.exception.CustomException;
import com.sparta.icticket.order.dto.OrderCreateRequestDto;
import com.sparta.icticket.order.dto.OrderCreateResponseDto;
import com.sparta.icticket.order.dto.OrderListResponseDto;
import com.sparta.icticket.performance.Performance;
import com.sparta.icticket.sales.Sales;
import com.sparta.icticket.sales.SalesRepository;
import com.sparta.icticket.seat.Seat;
import com.sparta.icticket.seat.SeatRepository;
import com.sparta.icticket.seat.dto.SeatCreateRequestDto;
import com.sparta.icticket.session.Session;
import com.sparta.icticket.session.SessionRepository;
import com.sparta.icticket.ticket.Ticket;
import com.sparta.icticket.ticket.TicketRepository;
import com.sparta.icticket.user.User;
import com.sparta.icticket.venue.Venue;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@Transactional
@Rollback
class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private SessionRepository sessionRepository;

    @Mock
    private SalesRepository salesRepository;

    @Mock
    private TicketRepository ticketRepository;

    @InjectMocks
    private OrderService orderService;

    // createOrder가 올바른 responseDto를 반환하는지 확인
    @Test
    void createOrder() {

        // given
        Session session = Mockito.mock(Session.class);
        when(sessionRepository.findById(any(Long.class))).thenReturn(Optional.of(session));

        Seat seat = Mockito.mock(Seat.class);
        when(seat.getSeatSelectedAt()).thenReturn(LocalDateTime.now());
        when(seat.getPrice()).thenReturn(10000);
        when(seat.getSeatNumber()).thenReturn("1");
        List<Seat> seats = new ArrayList<>();
        seats.add(seat);

        OrderCreateRequestDto requestDto = Mockito.mock(OrderCreateRequestDto.class);
        List<Long> seatIds = new ArrayList<>();
        seatIds.add(1L);
        when(requestDto.getSeatIdList()).thenReturn(seatIds);
        when(orderRepository.findSeatById(seatIds, session)).thenReturn(seats);

        User user = Mockito.mock(User.class);
        doNothing().when(seat).checkUser(user);
        doNothing().when(seat).updateSeatStatus(SeatStatus.PAYMENT_COMPLETED);

        Sales sales = Mockito.mock(Sales.class);
        Performance performance = Mockito.mock(Performance.class);
        when(salesRepository.findByPerformance(performance)).thenReturn(Optional.of(sales));

        when(sales.getDiscountRate()).thenReturn(10);
        when(session.getPerformance()).thenReturn(performance);

        List<String> seatNumberList = new ArrayList<>();
        seatNumberList.add("1");

        when(performance.getTitle()).thenReturn("title");
        when(performance.getImageUrl()).thenReturn("abcde");
        when(session.getSessionDate()).thenReturn(LocalDate.now());
        when(session.getSessionName()).thenReturn("A회차");
        when(user.getAddress()).thenReturn("1번지");
        when(user.getPhoneNumber()).thenReturn("010-0000-0000");

        // when
        OrderCreateResponseDto responseDto = orderService.createOrder(1L, requestDto, user);

        // then
        assertEquals("title", responseDto.getTitle());
        assertEquals("abcde", responseDto.getImageUrl());
        assertEquals(LocalDate.now(), responseDto.getSessionDate());
        assertEquals("A회차", responseDto.getSessionName());
        assertEquals(seatNumberList, responseDto.getSeatNumberList());
        assertEquals(9000, responseDto.getTotalPrice());
        assertEquals(10, responseDto.getDiscountRate());
        assertEquals("1번지", responseDto.getAddress());
        assertEquals("010-0000-0000", responseDto.getPhoneNumber());
        assertEquals(LocalDate.now(), responseDto.getOrderDate());
    }

    // order, ticket의 save가 일어나는지 테스트
    @Test
    void createSeat_save() {
        // given
        Session session = Mockito.mock(Session.class);
        when(sessionRepository.findById(any(Long.class))).thenReturn(Optional.of(session));

        Seat seat = Mockito.mock(Seat.class);
        when(seat.getSeatSelectedAt()).thenReturn(LocalDateTime.now());
        when(seat.getPrice()).thenReturn(10000);
        when(seat.getSeatNumber()).thenReturn("1");
        List<Seat> seats = new ArrayList<>();
        seats.add(seat);

        OrderCreateRequestDto requestDto = Mockito.mock(OrderCreateRequestDto.class);
        List<Long> seatIds = new ArrayList<>();
        seatIds.add(1L);
        when(requestDto.getSeatIdList()).thenReturn(seatIds);
        when(orderRepository.findSeatById(seatIds, session)).thenReturn(seats);

        User user = Mockito.mock(User.class);
        doNothing().when(seat).checkUser(user);
        doNothing().when(seat).updateSeatStatus(SeatStatus.PAYMENT_COMPLETED);

        Sales sales = Mockito.mock(Sales.class);
        Performance performance = Mockito.mock(Performance.class);
        when(salesRepository.findByPerformance(performance)).thenReturn(Optional.of(sales));

        when(sales.getDiscountRate()).thenReturn(10);
        when(session.getPerformance()).thenReturn(performance);

        // when
        orderService.createOrder(1L, requestDto, user);

        // then
        verify(orderRepository, times(1)).save(any(Order.class));
        verify(ticketRepository, times(1)).save(any(Ticket.class));
    }

    // 일부 좌석이 결제할 수 없는 경우 예외 테스트
    @Test
    void creatSeat_NotFoundSeat() {

        // given
        Seat seat1 = Mockito.mock(Seat.class);
        List<Seat> seats = new ArrayList<>();
        seats.add(seat1);

        List<Long> seatIds = new ArrayList<>();
        seatIds.add(1L);
        seatIds.add(2L);

        OrderCreateRequestDto requestDto = Mockito.mock(OrderCreateRequestDto.class);
        when(requestDto.getSeatIdList()).thenReturn(seatIds);

        Session session = Mockito.mock(Session.class);
        when(sessionRepository.findById(any(Long.class))).thenReturn(Optional.of(session));
        when(orderRepository.findSeatById(seatIds, session)).thenReturn(seats);

        User user = Mockito.mock(User.class);

        // when-then
        CustomException exception = assertThrows(CustomException.class, () -> {
            orderService.createOrder(1L, requestDto, user);
        });

        assertEquals(ErrorType.NOT_FOUND_SEAT, exception.getErrorType());

    }

    // 결제 시간을 초과한 경우 테스트
    @Test
    void creatSeat_TimeOut() {

        // given
        List<Long> seatIds = new ArrayList<>();
        seatIds.add(1L);
        OrderCreateRequestDto requestDto = Mockito.mock(OrderCreateRequestDto.class);
        when(requestDto.getSeatIdList()).thenReturn(seatIds);

        Session session = Mockito.mock(Session.class);
        Seat seat = Mockito.mock(Seat.class);
        List<Seat> seats = new ArrayList<>();
        seats.add(seat);

        when(sessionRepository.findById(any(Long.class))).thenReturn(Optional.of(session));
        User user = Mockito.mock(User.class);
        doNothing().when(seat).checkUser(user);

        when(orderRepository.findSeatById(seatIds, session)).thenReturn(seats);

        when(seat.getSeatSelectedAt()).thenReturn(LocalDateTime.now().minusMinutes(12));

        // when-then
        CustomException exception = assertThrows(CustomException.class, () -> {
            orderService.createOrder(1L, requestDto, user);
        });

        assertEquals(ErrorType.TIME_OUT, exception.getErrorType());

    }

    // getOrders가 올바른 responseDto를 반환하는지 테스트
    @Test
    void getOrders() {

        // given
        User user = Mockito.mock(User.class);
        doNothing().when(user).checkUser(any(Long.class));

        Order order = Mockito.mock(Order.class);
        List<Order> orders = new ArrayList<>();
        orders.add(order);
        when(orderRepository.findAllByUserOrderByCreatedAtDesc(user)).thenReturn(orders);

        Session session = Mockito.mock(Session.class);
        Performance performance = Mockito.mock(Performance.class);
        Venue venue = Mockito.mock(Venue.class);

        when(order.getId()).thenReturn(1L);
        when(order.getOrderNumber()).thenReturn("IC123456");
        when(order.getTicketCount()).thenReturn(1);
        when(order.getOrderStatus()).thenReturn(OrderStatus.SUCCESS);
        when(order.getCreatedAt()).thenReturn(LocalDateTime.now());
        when(order.getSession()).thenReturn(session);

        when(session.getSessionDate()).thenReturn(LocalDate.now());
        when(session.getSessionTime()).thenReturn(LocalTime.of(12, 30, 0));
        when(session.getPerformance()).thenReturn(performance);

        when(performance.getId()).thenReturn(1L);
        when(performance.getGenreType()).thenReturn(GenreType.CLASSIC);
        when(performance.getImageUrl()).thenReturn("abcd");
        when(performance.getTitle()).thenReturn("title");
        when(performance.getStartAt()).thenReturn(LocalDate.now());
        when(performance.getEndAt()).thenReturn(LocalDate.now());
        when(performance.getVenue()).thenReturn(venue);

        when(venue.getLocation()).thenReturn("1번지");

        // when
        List<OrderListResponseDto> responseDto = orderService.getOrders(1L, user);

        // then
        assertEquals(1L, responseDto.get(0).getPerformanceId());
        assertEquals(1L, responseDto.get(0).getOrderId());
        assertEquals("abcd", responseDto.get(0).getImageUrl());
        assertEquals(LocalDate.now(), responseDto.get(0).getOrderDate());
        assertEquals(GenreType.CLASSIC, responseDto.get(0).getGenreType());
        assertEquals("title", responseDto.get(0).getTitle());
        assertEquals(LocalDate.now(), responseDto.get(0).getStartAt());
        assertEquals(LocalDate.now(), responseDto.get(0).getEndAt());
        assertEquals("1번지", responseDto.get(0).getAddress());
        assertEquals("IC123456", responseDto.get(0).getOrderNumber());
        assertEquals(LocalDate.now(), responseDto.get(0).getSessionDate());
        assertEquals(LocalTime.of(12, 30, 0), responseDto.get(0).getSessionTime());
        assertEquals(1, responseDto.get(0).getTicketCount());
        assertEquals(OrderStatus.SUCCESS, responseDto.get(0).getOrderStatus());
    }

    // 예매 취소 시, ticket의 delete가 수행되는지 테스트
    @Test
    void deleteOrder() {

        // given
        Order order = Mockito.mock(Order.class);
        when(orderRepository.findById(any(Long.class))).thenReturn(Optional.of(order));

        User user = Mockito.mock(User.class);
        doNothing().when(order).checkUser(user);
        doNothing().when(order).checkCanceledOrder();

        Ticket ticket = Mockito.mock(Ticket.class);
        List<Ticket> ticketList = new ArrayList<>();
        ticketList.add(ticket);
        when(ticketRepository.findByOrder(order)).thenReturn(Optional.of(ticketList));

        Seat seat = Mockito.mock(Seat.class);
        when(ticket.getSeat()).thenReturn(seat);
        doNothing().when(seat).updateSeatStatus(SeatStatus.NOT_RESERVED);

        // when
        orderService.deleteOrder(1L, user);

        verify(ticketRepository, times(1)).delete(ticket);
    }
}