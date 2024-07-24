package com.sparta.icticket.seat;

import com.sparta.icticket.common.enums.ErrorType;
import com.sparta.icticket.common.enums.SeatStatus;
import com.sparta.icticket.common.exception.CustomException;
import com.sparta.icticket.performance.Performance;
import com.sparta.icticket.performance.PerformanceRepository;
import com.sparta.icticket.sales.Sales;
import com.sparta.icticket.sales.SalesRepository;
import com.sparta.icticket.seat.dto.SeatCountResponseDto;
import com.sparta.icticket.seat.dto.SeatReservedRequestDto;
import com.sparta.icticket.seat.dto.SeatReservedResponseDto;
import com.sparta.icticket.session.Session;
import com.sparta.icticket.session.SessionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class SeatService {

    private final SeatRepository seatRepository;
    private final PerformanceRepository performanceRepository;
    private final SessionRepository sessionRepository;
    private final SalesRepository salesRepository;
    private final SeatRepositoryQuery seatRepositoryQuery;

    /**
     * 세션별 잔여 좌석 조회
     * @param performanceId
     * @param sessionId
     * @return
     */
    public SeatCountResponseDto getSeatCount(Long performanceId, Long sessionId) {
        Performance findPerformance = checkPerformance(performanceId);
        Session findSession = checkSession(sessionId);

        // 세션이 해당 공연의 세션이 아닐 때 예외
        if(!findPerformance.equals(findSession.getPerformance())) {
            throw new CustomException(ErrorType.NOT_FOUND_SESSION);
        }

        Integer totalSeatCount = seatRepository.countBySession(findSession);
        Integer restSeatCount = seatRepository.countBySessionAndSeatStatus(findSession, SeatStatus.NOT_RESERVED);

        return new SeatCountResponseDto(totalSeatCount, restSeatCount);
    }

    /**
     * 좌석 선택 완료
     * @param sessionId
     * @param requestDto
     * @return
     */
    @Transactional
    public SeatReservedResponseDto reserveSeat(Long sessionId, SeatReservedRequestDto requestDto) {
        Session findSession = checkSession(sessionId);
        List<Long> seatIdList = requestDto.getSeatIdList();
        List<String> seatNumberList = new ArrayList<>();
        Integer totalPrice = 0;
        Integer discountRate = 0;


        List<Seat> seatList = seatRepositoryQuery.findSeatsByIdList(seatIdList);

        if(seatList.size() < seatIdList.size()) {
            throw new CustomException(ErrorType.ALREADY_RESERVED_SEAT);
        }

        for(Seat seat : seatList) {
            seat.updateIsReserved();
            seatNumberList.add(seat.getSeatNumber());
            totalPrice += seat.getPrice();
        }

        // 세션에 해당하는 공연의 할인 정보 취득
        Performance findPerformance = findSession.getPerformance();
        Sales sales  = salesRepository.findByPerformance(findPerformance).orElse(null);

        // 할인하는 공연이면 할인율을 받아옴
        if(sales != null) {
            discountRate = sales.getDiscountRate();
        }


        return new SeatReservedResponseDto(findPerformance, findSession, seatNumberList, totalPrice, discountRate);
    }

    /**
     * 공연 검증
     * @param performanceId
     * @return
     */
    private Performance checkPerformance(Long performanceId) {
        return performanceRepository.findById(performanceId).orElseThrow(() ->
                new CustomException(ErrorType.NOT_FOUND_PERFORMANCE));
    }

    /**
     * 세션 검증
     * @param sessionId
     * @return
     */
    private Session checkSession(Long sessionId) {
        return sessionRepository.findById(sessionId).orElseThrow(() ->
                new CustomException(ErrorType.NOT_FOUND_SESSION));
    }
}