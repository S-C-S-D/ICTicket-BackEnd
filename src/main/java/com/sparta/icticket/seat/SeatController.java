package com.sparta.icticket.seat;

import com.sparta.icticket.common.dto.ResponseDataDto;
import com.sparta.icticket.common.enums.SuccessStatus;
import com.sparta.icticket.seat.dto.SeatCountResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping
public class SeatController {

    private final SeatService seatService;

    /**
     * 세션별 잔여 좌석 조회 기능
     * @param performanceId
     * @param sessionId
     * @return
     */
    @GetMapping("/performances/{performanceId}/sessions/{sessionId}/seat-count")
    public ResponseEntity<ResponseDataDto<SeatCountResponseDto>> getSeatCount(
            @PathVariable Long performanceId, @PathVariable Long sessionId) {
        SeatCountResponseDto responseDto = seatService.getSeatCount(performanceId, sessionId);
        return ResponseEntity.ok(new ResponseDataDto<>(SuccessStatus.SESSION_GET_SEAT_INFOS_SUCCESS, responseDto));
    }
}
