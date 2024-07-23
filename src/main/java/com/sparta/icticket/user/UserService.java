package com.sparta.icticket.user;

import com.sparta.icticket.common.enums.ErrorType;
import com.sparta.icticket.common.exception.CustomException;
import com.sparta.icticket.user.dto.UserSignupRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void createUser(UserSignupRequestDto requestDto) {
        checkDuplicateEmail(requestDto.getEmail());

        String encodedPassword = passwordEncoder.encode(requestDto.getPassword());

        User saveUser = new User(requestDto, encodedPassword);

        userRepository.save(saveUser);
    }

    public void logout(User loginUser) {
        loginUser.removeRefreshToken();
        userRepository.save(loginUser);
    }

    private void checkDuplicateEmail(String email) {
        userRepository.findByEmail(email).ifPresent(e -> {
                throw new CustomException(ErrorType.ALREADY_EXISTS_EMAIL);});
    }
}
