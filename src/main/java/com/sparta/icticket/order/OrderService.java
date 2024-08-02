package com.sparta.icticket.order;

import com.sparta.icticket.common.enums.ErrorType;
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
import com.sparta.icticket.session.Session;
import com.sparta.icticket.session.SessionRepository;
import com.sparta.icticket.ticket.Ticket;
import com.sparta.icticket.ticket.TicketRepository;
import com.sparta.icticket.user.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final TicketRepository ticketRepository;
    private final SessionRepository sessionRepository;
    private final SalesRepository salesRepository;

    /**
     * 결제 완료 기능
     * @param sessionId
     * @param requestDto
     * @param loginUser
     * @return
     */
    @Transactional
    public OrderCreateResponseDto createOrder(Long sessionId, OrderCreateRequestDto requestDto, User loginUser) {

        LocalDateTime reservedAt = requestDto.getModifiedAt();

        if(reservedAt.isBefore(LocalDateTime.now().minusMinutes(10))) {
            throw new CustomException(ErrorType.TIME_OUT);
        }

        // 세션 찾기
        Session findSession = findSessionById(sessionId);

        List<String> findSeatNumberList = new ArrayList<>();
        Integer totalPrice = 0;
        Integer discountRate = 0;

        List<Seat> findSeatList = orderRepository.findSeatById(requestDto.getSeatIdList(), findSession);

        if(findSeatList.size() != requestDto.getSeatIdList().size()) {
            throw new CustomException(ErrorType.NOT_FOUND_SEAT);
        }

        for(Seat seat : findSeatList) {
            findSeatNumberList.add(seat.getSeatNumber());
            totalPrice += seat.getPrice();
            seat.updateSeatStatus(SeatStatus.PAYMENT_COMPLETED);
        }

        // 할인율 구하기
        Sales findSales = findSales(findSession.getPerformance());
        if(findSales !=null) {
            discountRate = findSales.getDiscountRate();
        }

        totalPrice = (int) (totalPrice - (totalPrice * (discountRate / 100.0)));

        // order 생성
        Order saveOrder = new Order(loginUser, findSession, makeOrderNumber(), requestDto.getSeatIdList().size(), totalPrice);
        orderRepository.save(saveOrder);

        // Ticket 생성
        for(Seat seat : findSeatList) {
            Ticket saveTicket = new Ticket(saveOrder, seat, (int)(seat.getPrice() - (seat.getPrice() * (discountRate / 100.0))));
            ticketRepository.save(saveTicket);
        }

        return new OrderCreateResponseDto(saveOrder, findSeatNumberList, discountRate);
    }

    /**
     * 예매 내역 조회
     * @param userId
     * @param loginUser
     * @return
     */
    public List<OrderListResponseDto> getOrders(Long userId, User loginUser) {
        if(!Objects.equals(userId, loginUser.getId())) {
            throw new CustomException(ErrorType.CAN_NOT_LOAD_ORDER_HISTORY);
        }
        log.info("===============order에서 session,performance,venue entity 페치조인해야함================");
        List<Order> orderList = orderRepository.findAllByUserOrderByCreatedAtDesc(loginUser);
        log.info("===============session,performance,venue entity를 불러오면 안됨================");
        return orderList.stream().map(OrderListResponseDto :: new).toList();
    }

    /**
     * 예매 취소 기능
     * @param orderId
     * @param loginUser
     */
    @Transactional
    public void deleteOrder(Long orderId, User loginUser) {
        Order order = validateOrder(orderId);

        boolean isLoggedInUser = order.getUser().getId().equals(loginUser.getId());
        if (!isLoggedInUser) {
            throw new CustomException(ErrorType.NOT_YOUR_ORDER);
        }

        boolean isAlreadyCancled = order.getOrderStatus().equals(OrderStatus.CANCEL);
        if (isAlreadyCancled) {
            throw new CustomException(ErrorType.ALREADY_CANCELED_ORDER);
        }


        //order 상태변경 후 seat 상태변경 후 ticket 삭제

        // (1) Order 상태변경
        order.updateOrderStatusToCancel();
        List<Ticket> tickets = validateTicket(order);
        // (2) Seat 상태변경,ticket 삭제
        for (Ticket ticket : tickets) {
            ticket.getSeat().updateSeatStatus(SeatStatus.NOT_RESERVED);
            ticketRepository.delete(ticket);
        }

    }

    private List<Ticket> validateTicket(Order order) {
        return ticketRepository.findByOrder(order)
                .orElseThrow(()->new CustomException(ErrorType.NOT_FOUND_TICKET));
    }


    private Order validateOrder(Long orderId) {
        return orderRepository.findById(orderId).orElseThrow(() -> new CustomException(ErrorType.NOT_FOUND_ORDER));
    }

    /**
     * 예매 번호 생성
     * @return
     */
    private String makeOrderNumber() {
        return "IC" + ((int)(Math.random() * 899999) + 100000);
    }


    /**
     * 세션 검증
     * @param sessionId
     * @return
     */
    private Session findSessionById(Long sessionId) {
        return sessionRepository.findById(sessionId).orElseThrow(() ->
                new CustomException(ErrorType.NOT_FOUND_SESSION));
    }

    /**
     * 할인 검증
     * @param performance
     * @return
     */
    private Sales findSales(Performance performance) {
        return salesRepository.findByPerformance(performance).orElse(null);
    }
}
