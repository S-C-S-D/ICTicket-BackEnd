package com.sparta.icticket.performance;

import com.querydsl.core.Tuple;
import com.sparta.icticket.common.enums.GenreType;

import org.springframework.data.domain.Pageable;
import java.util.List;

public interface PerformanceRepositoryQuery {
    List<Performance> getGenreRankPerformances(GenreType genreType, Pageable pageable);

    List<Performance> getTodayOpenPerformances(Pageable pageable);

    List<Performance> getDiscountPerformances(GenreType genreType, Pageable pageable);

    List<Performance> getWillBeOpenedPerformances(GenreType genreType, Pageable pageable);

    List<Performance> getRankAllPerformances(Pageable pageable);

    List<Performance> getRecommendPerformances(Pageable pageable);
}

