package com.sparta.icticket.banner;

import com.sparta.icticket.common.enums.BannerType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BannerRepository extends JpaRepository<Banner, Long>, BannerRepositoryQuery {

    //주어진 위치에 해당하는 배너를 반환합니다.
    Optional<Banner> findByPosition(Integer position);
}
