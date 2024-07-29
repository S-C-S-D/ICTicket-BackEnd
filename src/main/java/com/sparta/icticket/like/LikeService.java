package com.sparta.icticket.like;

import com.sparta.icticket.common.enums.ErrorType;
import com.sparta.icticket.common.exception.CustomException;
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
    private final UserRepository userRepository;
    private final PerformanceRepository performanceRepository;

    /**
     * 관심 공연 등록
     * @param performanceId
     * @param loginUser
     */
    public void createLike(Long performanceId, User loginUser) {

        Performance findPerformance = findPerformanceById(performanceId);

        likeRepository.findByUserAndPerformance(loginUser, findPerformance).ifPresent(l ->{
            throw new CustomException(ErrorType.ALREADY_LIKED_PERFORMANCE);});

        Like saveLike = new Like(loginUser, findPerformance);

        likeRepository.save(saveLike);
    }

    /**
     * 관심 공연 등록 삭제
     * @param performanceId
     * @param likeId
     * @param loginUser
     */
    @Transactional
    public void deleteLike(Long performanceId, Long likeId, User loginUser) {
        Performance findPerformance = findPerformanceById(performanceId);

        Like findLike = likeRepository.findByIdAndPerformanceAndUser(likeId, findPerformance, loginUser).orElseThrow(() ->
                new CustomException(ErrorType.NOT_LIKED_PERFORMANCE));

        likeRepository.delete(findLike);
    }

    /**
     * 관심 개수 조회
     * @param performanceId
     * @return
     */
    public Long getLikesCount(Long performanceId) {
        Performance findPerformance = findPerformanceById(performanceId);

        return likeRepository.countByPerformance(findPerformance);
    }

    /**
     * 관심 공연 등록 여부 조회
     * @param performanceId
     * @param loginUser
     * @return
     */
    public boolean getLike(Long performanceId, User loginUser) {
        Performance findPerformance = findPerformanceById(performanceId);

        return likeRepository.findByUserAndPerformance(loginUser, findPerformance).isPresent();
    }

    /**
     * 공연 존재 여부 확인
     * @param performanceId
     * @return
     */
    private Performance findPerformanceById(Long performanceId) {
        return performanceRepository.findById(performanceId).orElseThrow(() ->
                new CustomException(ErrorType.NOT_FOUND_PERFORMANCE));
    }

}
