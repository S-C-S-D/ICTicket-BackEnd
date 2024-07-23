package com.sparta.icticket.user;

import com.sparta.icticket.common.dto.ResponseDataDto;
import com.sparta.icticket.common.dto.ResponseMessageDto;
import com.sparta.icticket.common.enums.SuccessStatus;
import com.sparta.icticket.security.UserDetailsImpl;
import com.sparta.icticket.user.dto.UserProfileRequestDto;
import com.sparta.icticket.user.dto.UserProfileResponseDto;
import com.sparta.icticket.security.jwt.JwtUtil;
import com.sparta.icticket.user.dto.UserResignRequestDto;
import com.sparta.icticket.user.dto.UserSignupRequestDto;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    /**
     * 회원 가입 기능
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
     * [테스트용]어드민 회원 가입 기능
     * @param requestDto
     * @return
     */
    @PostMapping("/admin")
    public ResponseEntity<ResponseMessageDto> createAdminUser(
            @Valid @RequestBody UserSignupRequestDto requestDto) {
        userService.createAdminUser(requestDto);
        return ResponseEntity.ok(new ResponseMessageDto(SuccessStatus.USER_SIGN_UP_SUCCESS));
    }

    /**
     * 로그아웃 기능
     *
     * @param userDetails
     * @param response
     * @return
     */
    @PostMapping("/logout")
    public ResponseEntity<ResponseMessageDto> logout(@AuthenticationPrincipal UserDetailsImpl userDetails, HttpServletResponse response) {
        userService.logout(userDetails.getUser());
        response.setHeader(JwtUtil.AUTH_ACCESS_HEADER, "");
        return ResponseEntity.ok(new ResponseMessageDto(SuccessStatus.USER_LOGOUT_SUCCESS));
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
        userService.resignUser(requestDto, userDetails.getUser());
        return ResponseEntity.ok(new ResponseMessageDto(SuccessStatus.USER_DEACTIVATE_SUCCESS));
    }

    /**
     * 프로필 수정 기능
     * @param requestDto
     * @param userDetails
     * @return
     */
    @PatchMapping("/profile")
    public ResponseEntity<ResponseMessageDto> updateProfile(
            @Valid @RequestBody UserProfileRequestDto requestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        userService.updateProfile(requestDto, userDetails.getUser());
        return ResponseEntity.ok(new ResponseMessageDto(SuccessStatus.USER_UPDATE_SUCCESS));
    }

    /**
     * 프로필 조회 기능
     * @param userDetails
     * @return
     */
    @GetMapping("/profile")
    public ResponseEntity<ResponseDataDto<UserProfileResponseDto>> getProfile(
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        UserProfileResponseDto responseDto = userService.getProfile(userDetails.getUser());
        return ResponseEntity.ok(new ResponseDataDto<>(SuccessStatus.USER_GET_INFO_SUCCESS, responseDto));
    }
}
