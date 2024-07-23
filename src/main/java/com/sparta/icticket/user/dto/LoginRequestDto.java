package com.sparta.icticket.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class LoginRequestDto {
    @NotBlank(message = "이메일을 입력해 주세요.")
    @Email(message = "올바른 이메일 형식을 입력해 주세요.")
    private String email;

    @NotBlank(message = "비밀번호를 입력해 주세요.")
    @Pattern(regexp = "^(?=.*[a-zA-Z])[a-zA-Z0-9!@#$%^&*()_+\\-={}|:;<>?,./~`]{8,16}$",
            message = "비밀번호는 최소 1개의 영문자를 포함해야 하며, 숫자나 특수문자는 선택사항입니다. 길이는 8자에서 16자 사이여야 합니다. 한글은 포함할 수 없습니다.")
    private String password;
}
