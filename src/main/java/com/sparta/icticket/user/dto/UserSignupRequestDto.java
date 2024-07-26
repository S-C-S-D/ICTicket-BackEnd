package com.sparta.icticket.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserSignupRequestDto {

    @NotBlank(message = "이메일을 입력해 주세요.")
    @Email(message = "올바른 이메일 형식을 입력해 주세요.")
    private String email;

    @NotBlank(message = "비밀번호를 입력해 주세요.")
    @Pattern(regexp = "^(?=.*[a-zA-Z])[a-zA-Z0-9!@#$%^&*()_+\\-={}|:;<>?,./~`]{8,16}$",
            message = "비밀번호는 최소 1개의 영문자를 포함해야 하며, 숫자나 특수문자는 선택사항입니다. 길이는 8자에서 16자 사이여야 합니다. 한글은 포함할 수 없습니다.")
    private String password;

    @NotBlank(message = "이름을 입력해 주세요.")
    @Pattern(regexp = "^[a-zA-Z가-힣]{1,}$",
            message = "이름은 숫자나 특수문자를 포함할 수 없으며, 최소 1글자 이상이어야 합니다.")
    private String name;

    @NotBlank(message = "닉네임을 입력해 주세요.")
    @Pattern(regexp = "^[a-zA-Z]{3,12}$",
            message = "닉네임은 영문자만 가능하며, 3자에서 12자 사이여야 합니다.")
    private String nickname;

    @NotBlank(message = "전화번호를 입력해 주세요.")
    @Pattern(regexp = "^\\d{3}-\\d{4}-\\d{4}$",
            message = "전화번호는 010-1234-5678 형식이어야 합니다.")
    private String phoneNumber;

    @NotBlank(message = "주소를 입력해 주세요.")
    private String address;
}
