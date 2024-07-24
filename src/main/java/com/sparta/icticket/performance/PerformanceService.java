package com.sparta.icticket.performance;

import com.sparta.icticket.common.enums.ErrorType;
import com.sparta.icticket.common.enums.GenreType;
import com.sparta.icticket.common.exception.CustomException;
import com.sparta.icticket.performance.dto.PerformanceDetailResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@RequiredArgsConstructor
public class PerformanceService {
    private final PerformanceRepository performanceRepository;

    /**
     * 단일 공연 조회
     * @param performanceId
     * @return
     */
    @Transactional
    public PerformanceDetailResponseDto getPerformance(Long performanceId) {
        Performance performance = performanceRepository.findById(performanceId).orElseThrow(
                () -> new CustomException(ErrorType.NOT_FOUND_PERFORMANCE));

        performance.addViewCount();

        return new PerformanceDetailResponseDto(performance);
    }

    /**
     * 장르별 랭킹 공연 조회
     * @param genre
     * @param page
     * @param size
     * @return
     */
    public List<PerformanceDetailResponseDto> getGenreRankPerformances(GenreType genre, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        List<Performance> genreRankPerformances = performanceRepository.getGenreRankPerformances(genre, pageable);

        return genreRankPerformances.stream().map(PerformanceDetailResponseDto::new).toList();
    }

    /**
     * 오늘 오픈 공연 조회
     * @param page
     * @param size
     * @return
     */
    public List<PerformanceDetailResponseDto> getTodayOpenPerformances(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        List<Performance> performanceList = performanceRepository.getTodayOpenPerformances(pageable);

        return performanceList.stream().map(PerformanceDetailResponseDto::new).toList();
    }

    /**
     * 할인 중인 공연 조회
     * @param genre
     * @param page
     * @param size
     * @return
     */
    public List<PerformanceDetailResponseDto> getDiscountPerformances(GenreType genre, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        List<Performance> discountPerformances = performanceRepository.getDiscountPerformances(genre, pageable);

        return discountPerformances.stream().map(PerformanceDetailResponseDto::new).toList();
    }

    /**
     * 곧 오픈할 공연 조회
     * @param genre
     * @param page
     * @param size
     * @return
     */
    public List<PerformanceDetailResponseDto> getWillBeOpenedPerformances(GenreType genre, int page, int size) {
        Pageable pageable = PageRequest.of(page, size); // Pageable 정의 추가
        List<Performance> willBeOpenedPerformances = performanceRepository.getWillBeOpenedPerformances(genre, pageable); // 올바른 변수 사용
        return willBeOpenedPerformances.stream().map(PerformanceDetailResponseDto::new).toList(); // 올바른 변수 사용
    }

    /**
     * 전체 랭킹 조회
     * @param page
     * @param size
     * @return
     */
    public List<PerformanceDetailResponseDto> getRankAllPerformances(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        List<Performance> getRankAllPerformances = performanceRepository.getRankAllPerformances(pageable);
        return getRankAllPerformances.stream().map(PerformanceDetailResponseDto::new).toList();
    }
}
