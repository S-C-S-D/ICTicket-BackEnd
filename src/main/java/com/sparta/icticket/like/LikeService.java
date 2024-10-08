package com.sparta.icticket.like;

import com.sparta.icticket.common.enums.ErrorType;
import com.sparta.icticket.common.exception.CustomException;
import com.sparta.icticket.like.dto.IsLikeResponseDto;
import com.sparta.icticket.performance.Performance;
import com.sparta.icticket.performance.PerformanceRepository;
import com.sparta.icticket.user.User;
import com.sparta.icticket.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class LikeService {

    private final LikeRepository likeRepository;
    private final PerformanceRepository performanceRepository;

    /**
     * 관심 공연 등록
     * @param performanceId
     * @param loginUser
     */
    public void createLike(Long performanceId, User loginUser) {

        Performance findPerformance = getPerformance(performanceId);

        likeRepository.findByUserAndPerformance(loginUser, findPerformance).ifPresent(l ->{
            throw new CustomException(ErrorType.ALREADY_LIKED_PERFORMANCE);});

        Like saveLike = new Like(loginUser, findPerformance);

        likeRepository.save(saveLike);
    }

    /**
     * 관심 공연 취소
     * @param performanceId
     * @param likeId
     * @param loginUser
     */
    @Transactional
    public void deleteLike(Long performanceId, Long likeId, User loginUser) {
        Performance findPerformance = getPerformance(performanceId);

        Like findLike = likeRepository.findByIdAndPerformanceAndUser(likeId, findPerformance, loginUser).orElseThrow(() ->
                new CustomException(ErrorType.NOT_LIKED_PERFORMANCE));

        likeRepository.delete(findLike);
    }

    /**
     * 관심 개수 조회
     * @param performanceId
     */
    public Long getLikesCount(Long performanceId) {
        Performance findPerformance = getPerformance(performanceId);

        return likeRepository.countByPerformance(findPerformance);
    }

    /**
     * 단일 공연 좋아요 여부
     * @param performanceId
     * @param loginUser
     */
    public IsLikeResponseDto getLike(Long performanceId, User loginUser) {
        Performance findPerformance = getPerformance(performanceId);
        Like like = likeRepository.findLikeIdByPerformanceIdAndUserId(performanceId, loginUser.getId()).orElse(null);
        IsLikeResponseDto isLikeResponseDto = new IsLikeResponseDto(like, likeRepository.findByUserAndPerformance(loginUser, findPerformance).isPresent());
        return isLikeResponseDto;
    }

    /**
     * 공연 조회
     * @param performanceId
     * @description 해당 id를 가진 performance 객체 조회
     */
    private Performance getPerformance(Long performanceId) {
        return performanceRepository.findById(performanceId).orElseThrow(() ->
                new CustomException(ErrorType.NOT_FOUND_PERFORMANCE));
    }

}
