package com.sparta.icticket.comment;

import com.sparta.icticket.common.Timestamped;
import com.sparta.icticket.performance.Performance;
import com.sparta.icticket.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "comments") // 페이징 처리 추천
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
}
