package com.sparta.icticket.like;

import com.sparta.icticket.performance.Performance;
import com.sparta.icticket.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, Long> {
    Optional<Like> findByUserAndPerformance(User findUser, Performance findPerformance);
}
