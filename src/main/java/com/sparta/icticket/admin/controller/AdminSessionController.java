package com.sparta.icticket.admin.controller;

import com.sparta.icticket.admin.service.AdminSessionService;
import com.sparta.icticket.session.dto.CreateSessionRequestDto;
import com.sparta.icticket.session.dto.UpdateSessionRequestDto;
import com.sparta.icticket.common.dto.ResponseMessageDto;
import com.sparta.icticket.common.enums.SuccessStatus;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j(topic = "AdminSessionController")
@RequiredArgsConstructor
@RestController
@RequestMapping("/admin/performances/{performanceId}/sessions")
public class AdminSessionController {

    private final AdminSessionService adminSessionService;

    /**
     * 세션 등록
     * @param performanceId
     * @param createSessionRequestDto
     * @return
     */
    @PostMapping
    public ResponseEntity<ResponseMessageDto> createSession(
            @PathVariable Long performanceId,
            @RequestBody @Valid CreateSessionRequestDto createSessionRequestDto
    ) {
        adminSessionService.createSession(performanceId, createSessionRequestDto);
        return ResponseEntity.ok(new ResponseMessageDto(SuccessStatus.SESSION_CREATE_SUCCESS));
    }

    /**
     * 세션 수정
     * @param performanceId
     * @param sessionId
     * @param updateSessionRequestDto
     * @return
     */
    @PatchMapping("/{sessionId}")
    public ResponseEntity<ResponseMessageDto> updateSession(
            @PathVariable Long performanceId,
            @PathVariable Long sessionId,
            @RequestBody @Valid UpdateSessionRequestDto updateSessionRequestDto
    ) {
        adminSessionService.updateSession(performanceId,sessionId, updateSessionRequestDto);
        return ResponseEntity.ok(new ResponseMessageDto(SuccessStatus.SESSION_UPDATE_SUCCESS));
    }

    /**
     * 세션 삭제
     * @param performanceId
     * @param sessionId
     * @return
     */
    @DeleteMapping("/{sessionId}")
    public ResponseEntity<ResponseMessageDto> deleteSession(
            @PathVariable Long performanceId,
            @PathVariable Long sessionId
    ) {
        adminSessionService.deleteSession(performanceId, sessionId);
        return ResponseEntity.ok(new ResponseMessageDto(SuccessStatus.SESSION_DELETE_SUCCESS));
    }

}
