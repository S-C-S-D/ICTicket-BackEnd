package com.sparta.icticket.admin.service;

import com.sparta.icticket.admin.controller.sales.dto.SalesAddRequestDto;
import com.sparta.icticket.admin.controller.sales.dto.SalesUpdateRequestDto;
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

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdminSalesService {

    private final SalesRepository salesRepository;
    private final PerformanceRepository performanceRepository;

    /**
     * 할인 등록
     * @param performanceId
     * @param requestDto
     */
    public void addSales(Long performanceId, SalesAddRequestDto requestDto) {
        Performance findPerformance = findPerformanceById(performanceId);

        checkDate(requestDto.getStartAt(), requestDto.getStartAt());

        Sales saveSales = new Sales(findPerformance, requestDto);

        salesRepository.save(saveSales);
    }

    /**
     * 할인 수정
     * @param performanceId
     * @param salesId
     * @param requestDto
     */
    @Transactional
    public void updateSales(Long performanceId, Long salesId, SalesUpdateRequestDto requestDto) {
        Performance findPerformance = findPerformanceById(performanceId);
        Sales findSales = checkPerformanceAndSales(salesId, findPerformance);
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
        Performance findPerformance = findPerformanceById(performanceId);

        Sales findSales = checkPerformanceAndSales(salesId, findPerformance);

        salesRepository.delete(findSales);
    }

    /**
     * 할인 검증
     * @param salesId
     * @param performance
     * @return
     */
    private Sales checkPerformanceAndSales(Long salesId, Performance performance) {
        return salesRepository.findByIdAndPerformance(salesId, performance).orElseThrow(() ->
                new CustomException(ErrorType.INVALID_ACCESS));
    }

    /**
     * 공연 검증
     * @param performanceId
     * @return
     */
    private Performance findPerformanceById(Long performanceId) {
        return performanceRepository.findById(performanceId).orElseThrow(() ->
                new CustomException(ErrorType.NOT_FOUND_PERFORMANCE));
    }

    /**
     * 할인 날짜 검증
     * @param endAt
     * @param startAt
     */
    private void checkDate(LocalDateTime endAt, LocalDateTime startAt) {
        if(endAt.isAfter(startAt) || startAt.isBefore(LocalDateTime.now())) {
            throw new CustomException(ErrorType.WRONG_DATE_FORMAT);
        }
    }
}
