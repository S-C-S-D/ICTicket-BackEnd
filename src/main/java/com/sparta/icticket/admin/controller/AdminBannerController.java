package com.sparta.icticket.admin.controller;

import com.sparta.icticket.admin.service.AdminBannerService;
import com.sparta.icticket.banner.dto.BannerRequestDto;
import com.sparta.icticket.common.dto.ResponseMessageDto;
import com.sparta.icticket.common.enums.SuccessStatus;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/banners")
@RequiredArgsConstructor
public class AdminBannerController {

    private final AdminBannerService adminBannerService;

    /**
     * 배너 등록
     * @param requestDto
     * @return
     */
    @PostMapping
    public ResponseEntity<ResponseMessageDto> createBanners(
            @Valid @RequestBody BannerRequestDto requestDto) {
        adminBannerService.createBanners(requestDto);
        return ResponseEntity.ok().body(new ResponseMessageDto(SuccessStatus.BANNER_CREATE_SUCCESS));
    }

    /**
     * 배너 삭제
     * @param bannerId
     * @return
     */
    @DeleteMapping("/{bannerId}")
    public ResponseEntity<ResponseMessageDto> deleteBanner(
            @PathVariable Long bannerId){
        adminBannerService.deleteBanner(bannerId);
        return ResponseEntity.ok().body(new ResponseMessageDto(SuccessStatus.BANNER_DELETE_SUCCESS));
    }
}
