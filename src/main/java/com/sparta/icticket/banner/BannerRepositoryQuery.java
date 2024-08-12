package com.sparta.icticket.banner;

import com.sparta.icticket.common.enums.BannerType;

import java.util.List;

public interface BannerRepositoryQuery {

    List<Banner> findTop10Banner(BannerType bannerType);
}
