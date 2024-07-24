package com.sparta.icticket.comment;

import com.sparta.icticket.common.dto.ResponseDataDto;
import com.sparta.icticket.common.dto.ResponseMessageDto;
import com.sparta.icticket.common.enums.SuccessStatus;
import com.sparta.icticket.security.UserDetailsImpl;
import com.sparta.icticket.user.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/performances/{performanceId}/comments")
public class CommentController {

    private final CommentService commentService;

    /*댓글 작성*/
    @PostMapping
    public ResponseEntity<ResponseMessageDto> createComment(
            @PathVariable Long performanceId,
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestBody CreateCommentRequestDto createCommentRequestDto) {
        User loginUser = userDetails.getUser();
        commentService.createComment(performanceId, createCommentRequestDto, loginUser);
        return ResponseEntity.ok(new ResponseMessageDto(SuccessStatus.COMMENT_CREATE_SUCCESS));
    }

    /*댓글 삭제*/
    @DeleteMapping("/{commentId}")
    public ResponseEntity<ResponseMessageDto> deleteComment(
            @PathVariable @Valid Long performanceId,
            @PathVariable @Valid Long commentId,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        User loginUser = userDetails.getUser();
        commentService.deleteComment(performanceId, commentId, loginUser);
        return ResponseEntity.ok(new ResponseMessageDto(SuccessStatus.COMMENT_DELETE_SUCCESS));
    }

    /* 단일 공연 댓글 조회*/
    @GetMapping
    public ResponseEntity<ResponseDataDto<List<GetCommentResponseDto>>> getComments(@PathVariable @Valid Long performanceId) {
        List<GetCommentResponseDto> commentsResponseDtoList = commentService.getComments(performanceId);
        return ResponseEntity.ok(new ResponseDataDto<>(SuccessStatus.COMMENT_GET_INFOS_SUCCESS, commentsResponseDtoList));
    }
}
