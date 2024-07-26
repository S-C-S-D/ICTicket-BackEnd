package com.sparta.icticket.banner;

import com.sparta.icticket.common.enums.BannerType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BannerRepository extends JpaRepository<Banner, Long> {
    List<Banner> findTop10ByBannerTypeOrderByPosition(BannerType bannerType);

    Optional<Banner> findByPosition(Integer position);
}
