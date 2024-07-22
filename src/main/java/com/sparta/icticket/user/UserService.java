package com.sparta.icticket.user;

import com.sparta.icticket.user.dto.UserSignupRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public void createUser(UserSignupRequestDto requestDto) {
        checkDuplicateEmail(requestDto.getEmail());

//        String encodedPassword = passwordEncoder.encode(requestDto.getPassword());

        User saveUser = new User(requestDto, requestDto.getPassword());

        userRepository.save(saveUser);
    }

    private void checkDuplicateEmail(String email) {
        userRepository.findByEmail(email).ifPresent(e -> {
                throw new IllegalArgumentException("중복된 이메일 입니다.");});
    }
}
