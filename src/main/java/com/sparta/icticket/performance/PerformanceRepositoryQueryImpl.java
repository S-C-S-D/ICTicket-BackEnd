package com.sparta.icticket.performance;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.icticket.common.enums.GenreType;
import com.sparta.icticket.like.QLike;
import com.sparta.icticket.sales.QSales;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class PerformanceRepositoryQueryImpl implements PerformanceRepositoryQuery {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Performance> getGenreRankPerformances(GenreType genreType, Pageable pageable) {
        QPerformance qPerformance = QPerformance.performance;
        QLike qLike = QLike.like;

        return jpaQueryFactory // join on id=id
                .select(qPerformance)
                .from(qPerformance)
                .leftJoin(qLike).on(qPerformance.id.eq(qLike.performance.id))
                .where(qPerformance.genreType.eq(genreType))
                .groupBy(qPerformance.id)
                .orderBy(qLike.id.count().add(qPerformance.viewCount).desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }

    @Override
    public List<Performance> getTodayOpenPerformances(Pageable pageable) {
        QPerformance qPerformance = QPerformance.performance;
        LocalDate now = LocalDate.now();
        return jpaQueryFactory
                .select(qPerformance)
                .from(qPerformance)
                .where(
                        qPerformance.openAt.year().eq(now.getYear())
                                .and(qPerformance.openAt.month().eq(now.getMonth().getValue()))
                                .and(qPerformance.openAt.dayOfMonth().eq(now.getDayOfMonth())))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }

    @Override
    public List<Performance> getDiscountPerformances(GenreType genreType, Pageable pageable) {
        QPerformance qPerformance = QPerformance.performance;
        QSales qSales = QSales.sales;

        return jpaQueryFactory
                .select(qPerformance)
                .from(qPerformance)
                .leftJoin(qSales).on(qPerformance.id.eq(qSales.performance.id))
                .where(qPerformance.genreType.eq(genreType).and(qSales.id.isNotNull()))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }

    @Override
    public List<Performance> getWillBeOpenedPerformances(GenreType genreType, Pageable pageable) {
        QPerformance qPerformance = QPerformance.performance;
        LocalDateTime now = LocalDateTime.now();

        return jpaQueryFactory
                .select(qPerformance)
                .from(qPerformance)
                .where(qPerformance.genreType.eq(genreType)
                        .and(qPerformance.openAt.after(now)))
                .orderBy(qPerformance.openAt.asc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }

    @Override
    public List<Performance> getRankAllPerformances(Pageable pageable) {
        QPerformance qPerformance = QPerformance.performance;
        QLike qLike = QLike.like;

        return jpaQueryFactory
                .select(qPerformance)
                .from(qPerformance)
                .leftJoin(qLike).on(qPerformance.id.eq(qLike.performance.id))
                .groupBy(qPerformance.id)
                .orderBy(qLike.id.count().add(qPerformance.viewCount).desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }

    @Override
    public List<Performance> getRecommendPerformances(Pageable pageable) {
        QPerformance qPerformance = QPerformance.performance;
        QLike qLike = QLike.like;

        GenreType[] values = GenreType.values();

        List<Performance> result = new ArrayList<>();
        for (GenreType value : values) {
            List<Performance> fetch = jpaQueryFactory
                    .select(qPerformance)
                    .from(qPerformance)
                    .leftJoin(qLike).on(qPerformance.id.eq(qLike.performance.id))
                    .where(qPerformance.genreType.in(value))
                    .groupBy(qPerformance.id)
                    .orderBy(qLike.id.count().add(qPerformance.viewCount).desc())
                    .limit(2)
                    .fetch();
            result.addAll(fetch);
        }

        List<Performance> paged = new ArrayList<>();
        long offset = pageable.getOffset();
        int pageSize = pageable.getPageSize();
        long endIndex = Math.min(result.size(), pageSize + (offset * pageSize));
        // 0~4, 4~8 , 8~12, 12~16 ...
        for (long i = offset; i < endIndex; i++) {
            paged.add(result.get((int) i));
        }

        return paged;
    }
}
