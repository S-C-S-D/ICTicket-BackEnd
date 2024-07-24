package com.sparta.icticket.admin.controller;

import com.sparta.icticket.admin.controller.sales.dto.SalesAddRequestDto;
import com.sparta.icticket.admin.controller.sales.dto.SalesUpdateRequestDto;
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

    /**
     * 할인 수정 기능
     * @param performanceId
     * @param salesId
     * @param requestDto
     * @return
     */
    @PatchMapping("/{performanceId}/sales/{salesId}")
    public ResponseEntity<ResponseMessageDto> updateSales(
            @PathVariable Long performanceId, @PathVariable Long salesId,
            @RequestBody @Valid SalesUpdateRequestDto requestDto) {
        adminSalesService.updateSales(performanceId, salesId, requestDto);
        return ResponseEntity.ok(new ResponseMessageDto(SuccessStatus.DISCOUNT_UPDATE_SUCCESS));
    }

    @DeleteMapping("{performanceId}/sales/{salesId}")
    public ResponseEntity<ResponseMessageDto> deleteSales(
            @PathVariable Long performanceId, @PathVariable Long salesId) {
        adminSalesService.deleteSales(performanceId, salesId);
        return ResponseEntity.ok(new ResponseMessageDto(SuccessStatus.DISCOUNT_DELETE_SUCCESS));
    }
}
