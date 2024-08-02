package com.sparta.icticket.performance;

import com.sparta.icticket.common.enums.ErrorType;
import com.sparta.icticket.common.enums.GenreType;
import com.sparta.icticket.common.exception.CustomException;
import com.sparta.icticket.performance.dto.DiscountPerformanceResponseDto;
import com.sparta.icticket.performance.dto.PerformanceDetailResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
@Slf4j
public class PerformanceService {
    private final PerformanceRepository performanceRepository;

    /**
     * 단일 공연 조회
     * @param performanceId
     * @return
     */
    public PerformanceDetailResponseDto getPerformanceWithRetries(Long performanceId) {
        int retryCount = 0;
        boolean flag = false;
        Performance performance = null;

        while(retryCount < 10 && !flag) {
            try {
                performance = getPerformance(performanceId);
                flag = true;

            } catch (ObjectOptimisticLockingFailureException e) {
                retryCount++;
            }
        }

        return new PerformanceDetailResponseDto(performance);
    }

    /**
     * ViewCount를 올리는 메서드
     * @param performanceId
     * @return
     */
    public Performance getPerformance(Long performanceId) {
        Performance performance = performanceRepository.getByIdCustom(performanceId).orElseThrow(() ->
                new CustomException(ErrorType.NOT_FOUND_PERFORMANCE));

        performance.addViewCount();

        return performanceRepository.save(performance);
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
    public List<DiscountPerformanceResponseDto> getDiscountPerformances(GenreType genre, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        List<DiscountPerformanceResponseDto> discountPerformances = performanceRepository.getDiscountPerformances(genre, pageable);

        return discountPerformances;
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


    /**
     * 추천 공연 조회 (장르별로 1위, 2위 티켓들)
     * @param page
     * @param size
     * @return
     */
    public List<PerformanceDetailResponseDto> getRecommendPerformances(int page, int size){
        Pageable pageable = PageRequest.of(page, size);
        List<Performance> performances = performanceRepository.getRecommendPerformances(pageable);

        return performances.stream().map(PerformanceDetailResponseDto::new).toList();
    }
}
