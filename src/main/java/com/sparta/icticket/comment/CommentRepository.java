package com.sparta.icticket.comment;

import com.sparta.icticket.performance.Performance;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query("select c from Comment c join fetch c.user where c.performance = :performance")
    Optional<List<Comment>> findByPerformanceOrderByCreatedAtDesc(@Param("performance") Performance performance);

}
