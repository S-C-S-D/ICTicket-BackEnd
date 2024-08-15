package com.sparta.icticket.user;

import com.sparta.icticket.common.Timestamped;
import com.sparta.icticket.common.enums.ErrorType;
import com.sparta.icticket.common.enums.UserRole;
import com.sparta.icticket.common.enums.UserStatus;
import com.sparta.icticket.common.exception.CustomException;
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

    // user 생성
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

    //[테스트용 어드민]
    public User(UserSignupRequestDto userSignupRequestDto, String encodedPassword, UserRole userRole) {
        this.email = userSignupRequestDto.getEmail();
        this.password = encodedPassword;
        this.name = userSignupRequestDto.getName();
        this.nickname = userSignupRequestDto.getNickname();
        this.phoneNumber = userSignupRequestDto.getPhoneNumber();
        this.address = userSignupRequestDto.getAddress();
        this.userStatus = UserStatus.ACTIVATE;
        this.userRole = userRole;
    }

    public void saveRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public boolean validateRefreshToken(String refreshToken) {
        return this.refreshToken != null && this.refreshToken.equals(refreshToken);
    }

    // user 상태를 DEACTIVE로 변경
    public void updateResignUser() {
        this.refreshToken = null;
        this.userStatus = UserStatus.DEACTIVATE;
    }

    // user profile 변경
    public void updateUserProfile(UserProfileRequestDto requestDto) {
        this.nickname = requestDto.getNickname();
        this.phoneNumber = requestDto.getPhoneNumber();
        this.address = requestDto.getAddress();
    }

    // user의 resfreshToken을 null로 변경
    public void removeRefreshToken() {
        this.refreshToken = null;
    }

    // user의 user_status 확인
    public void checkNicknameByUserStatus(User user) {
        if(this.userStatus.equals(UserStatus.ACTIVATE) && !(this.equals(user))) {
            throw new CustomException(ErrorType.ALREADY_EXISTS_NICKNAME);
        }
    }

    public void checkUser(Long userId) {
        if(!this.id.equals(userId)) {
            throw new CustomException(ErrorType.CAN_NOT_LOAD_ORDER_HISTORY);
        }
    }
}
