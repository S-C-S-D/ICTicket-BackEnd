package com.sparta.icticket.user.dto;

import com.sparta.icticket.user.User;
import lombok.Getter;

@Getter
public class UserProfileResponseDto {
    private Long id;
    private String nickname;
    private String email;
    private String address;
    private String phoneNumber;
    private Integer orderCount;

    public UserProfileResponseDto(User user, Integer orderCount) {
        this.id = user.getId();
        this.nickname = user.getNickname();
        this.email = user.getEmail();
        this.address = user.getAddress();
        this.phoneNumber = user.getPhoneNumber();
        this.orderCount = orderCount;
    }
}
