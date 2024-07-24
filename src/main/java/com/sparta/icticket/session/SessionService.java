package com.sparta.icticket.session;

import com.sparta.icticket.common.enums.ErrorType;
import com.sparta.icticket.common.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SessionService {

    private final SessionRepository sessionRepository;

    public List<GetSessionsResponseDto> getSessions(Long performanceId) {
        return validateSession(performanceId).stream().map(GetSessionsResponseDto::new).toList();
    }

    /*메서드*/
    private List<Session> validateSession(Long performanceId) {
        Sort sessionSort = getSort();
        return sessionRepository.findByPerformanceId(performanceId,sessionSort)
                .orElseThrow(() -> new CustomException(ErrorType.NOT_FOUND_PERFORMANCE));
    }

    private Sort getSort() {
        return Sort.by(
                Sort.Order.asc("sessionDate"),
                Sort.Order.asc("sessionTime")
        );
    }


}
