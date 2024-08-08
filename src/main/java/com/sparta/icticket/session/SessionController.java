package com.sparta.icticket.session;

import com.sparta.icticket.common.dto.ResponseDataDto;
import com.sparta.icticket.common.enums.ErrorType;
import com.sparta.icticket.common.enums.SuccessStatus;
import com.sparta.icticket.common.exception.CustomException;
import com.sparta.icticket.performance.Performance;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class SessionController {

    private final SessionService sessionService;

    /**
     * 세션 조회
     * @param performanceId
     * @return
     */
    @GetMapping("/performances/{performanceId}/sessions")
    public ResponseEntity<ResponseDataDto<List<GetSessionsResponseDto>>> getSessions(
            @PathVariable Long performanceId
    ) {
        List<GetSessionsResponseDto> getSessionsResponseDtoList = sessionService.getSessions(performanceId);
        return ResponseEntity.ok(new ResponseDataDto<>(SuccessStatus.SESSION_GET_INFOS_SUCCESS, getSessionsResponseDtoList));
    }



}
