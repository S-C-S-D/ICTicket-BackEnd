package com.sparta.icticket.user.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class UserResignRequestDto {
    @NotBlank(message = "비밀번호를 입력해 주세요.")
    private String password;
}
