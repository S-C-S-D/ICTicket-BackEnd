package com.sparta.icticket.performance;

import com.sparta.icticket.common.enums.ErrorType;
import com.sparta.icticket.common.exception.CustomException;
import com.sparta.icticket.performance.dto.PerformanceDetailResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PerformanceService {
    private final PerformanceRepository performanceRepository;

    @Transactional
    public PerformanceDetailResponseDto getPerformance(Long performanceId) {
        Performance performance = performanceRepository.findById(performanceId).orElseThrow(
                () -> new CustomException(ErrorType.NOT_FOUND_PERFORMANCE));

        performance.addViewCount();

        return new PerformanceDetailResponseDto(performance);
    }


}
