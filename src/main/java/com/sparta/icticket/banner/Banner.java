package com.sparta.icticket.banner;

import com.sparta.icticket.banner.dto.BannerRequestDto;
import com.sparta.icticket.common.Timestamped;
import com.sparta.icticket.common.enums.BannerType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "banners")
public class Banner extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Integer position;

    @Column(nullable = false)
    private String linkUrl;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BannerType bannerType;

    @Column(nullable = false)
    private String bannerImageUrl;

    public Banner(BannerRequestDto requestDto) {
        this.position = requestDto.getPosition();
        this.linkUrl = requestDto.getLinkUrl();
        this.bannerType = requestDto.getBannerType();
        this.bannerImageUrl = requestDto.getBannerImageUrl();
    }
}
