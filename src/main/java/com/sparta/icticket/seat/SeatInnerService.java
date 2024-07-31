//package com.sparta.icticket.seat;
//
//import com.sparta.icticket.common.enums.ErrorType;
//import com.sparta.icticket.common.exception.CustomException;
//import com.sparta.icticket.performance.Performance;
//import com.sparta.icticket.sales.Sales;
//import com.sparta.icticket.seat.dto.SeatReservedRequestDto;
//import com.sparta.icticket.seat.dto.SeatReservedResponseDto;
//import com.sparta.icticket.session.Session;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.ArrayList;
//import java.util.List;
//
//@Service
//@RequiredArgsConstructor
//public class SeatInnerService {
//    private final SeatRepository seatRepository;
//
//    @Transactional
//    public SeatReservedResponseDto reserveSeat(SeatReservedRequestDto requestDto, Session session, Performance performance, Sales sales) {
//        List<Long> seatIdList = requestDto.getSeatIdList();
//        List<String> seatNumberList = new ArrayList<>();
//        Integer totalPrice = 0;
//        Integer discountRate = 0;
//
//
//        List<Seat> seatList = seatRepository.findSeatsByIdList(seatIdList);
//
//        if(seatList.size() < seatIdList.size()) {
//            throw new CustomException(ErrorType.ALREADY_RESERVED_SEAT);
//        }
//
//        for(Seat seat : seatList) {
//            seat.updateIsReserved();
//            seatNumberList.add(seat.getSeatNumber());
//            totalPrice += seat.getPrice();
//        }
//
//        // 세션에 해당하는 공연의 할인 정보 취득
//
//        // 할인하는 공연이면 할인율을 받아옴
//        if(sales != null) {
//            discountRate = sales.getDiscountRate();
//        }
//
//        return new SeatReservedResponseDto(performance, session, seatNumberList, totalPrice, discountRate);
//    }
//}
