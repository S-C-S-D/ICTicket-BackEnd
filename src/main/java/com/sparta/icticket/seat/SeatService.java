package com.sparta.icticket.seat;

import com.sparta.icticket.common.enums.ErrorType;
import com.sparta.icticket.common.enums.SeatStatus;
import com.sparta.icticket.common.exception.CustomException;
import com.sparta.icticket.common.config.DistributedLock;
import com.sparta.icticket.performance.Performance;
import com.sparta.icticket.performance.PerformanceRepository;
import com.sparta.icticket.sales.Sales;
import com.sparta.icticket.sales.SalesRepository;
import com.sparta.icticket.seat.dto.SeatCountResponseDto;
import com.sparta.icticket.seat.dto.SeatInfoResponseDto;
import com.sparta.icticket.seat.dto.SeatReservedRequestDto;
import com.sparta.icticket.seat.dto.SeatReservedResponseDto;
import com.sparta.icticket.session.Session;
import com.sparta.icticket.session.SessionRepository;
import com.sparta.icticket.user.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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


    /**
     * 세션별 잔여 좌석 조회
     * @param performanceId
     * @param sessionId
     */
    public SeatCountResponseDto getSeatCount(Long performanceId, Long sessionId) {
        Session findSession = getSession(sessionId);

        findSession.checkPerformance(performanceId);

        Integer totalSeatCount = seatRepository.countBySession(findSession);
        Integer restSeatCount = seatRepository.countBySessionAndSeatStatus(findSession, SeatStatus.NOT_RESERVED);

        return new SeatCountResponseDto(totalSeatCount, restSeatCount);
    }

    /**
     * 세션별 좌석 상세 조회
     * @param performanceId
     * @param sessionId
     */
    public List<SeatInfoResponseDto> getSeats(Long performanceId, Long sessionId) {
        Session findSession = getSession(sessionId);

        findSession.checkPerformance(performanceId);

        List<Seat> seatList = seatRepository.findAllBySessionId(sessionId);

        return seatList.stream().map(SeatInfoResponseDto::new).toList();
    }

    /**
     * 좌석 선택 완료
     * @param sessionId
     * @param requestDto
     */
    @DistributedLock(key = "seat")
    public SeatReservedResponseDto reserveSeat(Long sessionId, SeatReservedRequestDto requestDto, User loginUser) {

        Session findSession = getSession(sessionId);
        List<Long> seatIdList = requestDto.getSeatIdList();
        List<String> seatNumberList = new ArrayList<>();
        Integer totalPrice = 0;
        Integer discountRate = 0;

        List<Seat> seatList = seatRepository.findSeatsByIdList(seatIdList);

        if(seatList.size() < seatIdList.size()) {
            throw new CustomException(ErrorType.ALREADY_RESERVED_SEAT);
        }

        for(Seat seat : seatList) {
            seat.checkSession(sessionId);
            seat.updateSeatStatusToPaying(loginUser);
            seatNumberList.add(seat.getSeatNumber());
            totalPrice += seat.getPrice();
        }

        Performance findPerformance = findSession.getPerformance();
        Sales sales  = salesRepository.findByPerformance(findPerformance).orElse(null);

        if(sales != null) {
            discountRate = sales.getDiscountRate();
        }

        return new SeatReservedResponseDto(findPerformance, findSession, seatNumberList, totalPrice, discountRate);
    }

    /**
     * 세션 조회
     * @param sessionId
     * @description 해당 id를 가진 session 객체 조회
     */
    private Session getSession(Long sessionId) {
        return sessionRepository.findById(sessionId).orElseThrow(() ->
                new CustomException(ErrorType.NOT_FOUND_SESSION));
    }
}
