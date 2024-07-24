package com.sparta.icticket.performance;

import com.sparta.icticket.common.enums.ErrorType;
import com.sparta.icticket.common.enums.GenreType;
import com.sparta.icticket.common.exception.CustomException;
import com.sparta.icticket.performance.dto.PerformanceDetailResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


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


    public List<PerformanceDetailResponseDto> getGenreRankPerformances(GenreType genre, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        List<Performance> genreRankPerformances = performanceRepository.getGenreRankPerformances(genre, pageable);

        return genreRankPerformances.stream().map(PerformanceDetailResponseDto::new).toList();
    }

    @Transactional
    public List<PerformanceDetailResponseDto> getTodayOpenPerformances(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        List<Performance> performanceList = performanceRepository.getTodayOpenPerformances(pageable);

        return performanceList.stream().map(PerformanceDetailResponseDto::new).toList();
    }
}
