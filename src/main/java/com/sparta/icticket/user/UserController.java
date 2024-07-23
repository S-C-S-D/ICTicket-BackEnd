package com.sparta.icticket.user;

import com.sparta.icticket.common.dto.ResponseMessageDto;
import com.sparta.icticket.common.enums.SuccessStatus;
import com.sparta.icticket.security.UserDetailsImpl;
import com.sparta.icticket.user.dto.UserResignRequestDto;
import com.sparta.icticket.user.dto.UserSignupRequestDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    /**
     * 회원 가입 기능
     *
     * @param requestDto
     * @return
     */
    @PostMapping
    public ResponseEntity<ResponseMessageDto> createUser(
            @Valid @RequestBody UserSignupRequestDto requestDto) {
        userService.createUser(requestDto);
        return ResponseEntity.ok(new ResponseMessageDto(SuccessStatus.USER_SIGN_UP_SUCCESS));
    }

    /**
     * 회원 탈퇴 기능
     * @param requestDto
     * @param userDetails
     * @return
     */
    @PatchMapping
    public ResponseEntity<ResponseMessageDto> updateUser(
            @Valid @RequestBody UserResignRequestDto requestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        userService.deleteUser(requestDto, userDetails.getUser());
        return ResponseEntity.ok(new ResponseMessageDto(SuccessStatus.USER_DEACTIVATE_SUCCESS));
    }
}
