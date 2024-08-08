package com.sparta.icticket.user;


import com.sparta.icticket.common.enums.UserStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    // 해당 email을 가진 user 객체 조회
    Optional<User> findByEmail(String email);

    // 해당 nickname을 가진 user 객체 조회
    Optional<User> findByNickname(String nickname);

    // 해당 email과 user_status를 가진 user 객체 조회
    Optional<User> findByEmailAndUserStatus(String email, UserStatus userStatus);
}
