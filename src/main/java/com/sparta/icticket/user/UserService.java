package com.sparta.icticket.user;

import com.sparta.icticket.common.enums.ErrorType;
import com.sparta.icticket.common.enums.UserRole;
import com.sparta.icticket.common.enums.UserStatus;
import com.sparta.icticket.common.exception.CustomException;
import com.sparta.icticket.order.OrderRepository;
import com.sparta.icticket.user.dto.UserProfileRequestDto;
import com.sparta.icticket.user.dto.UserProfileResponseDto;
import com.sparta.icticket.user.dto.UserResignRequestDto;
import com.sparta.icticket.user.dto.UserSignupRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final OrderRepository orderRepository;

    /**
     * 회원 가입
     * @param requestDto
     */
    public void createUser(UserSignupRequestDto requestDto) {
        checkDuplicateEmail(requestDto.getEmail());

        if(checkDuplicateNickname(requestDto.getNickname()) != null) {
            throw new CustomException(ErrorType.ALREADY_EXISTS_NICKNAME);
        }

        String encodedPassword = passwordEncoder.encode(requestDto.getPassword());

        User saveUser = new User(requestDto, encodedPassword);

        userRepository.save(saveUser);
    }

    /**
     * [테스트용]어드민 회원 가입
     * @param requestDto
     */
    public void createAdminUser(UserSignupRequestDto requestDto) {
        checkDuplicateEmail(requestDto.getEmail());

        if(checkDuplicateNickname(requestDto.getNickname()) != null) {
            throw new CustomException(ErrorType.ALREADY_EXISTS_NICKNAME);
        }

        String encodedPassword = passwordEncoder.encode(requestDto.getPassword());

        User saveUser = new User(requestDto, encodedPassword, UserRole.ADMIN);

        userRepository.save(saveUser);
    }

    /**
     * 로그아웃
     * @param loginUser
     */
    public void logout(User loginUser) {
        loginUser.removeRefreshToken();
        userRepository.save(loginUser);
    }

    /**
     * 회원 탈퇴
     * @param requestDto
     * @param loginUser
     */
    @Transactional
    public void resignUser(UserResignRequestDto requestDto, User loginUser) {
        User findUser = getUserByEmailAndStatus(loginUser.getEmail());

        if(!passwordEncoder.matches(requestDto.getPassword(), findUser.getPassword())) {
             throw new CustomException(ErrorType.INVALID_PASSWORD);
        }

        findUser.updateResignUser();
    }

    /**
     * 프로필 수정
     * @param requestDto
     * @param loginUser
     */
    @Transactional
    public void updateProfile(UserProfileRequestDto requestDto, User loginUser) {
        User findUser = getUserByEmailAndStatus(loginUser.getEmail());

        User findUserByNickname = checkDuplicateNickname(requestDto.getNickname());
        if(findUserByNickname != null) {
            findUserByNickname.checkNicknameByUserStatus(findUser);
        }

        findUser.updateUserProfile(requestDto);
    }

    /**
     * 프로필 조회
     * @param loginUser
     */
    public UserProfileResponseDto getProfile(User loginUser) {
        User findUser = getUserByEmailAndStatus(loginUser.getEmail());

        Integer orderCount = orderRepository.countAllByUser(findUser);

        return new UserProfileResponseDto(findUser, orderCount);
    }

    /**
     * 이메일 중복 검사
     * @param email
     * @description 같은 email을 사용하는 user가 있는지 검증
     */
    private void checkDuplicateEmail(String email) {
        userRepository.findByEmail(email).ifPresent(e -> {
            throw new CustomException(ErrorType.ALREADY_EXISTS_EMAIL);
        });

    }

    /**
     * 닉네임 중복 사용자 조회
     * @param nickname
     * @description 같은 nickname을 사용하는 user 조회
     */
    private User checkDuplicateNickname(String nickname) {
        return userRepository.findByNickname(nickname);
    }

    /**
     * user 객체 조회
     * @param email
     * @description email과 user_status로 user 객체 조회
     */
    private User getUserByEmailAndStatus(String email) {
        return userRepository.findByEmailAndUserStatus(email, UserStatus.ACTIVATE).orElseThrow(() ->
                new CustomException(ErrorType.DEACTIVATE_USER));
    }
}
