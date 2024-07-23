package com.sparta.icticket.user;

import com.sparta.icticket.common.enums.ErrorType;
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
        checkDuplicateNickname(requestDto.getNickname());

        String encodedPassword = passwordEncoder.encode(requestDto.getPassword());

        User saveUser = new User(requestDto, encodedPassword);

        userRepository.save(saveUser);
    }


    /**
     * 회원 탈퇴
     * @param requestDto
     * @param loginUser
     */
    @Transactional
    public void resignUser(UserResignRequestDto requestDto, User loginUser) {
        User findUser = findUserByEmail(loginUser.getEmail());

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
        User findUser = findUserByEmail(loginUser.getEmail());

        checkDuplicateNickname(requestDto.getNickname());

        findUser.updateUserProfile(requestDto);
    }

    /**
     * 프로필 조회
     * @param loginUser
     * @return
     */
    public UserProfileResponseDto getProfile(User loginUser) {
        User findUser = findUserByEmail(loginUser.getEmail());

        Integer orderCount = orderRepository.countAllByUser(findUser);

        return new UserProfileResponseDto(findUser, orderCount);
    }

    /**
     * 이메일 중복 검사
     * @param email
     */
    private void checkDuplicateEmail(String email) {
        userRepository.findByEmail(email).ifPresent(e -> {
            throw new CustomException(ErrorType.ALREADY_EXISTS_EMAIL);
        });

    }

    /**
     * 닉네임 중복 검사(탈퇴 회원이 사용했던 닉네임은 사용 가능)
     * @param nickname
     */
    private void checkDuplicateNickname(String nickname) {

        if(userRepository.findByNickname(nickname).isPresent()) {
            User findUser = userRepository.findByNickname(nickname).get();

            if (findUser.getUserStatus().equals(UserStatus.ACTIVATE)) {
                throw new CustomException(ErrorType.ALREADY_EXISTS_NICKNAME);
            }
        }
    }

    /**
     * 유저 존재 여부 확인
     * @param email
     * @return
     */
    private User findUserByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() ->
                new CustomException(ErrorType.NOT_FOUND_USER));
    }
}
