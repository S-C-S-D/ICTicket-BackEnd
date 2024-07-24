package com.sparta.icticket.admin.controller;

import com.sparta.icticket.admin.controller.sales.dto.SalesAddRequestDto;
import com.sparta.icticket.admin.service.AdminSalesService;
import com.sparta.icticket.common.dto.ResponseMessageDto;
import com.sparta.icticket.common.enums.SuccessStatus;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/performances")
public class AdminSalesController {

    private final AdminSalesService adminSalesService;

    /**
     * 할인 등록 기능
     * @param performanceId
     * @param requestDto
     * @return
     */
    @PostMapping("/{performanceId}/sales")
    public ResponseEntity<ResponseMessageDto> addSales(
            @PathVariable Long performanceId,
            @RequestBody @Valid SalesAddRequestDto requestDto) {
        adminSalesService.addSales(performanceId, requestDto);
        return ResponseEntity.ok(new ResponseMessageDto(SuccessStatus.DISCOUNT_CREATE_SUCCESS));
    }
}
