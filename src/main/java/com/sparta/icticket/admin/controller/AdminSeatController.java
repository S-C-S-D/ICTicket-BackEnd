package com.sparta.icticket.admin.controller;

import com.sparta.icticket.admin.service.AdminSeatService;
import com.sparta.icticket.admin.seat.dto.SeatCreateRequestDto;
import com.sparta.icticket.common.dto.ResponseMessageDto;
import com.sparta.icticket.common.enums.SuccessStatus;
import com.sparta.icticket.security.UserDetailsImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/sessions/{sessionId}")
public class AdminSeatController {

    private final AdminSeatService adminSeatService;

    /**
     * 좌석 생성 기능
     * @param sessionId
     * @param requestDto
     * @param userDetails
     * @return
     */
    @PostMapping("/seats")
    public ResponseEntity<ResponseMessageDto> createSeat(
            @PathVariable Long sessionId,
            @RequestBody @Valid SeatCreateRequestDto requestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        adminSeatService.createSeat(sessionId, requestDto, userDetails.getUser());
        return ResponseEntity.ok(new ResponseMessageDto(SuccessStatus.SEAT_CREATE_SUCCESS));
    }

    /**
     * 좌석 삭제 기능
     * @param sessionId
     * @param seatId
     * @param userDetails
     * @return
     */
    @DeleteMapping("/seats/{seatId}")
    public ResponseEntity<ResponseMessageDto> deleteSeat(
            @PathVariable Long sessionId,
            @PathVariable Long seatId,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        adminSeatService.deleteSeat(sessionId, seatId, userDetails.getUser());
        return ResponseEntity.ok(new ResponseMessageDto(SuccessStatus.SEAT_DELETE_SUCCESS));
    }
}
