package com.sparta.icticket.admin.controller;

import com.sparta.icticket.admin.service.AdminPerformanceService;
import com.sparta.icticket.common.dto.ResponseMessageDto;
import com.sparta.icticket.common.enums.SuccessStatus;
import com.sparta.icticket.performance.dto.PerformanceRequestDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/performances")
public class AdminPerformanceController {

    private final AdminPerformanceService adminPerformanceService;

    /**
     * 공연 등록
     * @param requestDto 공연 등록에 필요한 정보
     * @return
     */
    @PostMapping
    public ResponseEntity<ResponseMessageDto> createPerformance(
            @Valid @RequestBody PerformanceRequestDto requestDto){

        adminPerformanceService.createPerformance(requestDto);

        return ResponseEntity.ok().body(new ResponseMessageDto(SuccessStatus.PERFORMANCE_CREATE_SUCCESS));
    }

    /**
     * 공연 수정
     * @param performanceId 공연 id
     * @param requestDto 공연 수정에 필요한 정보
     * @return
     */
    @PatchMapping("/{performanceId}")
    public ResponseEntity<ResponseMessageDto> updatePerformance(
            @PathVariable Long performanceId,
            @Valid @RequestBody PerformanceRequestDto requestDto){

        adminPerformanceService.updatePerformance(performanceId, requestDto);

        return ResponseEntity.ok().body(new ResponseMessageDto(SuccessStatus.PERFORMANCE_UPDATE_SUCCESS));
    }

    /**
     * 공연 삭제
     * @param performanceId 공연 id
     * @return
     */
    @DeleteMapping("/{performanceId}")
    public ResponseEntity<ResponseMessageDto> deletePerformance(
            @PathVariable Long performanceId){

        adminPerformanceService.deletePerformance(performanceId);

        return ResponseEntity.ok().body(new ResponseMessageDto(SuccessStatus.PERFORMANCE_DELETE_SUCCESS));
    }

}
