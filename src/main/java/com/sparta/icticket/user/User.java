package com.sparta.icticket.user;

import com.sparta.icticket.common.Timestamped;
import com.sparta.icticket.common.enums.UserRole;
import com.sparta.icticket.common.enums.UserStatus;
import com.sparta.icticket.user.dto.UserProfileRequestDto;
import com.sparta.icticket.user.dto.UserSignupRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@Getter
@NoArgsConstructor
@Table(name = "users")
public class User extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false)
    private String phoneNumber;

    @Column(nullable = false)
    private String address;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserStatus userStatus;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserRole userRole;

    @Column(columnDefinition = "Text")
    private String refreshToken;

    public User(UserSignupRequestDto userSignupRequestDto, String encodedPassword) {
        this.email = userSignupRequestDto.getEmail();
        this.password = encodedPassword;
        this.name = userSignupRequestDto.getName();
        this.nickname = userSignupRequestDto.getNickname();
        this.phoneNumber = userSignupRequestDto.getPhoneNumber();
        this.address = userSignupRequestDto.getAddress();
        this.userStatus = UserStatus.ACTIVATE;
        this.userRole = UserRole.USER;
    }

    public void saveRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public boolean validateRefreshToken(String refreshToken) {
        return this.refreshToken != null && this.refreshToken.equals(refreshToken);
    }

    public void updateResignUser() {
        this.refreshToken = null;
        this.userStatus = UserStatus.DEACTIVATE;
    }

    public void updateUserProfile(UserProfileRequestDto requestDto) {
        this.nickname = requestDto.getNickname();
        this.phoneNumber = requestDto.getPhoneNumber();
        this.address = requestDto.getAddress();
    }

    public void removeRefreshToken() {
        this.refreshToken = null;
    }
}
