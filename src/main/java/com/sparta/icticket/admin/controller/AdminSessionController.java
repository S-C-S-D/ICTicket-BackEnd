package com.sparta.icticket.admin.controller;

import com.sparta.icticket.admin.service.AdminSessionService;
import com.sparta.icticket.admin.session.dto.CreateSessionRequestDto;
import com.sparta.icticket.admin.session.dto.UpdateSessionRequestDto;
import com.sparta.icticket.common.dto.ResponseMessageDto;
import com.sparta.icticket.common.enums.SuccessStatus;
import com.sparta.icticket.security.UserDetailsImpl;
import com.sparta.icticket.user.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j(topic = "AdminSessionController")
@RequiredArgsConstructor
@RestController
@RequestMapping("/admin/performances/{performanceId}/sessions")
public class AdminSessionController {

    private final AdminSessionService adminSessionService;

    /*session 등록*/
    @PostMapping
    public ResponseEntity<ResponseMessageDto> createSession(
            @PathVariable Long performanceId,
            @RequestBody @Valid CreateSessionRequestDto createSessionRequestDto
    ) {
        adminSessionService.createSession(performanceId, createSessionRequestDto);
        return ResponseEntity.ok(new ResponseMessageDto(SuccessStatus.SESSION_CREATE_SUCCESS));
    }

    /*session 수정*/
    @PatchMapping("/{sessionId}")
    public ResponseEntity<ResponseMessageDto> updateSession(
            @PathVariable Long performanceId,
            @PathVariable Long sessionId,
            @RequestBody @Valid UpdateSessionRequestDto updateSessionRequestDto
    ) {
        adminSessionService.updateSession(performanceId,sessionId, updateSessionRequestDto);
        return ResponseEntity.ok(new ResponseMessageDto(SuccessStatus.SESSION_UPDATE_SUCCESS));
    }

    /*session 삭제*/
    @DeleteMapping("/{sessionId}")
    public ResponseEntity<ResponseMessageDto> deleteSession(
            @PathVariable Long performanceId,
            @PathVariable Long sessionId
    ) {
        adminSessionService.deleteSession(performanceId, sessionId);
        return ResponseEntity.ok(new ResponseMessageDto(SuccessStatus.SESSION_DELETE_SUCCESS));
    }

}
