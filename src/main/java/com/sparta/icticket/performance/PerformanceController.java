package com.sparta.icticket.performance;

import com.sparta.icticket.common.dto.ResponseDataDto;
import com.sparta.icticket.common.enums.SuccessStatus;
import com.sparta.icticket.performance.dto.PerformanceDetailResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/performances")
@RequiredArgsConstructor
public class PerformanceController {

    private final PerformanceService performanceService;

    @GetMapping("/{performanceId}")
    public ResponseEntity<ResponseDataDto<PerformanceDetailResponseDto>> getPerformance(
            @PathVariable Long performanceId) {

        PerformanceDetailResponseDto responseDto = performanceService.getPerformance(performanceId);

        return ResponseEntity.ok().body(new ResponseDataDto<>(SuccessStatus.PERFORMANCE_GET_INFO_SUCCESS, responseDto));
    }


}
