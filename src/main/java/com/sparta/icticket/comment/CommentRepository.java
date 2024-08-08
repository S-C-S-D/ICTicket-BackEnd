package com.sparta.icticket.comment;

import com.sparta.icticket.performance.Performance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    // performance 객체를 통해 DB로부터 해당 공연의 comment들을 최신순으로 가져온다
    Optional<List<Comment>> findByPerformanceOrderByCreatedAtDesc(Performance performance);
}
