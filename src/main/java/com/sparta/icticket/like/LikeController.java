package com.sparta.icticket.like;


import com.sparta.icticket.common.dto.ResponseDataDto;
import com.sparta.icticket.common.dto.ResponseMessageDto;
import com.sparta.icticket.common.enums.SuccessStatus;
import com.sparta.icticket.common.security.UserDetailsImpl;
import com.sparta.icticket.like.dto.IsLikeResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/performances/{performanceId}")
public class LikeController {

    private final LikeService likeService;

    /**
     * 관심 공연 등록
     * @param performanceId
     * @param userDetails
     */
    @PostMapping("/likes")
    public ResponseEntity<ResponseMessageDto> createLike(
            @PathVariable Long performanceId,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        likeService.createLike(performanceId, userDetails.getUser());
        return ResponseEntity.ok(new ResponseMessageDto(SuccessStatus.LIKE_ADD_SUCCESS));
    }

    /**
     * 관심 공연 취소
     * @param performanceId
     * @param userDetails
     */
    @DeleteMapping("/likes/{likesId}")
    public ResponseEntity<ResponseMessageDto> deleteLike(
            @PathVariable Long performanceId,
            @PathVariable Long likesId,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        likeService.deleteLike(performanceId, likesId, userDetails.getUser());
        return ResponseEntity.ok(new ResponseMessageDto(SuccessStatus.LIKE_UNLIKE_SUCCESS));
    }

    /**
     * 관심 개수 조회
     * @param performanceId
     */
    @GetMapping("/likes-count")
    public ResponseEntity<ResponseDataDto<Long>> getLikesCount(
            @PathVariable Long performanceId) {
        Long likeCount = likeService.getLikesCount(performanceId);
        return ResponseEntity.ok(new ResponseDataDto<>(SuccessStatus.LIKE_GET_COUNT_SUCCESS, likeCount));
    }

    /**
     * 단일 공연 좋아요 여부
     * @param performanceId
     * @param userDetails
     */
    @GetMapping("/likes")
    public ResponseEntity<ResponseDataDto<IsLikeResponseDto>> getLike(
            @PathVariable Long performanceId,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        IsLikeResponseDto isLikeResponseDto = likeService.getLike(performanceId, userDetails.getUser());
        return ResponseEntity.ok(new ResponseDataDto<>(SuccessStatus.LIKE_GET_ISLIKED_SUCCESS, isLikeResponseDto));
    }

}
