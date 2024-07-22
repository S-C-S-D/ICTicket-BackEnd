package com.sparta.icticket.user;

import com.sparta.icticket.common.enums.ErrorType;
import com.sparta.icticket.common.exception.CustomException;
import com.sparta.icticket.user.dto.UserProfileRequestDto;
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

    /**
     * 회원 가입
     * @param requestDto
     */
    public void createUser(UserSignupRequestDto requestDto) {
        checkDuplicateEmail(requestDto.getEmail());

        String encodedPassword = passwordEncoder.encode(requestDto.getPassword());

        User saveUser = new User(requestDto, encodedPassword);

        userRepository.save(saveUser);
    }

    @Transactional
    public void deleteUser(UserResignRequestDto requestDto) {
//        User findUser = userRepository.findByEmail(loginUser.getEmail());

//        if(!passwordEncoder.matches(requestDto.getPassword(), findUser.getPassword()) {
//             throw new CustomException(ErrorType.INVALID_PASSWORD);
//        }
    }


    /**
     * 이메일 중복 검사
     * @param email
     */
    private void checkDuplicateEmail(String email) {
        userRepository.findByEmail(email).ifPresent(e -> {
                throw new CustomException(ErrorType.ALREADY_EXISTS_EMAIL);});
    }
}
