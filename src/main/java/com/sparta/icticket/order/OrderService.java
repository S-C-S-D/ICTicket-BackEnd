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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final TicketRepository ticketRepository;
    private final SessionRepository sessionRepository;
    private final SalesRepository salesRepository;

    /**
     * 결제 완료
     * @param sessionId
     * @param requestDto
     * @param loginUser
     */
    @Transactional
    public OrderCreateResponseDto createOrder(Long sessionId, OrderCreateRequestDto requestDto, User loginUser) {
        //0. 결제하기 버튼을 눌렀을 때, 실행되는 로직입니다.
        //0. 현재 유저는 좌석 선택 완료 버튼을 누르고, 결제하기 페이지에 있는 상황입니다.
        //   버튼에는 2가지가 있고, 좌석 선택 완료 버튼 -> 결제 완료 버튼 순으로 진행이 됩니다.
        //    - 좌석 선택 완료 버튼
        //    - 결제 완료 버튼

        // 1.결제하기 버튼을 누르기 전에 좌석 선택 완료 버튼을 눌렀을 때, 버튼 누른 시간을 프론트에 저장했다가, 결제하기 버튼을 눌렀을 때 백앤드에 보내줍니다.
        LocalDateTime reservedAt = requestDto.getModifiedAt();

        // 2.프론트에서 보내준 좌석 선택완료 버튼을 클릭한 시간이 현재 시간과 10분 이상 차이가 나면, 결제가 되지 않고 예외처리로 넘어갑니다.
        if(reservedAt.isBefore(LocalDateTime.now().minusMinutes(10))) {
            throw new CustomException(ErrorType.TIME_OUT);
        }
        Session findSession = getSession(sessionId);

        List<String> findSeatNumberList = new ArrayList<>();
        Integer totalPrice = 0;
        Integer discountRate = 0;

        List<Seat> findSeatList = orderRepository.findSeatById(requestDto.getSeatIdList(), findSession);

        // 일부 좌석이 결제가 가능한 상태가 아니라면 예외처리
        if(findSeatList.size() != requestDto.getSeatIdList().size()) {
            throw new CustomException(ErrorType.NOT_FOUND_SEAT);
        }

        // seat_number 조회, 총 금액 계산, seat_status 변경
        for(Seat seat : findSeatList) {
            findSeatNumberList.add(seat.getSeatNumber());
            totalPrice += seat.getPrice();
            seat.updateSeatStatus(SeatStatus.PAYMENT_COMPLETED);
        }

        // 할인율 구하기
        Sales findSales = getSales(findSession.getPerformance());
        if(findSales !=null) {
            discountRate = findSales.getDiscountRate();
        }

        // 할인율을 적용한 총 금액 계산
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
     */
    public List<OrderListResponseDto> getOrders(Long userId, User loginUser) {

        loginUser.checkUser(userId);

        List<Order> orderList = orderRepository.findAllByUserOrderByCreatedAtDesc(loginUser);

        return orderList.stream().map(OrderListResponseDto :: new).toList();
    }

    /**
     * 예매 취소 기능
     * @param orderId
     * @param loginUser
     */
    @Transactional
    public void deleteOrder(Long orderId, User loginUser) {
        Order order = findOrder(orderId);

        order.checkUser(loginUser);


        order.checkCanceledOrder();

        //order 상태변경 후 seat 상태변경 후 ticket 삭제

        // (1) Order 상태변경
        order.updateOrderStatusToCancel();
        List<Ticket> tickets = findTicket(order);
        // (2) Seat 상태변경,ticket 삭제
        for (Ticket ticket : tickets) {
            ticket.getSeat().updateSeatStatus(SeatStatus.NOT_RESERVED);
            ticketRepository.delete(ticket);
        }

    }

    /**
     * 티켓 찾기
     * @param order
     * @description 예매 취소시 order에 연결되어있는 ticket을 찾을때 DB에서 가져온다
     */
    private List<Ticket> findTicket(Order order) {
        return ticketRepository.findByOrder(order)
                .orElseThrow(()->new CustomException(ErrorType.NOT_FOUND_TICKET));
    }


    /**
     * 주문 찾기
     * @param orderId
     * @description 예매 취소시 DB로부터 해당 order를 찾는다
     */
    private Order findOrder(Long orderId) {
        return orderRepository.findById(orderId).orElseThrow(() -> new CustomException(ErrorType.NOT_FOUND_ORDER));
    }

    /**
     * 예매 번호 생성
     * @description 고유문자인 "IC"와 랜덤 숫자열의 조합으로 예매 번호 생성
     */
    private String makeOrderNumber() {
        return "IC" + ((int)(Math.random() * 899999) + 100000);
    }


    /**
     * 세션 검증
     * @param sessionId
     * @description 해당 id를 가진 session 객체 조회
     */
    private Session getSession(Long sessionId) {
        return sessionRepository.findById(sessionId).orElseThrow(() ->
                new CustomException(ErrorType.NOT_FOUND_SESSION));
    }

    /**
     * 할인 검증
     * @param performance
     * @description 해당 performance를 가진 sales 객체 조회
     */
    private Sales getSales(Performance performance) {
        return salesRepository.findDiscountRateByPerformance(performance).orElse(null);
    }
}
