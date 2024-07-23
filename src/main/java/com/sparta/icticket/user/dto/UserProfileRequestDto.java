package com.sparta.icticket.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class UserProfileRequestDto {
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
