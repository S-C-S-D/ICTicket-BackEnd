package com.sparta.icticket.banner.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sparta.icticket.common.enums.BannerType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.time.LocalDateTime;

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

    @NotNull(message = "등록 시간을 입력해 주세요.")
    @JsonFormat(pattern = "yyyy-MM-dd-HH:mm")
    private LocalDateTime startAt;

    @NotNull(message = "종료 시간을 입력해 주세요.")
    @JsonFormat(pattern = "yyyy-MM-dd-HH:mm")
    private LocalDateTime endAt;

}
