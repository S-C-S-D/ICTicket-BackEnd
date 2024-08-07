package com.sparta.icticket.admin.service;

import com.sparta.icticket.banner.Banner;
import com.sparta.icticket.banner.BannerRepository;
import com.sparta.icticket.banner.dto.BannerRequestDto;
import com.sparta.icticket.common.enums.ErrorType;
import com.sparta.icticket.common.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AdminBannerService {
    private final BannerRepository bannerRepository;

    /**
     * 배너 등록
     * @param requestDto
     */
    public void createBanners(BannerRequestDto requestDto) {
        Optional<Banner> byPosition = bannerRepository.findByPosition(requestDto.getPosition());
        if(byPosition.isPresent()){
            throw new CustomException(ErrorType.ALREADY_EXISTS_BANNER_POSITION);
        }
        Banner banner = new Banner(requestDto);
        bannerRepository.save(banner);
    }


    /**
     * 배너 삭제
     * @param bannerId
     */
    public void deleteBanner(Long bannerId) {
        Banner banner = bannerRepository.findById(bannerId).orElseThrow(
                () -> new CustomException(ErrorType.NOT_FOUND_BANNER));
        bannerRepository.delete(banner);
    }
}
