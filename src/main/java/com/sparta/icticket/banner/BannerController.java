package com.sparta.icticket.banner;

import com.sparta.icticket.banner.dto.BannerResponseDto;
import com.sparta.icticket.common.dto.ResponseDataDto;
import com.sparta.icticket.common.enums.BannerType;
import com.sparta.icticket.common.enums.SuccessStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/banners")
public class BannerController {

    private final BannerService bannerService;

    /**
     * 배너 타입별 배너 조회(10개)
     * @param bannerType
     * @return
     */
    @GetMapping
    public ResponseEntity<ResponseDataDto<List<BannerResponseDto>>> getBannersByType(
            @RequestParam(value = "bannerType") BannerType bannerType){

        List<BannerResponseDto> responseDtoList = bannerService.getBannersByType(bannerType);

        return ResponseEntity.ok().body(new ResponseDataDto<>(SuccessStatus.BANNER_GET_SUCCESS, responseDtoList));
    }
}
