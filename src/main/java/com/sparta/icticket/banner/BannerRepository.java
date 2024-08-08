package com.sparta.icticket.banner;

import com.sparta.icticket.common.enums.BannerType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BannerRepository extends JpaRepository<Banner, Long> {

    //주어진 배너 타입에 따라 위치를 기준으로 내림차순 정렬된 상위 10개의 배너를 반환합니다.
    List<Banner> findTop10ByBannerTypeOrderByPositionDesc(BannerType bannerType);

    //주어진 위치에 해당하는 배너를 반환합니다.
    Optional<Banner> findByPosition(Integer position);
}
