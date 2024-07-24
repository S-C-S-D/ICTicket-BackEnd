package com.sparta.icticket.performance;

import com.sparta.icticket.common.enums.GenreType;

import org.springframework.data.domain.Pageable;
import java.util.List;

public interface PerformanceRepositoryQuery {
    List<Performance> getGenreRankPerformances(GenreType genreType, Pageable pageable);

    List<Performance> getTodayOpenPerformances(Pageable pageable);
}

