package com.sparta.icticket.admin.seat;

import com.sparta.icticket.admin.seat.dto.SeatCreateRequestDto;
import com.sparta.icticket.common.dto.ResponseMessageDto;
import com.sparta.icticket.common.enums.SuccessStatus;
import com.sparta.icticket.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/sessions")
public class AdminSeatController {

    private final AdminSeatService adminSeatService;

    /**
     * 좌석 생성 기능
     * @param sessionId
     * @param requestDto
     * @param userDetails
     * @return
     */
    @PostMapping("/{sessionId}/seats")
    public ResponseEntity<ResponseMessageDto> createSeat(
            @PathVariable Long sessionId,
            @RequestBody SeatCreateRequestDto requestDto,
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
    @DeleteMapping("/{sessionId}/seats/{seatId}")
    public ResponseEntity<ResponseMessageDto> deleteSeat(
            @PathVariable Long sessionId,
            @PathVariable Long seatId,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        adminSeatService.deleteSeat(sessionId, seatId, userDetails.getUser());
        return ResponseEntity.ok(new ResponseMessageDto(SuccessStatus.SEAT_DELETE_SUCCESS));
    }
}
