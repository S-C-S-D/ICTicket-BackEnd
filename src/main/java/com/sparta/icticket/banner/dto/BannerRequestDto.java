package com.sparta.icticket.banner.dto;

import com.sparta.icticket.common.enums.BannerType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class BannerRequestDto {
    @NotNull(message = "배너 포지션을 입력해주세요.")
    private Integer position;
    @NotBlank(message = "링크 url을 입력해주세요.")
    private String linkUrl;
    @NotNull(message = "배너의 타입을 입력해주세요.")
    private BannerType bannerType;
    @NotBlank(message = "배너의 이미지 주소를 입력해주세요")
    private String bannerImageUrl;
}
