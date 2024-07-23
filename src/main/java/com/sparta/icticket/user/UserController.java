package com.sparta.icticket.user;

import com.sparta.icticket.common.dto.ResponseMessageDto;
import com.sparta.icticket.common.enums.SuccessStatus;
import com.sparta.icticket.security.UserDetailsImpl;
import com.sparta.icticket.security.jwt.JwtUtil;
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

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<ResponseMessageDto> createUser(
            @Valid @RequestBody UserSignupRequestDto requestDto) {
        userService.createUser(requestDto);
        return ResponseEntity.ok(new ResponseMessageDto(SuccessStatus.USER_SIGN_UP_SUCCESS));
    }

    @PostMapping("/logout")
    public ResponseEntity<ResponseMessageDto> logout(@AuthenticationPrincipal UserDetailsImpl userDetails, HttpServletResponse response){
        userService.logout(userDetails.getUser());
        response.setHeader(JwtUtil.AUTH_ACCESS_HEADER, "");
        return ResponseEntity.ok(new ResponseMessageDto(SuccessStatus.USER_LOGOUT_SUCCESS));
    }
}
