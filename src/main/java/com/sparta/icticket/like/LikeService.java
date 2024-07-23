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
        User findUser = findUserByEmail(loginUser.getEmail());

        Performance findPerformance = findPerformanceById(performanceId);

        Like saveLike = new Like(findUser, findPerformance);

        likeRepository.save(saveLike);
    }

    /**
     * 관심 공연 등록 취소
     * @param performanceId
     * @param loginUser
     */
    public void deleteLike(Long performanceId, User loginUser) {
        User findUser = findUserByEmail(loginUser.getEmail());
        Performance findPerformance = findPerformanceById(performanceId);

        Like findLke = likeRepository.findByUserAndPerformance(findUser, findPerformance).orElseThrow(() ->
                new CustomException(ErrorType.ALREADY_LIKED_PERFORMANCE));

        likeRepository.delete(findLke);
    }

    /**
     * 유저 존재 여부 확인
     * @param email
     * @return
     */
    private User findUserByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() ->
                new CustomException(ErrorType.NOT_FOUND_USER));
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
