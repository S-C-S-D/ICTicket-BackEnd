package com.sparta.icticket.session;

import com.sparta.icticket.common.enums.ErrorType;
import com.sparta.icticket.common.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SessionService {

    private final SessionRepository sessionRepository;

    /**
     * 세션 조회
     * @param performanceId
     * @return
     */
    @Transactional(readOnly = true)
    public List<GetSessionsResponseDto> getSessions(Long performanceId) {
        return findSession(performanceId).stream().map(GetSessionsResponseDto::new).toList();
    }

    /*메서드*/

    /**
     * 세션 찾기
     * @param performanceId
     * @description 세션 조회시 url의 performanceId을 통해 세션을 찾고 sort에 있는 요소들 순으로 정렬해서 세션 List를 반환한다
     */
    private List<Session> findSession(Long performanceId) {
        Sort sessionSort = getSort();
        return sessionRepository.findByPerformanceId(performanceId,sessionSort)
                .orElseThrow(() -> new CustomException(ErrorType.NOT_FOUND_PERFORMANCE));
    }

    /**
     * 세션 정렬 요소
     * @description 세션 조회시 정렬요소들을 나열하였다. sessionDate,sessionTime 순으로 세션이 정렬된다
     */
    private Sort getSort() {
        return Sort.by(
                Sort.Order.asc("sessionDate"),
                Sort.Order.asc("sessionTime")
        );
    }


}
