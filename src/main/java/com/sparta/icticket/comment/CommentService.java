package com.sparta.icticket.comment;

import com.sparta.icticket.common.enums.ErrorType;
import com.sparta.icticket.common.exception.CustomException;
import com.sparta.icticket.performance.Performance;
import com.sparta.icticket.performance.PerformanceRepository;
import com.sparta.icticket.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentService {

    private final PerformanceRepository performanceRepository;
    private final CommentRepository commentRepository;

    /**
     * 댓글 작성
     * @param performanceId
     * @param createCommentRequestDto
     * @param loginUser
     */
    public void createComment(Long performanceId, CreateCommentRequestDto createCommentRequestDto, User loginUser) {
        Performance performance = findPerformance(performanceId);
        Comment comment = new Comment(createCommentRequestDto,loginUser,performance);
        commentRepository.save(comment);
    }

    /**
     * 댓글삭제
     * @param performanceId
     * @param commentId
     * @param loginUser
     */
    public void deleteComment(Long performanceId, Long commentId,User loginUser) {
        Comment comment = findComment(commentId);
        
        comment.checkPerformance(performanceId);
        comment.checkUser(loginUser);

        commentRepository.delete(comment);

    }

    /**
     * 단일 공연 댓글 조회
     * @param performanceId
     * @return
     */
    public List<GetCommentResponseDto> getComments(Long performanceId) {
        Performance performance = findPerformance(performanceId);
        List<Comment> comments= commentRepository.findByPerformanceOrderByCreatedAtDesc(performance)
                .orElseThrow(()-> new CustomException(ErrorType.NOT_FOUND_COMMENT));
                return comments.stream().map(GetCommentResponseDto::new).toList();
    }

    /**
     * 댓글 찾기
     * @param commentId
     * @description 댓글 삭제시 해당 댓글을 DB로부터 가져옴
     */
    private Comment findComment(Long commentId) {
        return commentRepository.findById(commentId)
                .orElseThrow(() -> new CustomException(ErrorType.NOT_FOUND_COMMENT));
    }

    /**
     * 공연 찾기
     * @param performanceId
     * @description 댓글 작성과 단일 공연 댓글 조회시 url으로부터 가져온 performanceId를 이용해 performance를 DB로부터 가져옴
     */
    private Performance findPerformance(Long performanceId) {
       return performanceRepository.findById(performanceId)
                .orElseThrow(() -> new CustomException(ErrorType.NOT_FOUND_PERFORMANCE));
    }
}
