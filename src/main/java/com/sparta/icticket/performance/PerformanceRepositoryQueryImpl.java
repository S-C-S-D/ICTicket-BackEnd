package com.sparta.icticket.performance;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.icticket.common.enums.GenreType;
import com.sparta.icticket.like.QLike;
import com.sparta.icticket.performance.dto.DiscountPerformanceResponseDto;
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

    /**
     * 장르별 인기순 조회
     * @param genreType 장르
     * @param pageable 페이지네이션 정보
     * @description 해당 장르의 공연들을 인기순으로 조회
     */
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

    /**
     * 오늘 오픈 공연 조회
     * @param pageable 페이지네이션 정보
     * @description 오늘 오픈하는 공연들을 오픈 시간이 빠른 순으로 조회
     */
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
                .orderBy(qPerformance.openAt.asc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }

    /**
     * 할인 중인 공연 조회
     * @param genreType 장르
     * @param pageable 페이지네이션 정보
     * @description 해당 장르의 공연들 중 할인 중인 공연을 할인률이 높은 순으로 조회
     */
    @Override
    public List<DiscountPerformanceResponseDto> getDiscountPerformances(GenreType genreType, Pageable pageable) {
        QPerformance qPerformance = QPerformance.performance;
        QSales qSales = QSales.sales;
        LocalDateTime now = LocalDateTime.now();

        return jpaQueryFactory
                .select(Projections.constructor(DiscountPerformanceResponseDto.class, qPerformance, qSales))
                .from(qPerformance)
                .leftJoin(qSales).on(qPerformance.id.eq(qSales.performance.id))
                .where(
                        qPerformance.genreType.eq(genreType)
                                .and(qSales.id.isNotNull())
                                .and(qSales.endAt.after(now))
                                .and(qPerformance.endAt.after(now.toLocalDate())))
                .orderBy(qSales.discountRate.desc(), qSales.startAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }

    /**
     * 오픈 예정인 공연 조회
     * @param genreType 장르
     * @param pageable 페이지네이션 정보
     * @description 해당 장르의 공연 중 오늘 오픈되는 공연들을 오픈 시간이 빠른 순으로 조회
     */
    @Override
    public List<Performance> getWillBeOpenedPerformances(GenreType genreType, Pageable pageable) {
        QPerformance qPerformance = QPerformance.performance;
        LocalDateTime now = LocalDateTime.now();

        return jpaQueryFactory
                .select(qPerformance)
                .from(qPerformance)
                .where(qPerformance.genreType.eq(genreType)
                        .and(qPerformance.openAt.after(now))
                        .and(qPerformance.endAt.after(now.toLocalDate())))
                .orderBy(qPerformance.openAt.asc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }

    /**
     * 전체 공연 랭킹 조회
     * @param pageable 페이지네이션 정보
     * @description 전체 공연들을 인기순(좋아요+조회수)으로 조회
     */
    @Override
    public List<Performance> getRankAllPerformances(Pageable pageable) {
        QPerformance qPerformance = QPerformance.performance;
        QLike qLike = QLike.like;

        return jpaQueryFactory
                .select(qPerformance)
                .from(qPerformance)
                .leftJoin(qLike).on(qPerformance.id.eq(qLike.performance.id))
                .where(qPerformance.endAt.after(LocalDate.now()))
                .groupBy(qPerformance.id)
                .orderBy(qLike.id.count().add(qPerformance.viewCount).desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }

    /**
     * 장르별 추천 공연 조회
     * @param pageable 페이지네이션 정보
     * @description 장르별 인기순 1,2위 공연들을 조회
     */
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
                    .where(
                            qPerformance.genreType.in(value)
                                    .and(qPerformance.endAt.after(LocalDate.now())))
                    .groupBy(qPerformance.id)
                    .orderBy(qLike.id.count().add(qPerformance.viewCount).desc())
                    .limit(2)
                    .fetch();
            result.addAll(fetch);
        }

        List<Performance> paged = new ArrayList<>();
        long offset = pageable.getOffset();
        int pageSize = pageable.getPageSize();
        long endIndex = Math.min(result.size(), pageSize + (offset));
        // 0~4, 4~8 , 8~12, 12~16 ...
        for (long i = offset; i < endIndex; i++) {
            paged.add(result.get((int) i));
        }

        return paged;
    }
}
