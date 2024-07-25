package com.sparta.icticket.order;

import com.sparta.icticket.common.enums.ErrorType;
import com.sparta.icticket.common.exception.CustomException;
import com.sparta.icticket.order.dto.OrderCreateRequestDto;
import com.sparta.icticket.order.dto.OrderCreateResponseDto;
import com.sparta.icticket.performance.Performance;
import com.sparta.icticket.sales.Sales;
import com.sparta.icticket.sales.SalesRepository;
import com.sparta.icticket.session.Session;
import com.sparta.icticket.session.SessionRepository;
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

    public OrderCreateResponseDto createOrder(Long sessionId, OrderCreateRequestDto requestDto, User loginUser) {
        // 세션 찾기
        Session findSession = findSessionById(sessionId);

        // 좌석 번호 구하기
        List<String> findSeatList = orderRepository.findSeatNumberById(requestDto.getSeatIdList());

        // 좌석 총 금액 구하기
        Integer totalPrice = orderRepository.sumTotalPrice(requestDto.getSeatIdList());

        // 할인율 구하기
        Integer discountRate = 0;
        Sales findSales = findSales(findSession.getPerformance());
        if(findSales != null) {
            discountRate = findSales.getDiscountRate();
        }

        return new OrderCreateResponseDto(findSession, findSeatList, totalPrice, discountRate, loginUser);
    }

    private Session findSessionById(Long sessionId) {
        return sessionRepository.findById(sessionId).orElseThrow(() ->
                new CustomException(ErrorType.NOT_FOUND_SESSION));
    }

    private Sales findSales(Performance performance) {
        return salesRepository.findByPerformance(performance).orElse(null);
    }
}
