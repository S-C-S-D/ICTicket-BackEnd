package com.sparta.icticket.comment;

import com.sparta.icticket.common.enums.ErrorType;
import com.sparta.icticket.common.exception.CustomException;
import com.sparta.icticket.performance.Performance;
import com.sparta.icticket.performance.PerformanceRepository;
import com.sparta.icticket.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentService {

    private final PerformanceRepository performanceRepository;
    private final CommentRepository commentRepository;

    public void createComment(Long performanceId, CreateCommentRequestDto createCommentRequestDto, User loginUser) {
        Performance performance = validatePerformance(performanceId);
        Comment comment = new Comment(createCommentRequestDto,loginUser,performance);
        commentRepository.save(comment);
    }

    public void deleteComment(Long performanceId, Long commentId,User loginUser) {
        Comment comment = validateComment(commentId);

        boolean isPerformanceValid = comment.getPerformance().getId().equals(performanceId);
        if (!isPerformanceValid) {
            throw new CustomException(ErrorType.NOT_FOUND_PERFORMANCE);
        }

        boolean isCommentByLoggedInUser=comment.getUser().getId().equals(loginUser.getId());
        if (!isCommentByLoggedInUser) {
            throw new CustomException(ErrorType.NOT_AVAILABLE_PERMISSION);
        }

    }

    private Comment validateComment(Long commentId) {
        return commentRepository.findById(commentId)
                .orElseThrow(() -> new CustomException(ErrorType.NOT_FOUND_COMMENT));
    }

    private Performance validatePerformance(Long performanceId) {
       return performanceRepository.findById(performanceId)
                .orElseThrow(() -> new CustomException(ErrorType.NOT_FOUND_PERFORMANCE));
    }

}
