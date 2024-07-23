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

        Performance findPerformance = performanceRepository.findById(performanceId).orElseThrow(() ->
                new CustomException(ErrorType.NOT_FOUND_PERFORMANCE));

        Like saveLike = new Like(findUser, findPerformance);

        likeRepository.save(saveLike);

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
}
