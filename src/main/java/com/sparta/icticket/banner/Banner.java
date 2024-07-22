package com.sparta.icticket.banner;

import com.sparta.icticket.common.Timestamped;
import com.sparta.icticket.common.enums.BannerType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "banners")
public class Banner extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Integer position;

    @Column(nullable = false)
    private Long linkUrl;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BannerType bannerType;

    @Column(nullable = false)
    private String bannerImageUrl;
}
