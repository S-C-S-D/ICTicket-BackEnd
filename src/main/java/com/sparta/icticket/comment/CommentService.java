package com.sparta.icticket.comment;

import com.sparta.icticket.common.enums.ErrorType;
import com.sparta.icticket.common.exception.CustomException;
import com.sparta.icticket.performance.Performance;
import com.sparta.icticket.performance.PerformanceRepository;
import com.sparta.icticket.user.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Slf4j(topic = "CommentService")
@Service
@Transactional
@RequiredArgsConstructor
public class CommentService {

    private final PerformanceRepository performanceRepository;
    private final CommentRepository commentRepository;

    /**
     * 댓글 작성
     *
     * @param performanceId
     * @param createCommentRequestDto
     * @param loginUser
     */
    public void createComment(Long performanceId, CreateCommentRequestDto createCommentRequestDto, User loginUser) {
        Performance performance = validatePerformance(performanceId);
        Comment comment = new Comment(createCommentRequestDto, loginUser, performance);
        commentRepository.save(comment);

    }

    /**
     * 댓글삭제
     * @param performanceId
     * @param commentId
     * @param loginUser
     */
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

        commentRepository.delete(comment);

    }

    /**
     * 단일 공연 댓글 조회
     * @param performanceId
     * @return
     */
    public List<GetCommentResponseDto> getComments(Long performanceId) {
        Performance performance = validatePerformance(performanceId);
        log.info("=============comment를 불러올때 user도 페치조인이 되는지==============");
        List<Comment> comments= commentRepository.findByPerformanceOrderByCreatedAtDesc(performance)
                .orElseThrow(()-> new CustomException(ErrorType.NOT_FOUND_COMMENT));
        log.info("=============여기사이에 user 관련 쿼리가 없어야함==============");
                return comments.stream().map(GetCommentResponseDto::new).toList();

    }

    /*메서드*/
    private Comment validateComment(Long commentId) {
        return commentRepository.findById(commentId)
                .orElseThrow(() -> new CustomException(ErrorType.NOT_FOUND_COMMENT));
    }

    private Performance validatePerformance(Long performanceId) {
       return performanceRepository.findById(performanceId) // query만 적용시 여기서 invocationTargetException 오류 발생
                .orElseThrow(() -> new CustomException(ErrorType.NOT_FOUND_PERFORMANCE));
    }


}
