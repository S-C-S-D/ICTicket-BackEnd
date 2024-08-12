package com.sparta.icticket.banner;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.icticket.common.enums.BannerType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

import static com.sparta.icticket.banner.QBanner.banner;

@Repository
@RequiredArgsConstructor
public class BannerRepositoryQueryImpl implements BannerRepositoryQuery {

    private final JPAQueryFactory queryFactory;

    /**
     * bannerType 별 10개의 banner 반환
     * @param bannerType
     * @description 현재 시간이 start_at, end_at 사이이고 position 기준 내림차순 정렬시의 상위 10개의 banner 반환
     */
    @Override
    public List<Banner> findTop10Banner(BannerType bannerType) {
        LocalDateTime now = LocalDateTime.now();

        return queryFactory
                .selectFrom(banner)
                .where(banner.bannerType.eq(bannerType).and(banner.startAt.before(now).and(banner.endAt.after(now))))
                .orderBy(banner.position.desc())
                .limit(10).fetch();
    }
}
