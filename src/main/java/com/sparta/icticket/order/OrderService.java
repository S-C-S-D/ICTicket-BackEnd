package com.sparta.icticket.order;

import com.sparta.icticket.common.enums.ErrorType;
import com.sparta.icticket.common.exception.CustomException;
import com.sparta.icticket.order.dto.OrderCreateRequestDto;
import com.sparta.icticket.order.dto.OrderCreateResponseDto;
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

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final SessionRepository sessionRepository;
    private final SalesRepository salesRepository;
    private final TicketRepository ticketRepository;

    /**
     * 결제 완료 기능
     * @param sessionId
     * @param requestDto
     * @param loginUser
     * @return
     */
    public OrderCreateResponseDto createOrder(Long sessionId, OrderCreateRequestDto requestDto, User loginUser) {
        // 세션 찾기
        Session findSession = findSessionById(sessionId);

        // 좌석 번호 구하기
        List<String> findSeatNumberList = orderRepository.findSeatNumberById(requestDto.getSeatIdList());

        // 좌석 총 금액 구하기
        Integer totalPrice = orderRepository.sumTotalPrice(requestDto.getSeatIdList());

        // 할인율 구하기
        Integer discountRate = 0;
        Sales findSales = findSales(findSession.getPerformance());
        if(findSales != null) {
            discountRate = findSales.getDiscountRate();
        }

        // 예매 번호 생성(6자리)
        String orderNumber = "IC" + ((int)(Math.random() * 899999) + 100000);

        // order 생성
        Order saveOrder = new Order(loginUser, findSession, orderNumber, requestDto.getSeatIdList().size(), totalPrice);
        orderRepository.save(saveOrder);

        // Ticket 생성
        List<Seat> findSeatList = orderRepository.findSeatById(requestDto.getSeatIdList());

        for(Seat seat : findSeatList) {
            Ticket saveTicket = new Ticket(saveOrder, seat, seat.getPrice());
            ticketRepository.save(saveTicket);
        }

        return new OrderCreateResponseDto(saveOrder, findSeatNumberList, discountRate, orderNumber);
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
