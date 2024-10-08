package com.sparta.icticket.seat;

import com.sparta.icticket.common.dto.ResponseDataDto;
import com.sparta.icticket.common.enums.SuccessStatus;
import com.sparta.icticket.common.security.UserDetailsImpl;
import com.sparta.icticket.seat.dto.SeatCountResponseDto;
import com.sparta.icticket.seat.dto.SeatInfoResponseDto;
import com.sparta.icticket.seat.dto.SeatReservedRequestDto;
import com.sparta.icticket.seat.dto.SeatReservedResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping
public class SeatController {

    private final SeatService seatService;

    /**
     * 세션별 잔여 좌석 조회
     * @param performanceId
     * @param sessionId
     */
    @GetMapping("/performances/{performanceId}/sessions/{sessionId}/seat-count")
    public ResponseEntity<ResponseDataDto<SeatCountResponseDto>> getSeatCount(
            @PathVariable Long performanceId, @PathVariable Long sessionId) {
        SeatCountResponseDto responseDto = seatService.getSeatCount(performanceId, sessionId);
        return ResponseEntity.ok(new ResponseDataDto<>(SuccessStatus.SESSION_GET_SEAT_INFOS_SUCCESS, responseDto));
    }

    /**
     * 세션별 좌석 상세 조회
     * @param performanceId
     * @param sessionId
     */
    @GetMapping("/performances/{performanceId}/sessions/{sessionId}/seats")
    public ResponseEntity<ResponseDataDto<List<SeatInfoResponseDto>>> getSeats(
            @PathVariable Long performanceId, @PathVariable Long sessionId) {
        List<SeatInfoResponseDto> responseDto = seatService.getSeats(performanceId, sessionId);
        return ResponseEntity.ok(new ResponseDataDto<>(SuccessStatus.SESSION_GET_SEAT_INFOS_SUCCESS, responseDto));
    }


    /**
     * 좌석 선택 완료
     * @param sessionId
     * @param requestDto
     */
    @PatchMapping("/sessions/{sessionId}/seats/reserve")
    public ResponseEntity<ResponseDataDto<SeatReservedResponseDto>> reserveSeat(
            @PathVariable Long sessionId, @RequestBody @Valid SeatReservedRequestDto requestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        SeatReservedResponseDto responseDto = seatService.reserveSeat(sessionId, requestDto, userDetails.getUser());
        return ResponseEntity.ok(new ResponseDataDto<>(SuccessStatus.SEAT_SELECT_SUCCESS, responseDto));
    }
}
