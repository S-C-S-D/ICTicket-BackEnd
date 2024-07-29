package com.sparta.icticket.user;


import com.sparta.icticket.common.enums.UserStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    Optional<User> findByNickname(String nickname);

    Optional<User> findByEmailAndUserStatus(String email, UserStatus userStatus);
}
