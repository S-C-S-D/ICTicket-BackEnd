package com.sparta.icticket.banner;

import com.sparta.icticket.banner.dto.BannerResponseDto;
import com.sparta.icticket.common.enums.BannerType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BannerService {

    private final BannerRepository bannerRepository;

    /**
     * 배너 타입별 배너 조회(10개)
     * @param bannerType 배너 타입
     * @return 배너 리스트
     */
    public List<BannerResponseDto> getBannersByType(BannerType bannerType) {
        List<Banner> bannerList = bannerRepository.findTop10Banner(bannerType);
        return bannerList.stream().map(BannerResponseDto::new).toList();
    }
}
