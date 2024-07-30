package com.sparta.icticket.comment;

import com.sparta.icticket.performance.Performance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    Optional<List<Comment>> findByPerformanceOrderByCreatedAtDesc(Performance performance);

}
