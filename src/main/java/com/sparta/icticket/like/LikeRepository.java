package com.sparta.icticket.like;

import com.sparta.icticket.performance.Performance;
import com.sparta.icticket.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, Long> {

    // 해당 user와 performance를 가진 like 객체 조회
    Optional<Like> findByUserAndPerformance(User findUser, Performance findPerformance);

    // 해당 performance를 가진 like 객체의 개수 조회
    Long countByPerformance(Performance findPerformance);

    // 해당 id, performance, user를 가진 like 객체 조회
    Optional<Like> findByIdAndPerformanceAndUser(Long likeId, Performance findPerformance, User loginUser);

    // 객체를 user로 넘겨주면 findByUserAndPerformance와 같은 역할을 해서 삭제 필요
    Optional<Like> findLikeIdByPerformanceIdAndUserId(Long performanceId, Long id);
}
