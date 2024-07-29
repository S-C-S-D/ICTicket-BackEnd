package com.sparta.icticket.banner.dto;

import com.sparta.icticket.banner.Banner;
import com.sparta.icticket.common.enums.BannerType;
import lombok.Getter;

@Getter
public class BannerResponseDto {
    private Long id;
    private Integer position;
    private String linkUrl;
    private BannerType bannerType;
    private String bannerImageUrl;

    public BannerResponseDto(Banner banner){
        this.id = banner.getId();
        this.position = banner.getPosition();
        this.linkUrl = banner.getLinkUrl();
        this.bannerType = banner.getBannerType();
        this.bannerImageUrl = banner.getBannerImageUrl();
    }
}
