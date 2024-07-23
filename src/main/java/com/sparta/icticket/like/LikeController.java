package com.sparta.icticket.like;


import com.sparta.icticket.common.dto.ResponseMessageDto;
import com.sparta.icticket.common.enums.SuccessStatus;
import com.sparta.icticket.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/performances")
public class LikeController {

    private final LikeService likeService;

    /**
     * 관심 공연 등록 기능
     * @param performanceId
     * @param userDetails
     * @return
     */
    @PostMapping("/{performanceId}/likes")
    public ResponseEntity<ResponseMessageDto> createLike(
            @PathVariable Long performanceId,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        likeService.createLike(performanceId, userDetails.getUser());
        return ResponseEntity.ok(new ResponseMessageDto(SuccessStatus.LIKE_ADD_SUCCESS));
    }

    /**
     * 관심 공연 등록 취소 기능
     * @param performanceId
     * @param userDetails
     * @return
     */
    @DeleteMapping("/{performanceId}/likes")
    public ResponseEntity<ResponseMessageDto> deleteLike(
            @PathVariable Long performanceId,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        likeService.deleteLike(performanceId, userDetails.getUser());
        return ResponseEntity.ok(new ResponseMessageDto(SuccessStatus.LIKE_UNLIKE_SUCCESS));
    }
}
