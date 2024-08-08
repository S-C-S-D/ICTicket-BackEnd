package com.sparta.icticket.admin.service;

import com.sparta.icticket.sales.dto.SalesAddRequestDto;
import com.sparta.icticket.sales.dto.SalesUpdateRequestDto;
import com.sparta.icticket.common.enums.ErrorType;
import com.sparta.icticket.common.exception.CustomException;
import com.sparta.icticket.performance.Performance;
import com.sparta.icticket.performance.PerformanceRepository;
import com.sparta.icticket.sales.Sales;
import com.sparta.icticket.sales.SalesRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdminSalesService {

    private final SalesRepository salesRepository;
    private final PerformanceRepository performanceRepository;

    /**
     * 할인 적용
     * @param performanceId
     * @param requestDto
     */
    public void addSales(Long performanceId, SalesAddRequestDto requestDto) {
        Performance findPerformance = getPerformance(performanceId);

        if(salesRepository.existsByPerformance(findPerformance)) {
            throw new CustomException(ErrorType.ALREADY_EXISTS_SALES);
        }

        checkDate(requestDto.getStartAt(), requestDto.getStartAt());

        Sales saveSales = new Sales(findPerformance, requestDto);

        salesRepository.save(saveSales);
    }

    /**
     * 할인 적용 수정
     * @param performanceId
     * @param salesId
     * @param requestDto
     */
    @Transactional
    public void updateSales(Long performanceId, Long salesId, SalesUpdateRequestDto requestDto) {
        Performance findPerformance = getPerformance(performanceId);
        Sales findSales = getSales(salesId, findPerformance);
        checkDate(requestDto.getStartAt(), requestDto.getStartAt());

        findSales.updateSales(requestDto);

    }

    /**
     * 할인 삭제
     * @param performanceId
     * @param salesId
     */
    @Transactional
    public void deleteSales(Long performanceId, Long salesId) {
        Performance findPerformance = getPerformance(performanceId);

        Sales findSales = getSales(salesId, findPerformance);

        salesRepository.delete(findSales);
    }

    /**
     * 할인 검증
     * @param salesId
     * @param performance
     * @description 해당 id와 performance를 가진 sales 객체 조회
     */
    private Sales getSales(Long salesId, Performance performance) {
        return salesRepository.findByIdAndPerformance(salesId, performance).orElseThrow(() ->
                new CustomException(ErrorType.NOT_FOUND_SALES));
    }

    /**
     * 공연 조회
     * @param performanceId
     * @description 해당 id를 가진 performance 객체 조회
     */
    private Performance getPerformance(Long performanceId) {
        return performanceRepository.findById(performanceId).orElseThrow(() ->
                new CustomException(ErrorType.NOT_FOUND_PERFORMANCE));
    }

    /**
     * 할인 날짜 검증
     * @param endAt
     * @param startAt
     * @description 할인 종료 날짜가 시작 날짜 이후는 아닌지, 할인 시작 날짜가 오늘 이전은 아닌지 검증
     */
    private void checkDate(Timestamp endAt, Timestamp startAt) {
        if(endAt.after(startAt) || startAt.before(Timestamp.valueOf(LocalDateTime.now()))) {
            throw new CustomException(ErrorType.WRONG_DATE_FORMAT);
        }
    }
}
