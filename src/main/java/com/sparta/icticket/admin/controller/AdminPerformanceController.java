package com.sparta.icticket.admin.controller;

import com.sparta.icticket.admin.service.AdminPerformanceService;
import com.sparta.icticket.common.dto.ResponseMessageDto;
import com.sparta.icticket.common.enums.SuccessStatus;
import com.sparta.icticket.performance.dto.PerformanceRequestDto;
import com.sparta.icticket.security.UserDetailsImpl;
import com.sparta.icticket.user.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/performances")
public class AdminPerformanceController {

    private final AdminPerformanceService adminPerformanceService;

    @PostMapping
    public ResponseEntity<ResponseMessageDto> createPerformance(
            @Valid @RequestBody PerformanceRequestDto requestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails){
        User loginUser = userDetails.getUser();

        adminPerformanceService.createPerformance(requestDto, loginUser);

        return ResponseEntity.ok().body(new ResponseMessageDto(SuccessStatus.PERFORMANCE_CREATE_SUCCESS));
    }
}
