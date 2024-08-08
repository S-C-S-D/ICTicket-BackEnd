package com.sparta.icticket.comment;

import com.sparta.icticket.common.Timestamped;
import com.sparta.icticket.common.enums.ErrorType;
import com.sparta.icticket.common.exception.CustomException;
import com.sparta.icticket.performance.Performance;
import com.sparta.icticket.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "comments")
public class Comment extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "performance_id", nullable = false)
    private Performance performance;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private Integer rate;

    public Comment(CreateCommentRequestDto createCommentRequestDto,User loginUser,Performance performance) {
        this.user = loginUser;
        this.performance = performance;
        this.title = createCommentRequestDto.getTitle();
        this.description = createCommentRequestDto.getDescription();
        this.rate= createCommentRequestDto.getRate();
    }

    public void checkPerformance(Long performanceId) {
        if (!this.performance.getId().equals(performanceId)) {
            throw new CustomException(ErrorType.NOT_FOUND_PERFORMANCE);
        }
    }

    public void checkUser(User loginUser) {
        if (!this.user.getId().equals(loginUser.getId())) {
            throw new CustomException(ErrorType.NOT_AVAILABLE_PERMISSION);
        }
    }
}
