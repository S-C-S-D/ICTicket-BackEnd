package com.sparta.icticket.admin.service;

import com.sparta.icticket.common.enums.ErrorType;
import com.sparta.icticket.common.exception.CustomException;
import com.sparta.icticket.performance.Performance;
import com.sparta.icticket.performance.PerformanceRepository;
import com.sparta.icticket.performance.dto.PerformanceRequestDto;
import com.sparta.icticket.venue.Venue;
import com.sparta.icticket.venue.VenueRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AdminPerformanceService {
    private final PerformanceRepository performanceRepository;
    private final VenueRepository venueRepository;

    /**
     * 공연 등록
     * @param requestDto
     */
    public void createPerformance(PerformanceRequestDto requestDto) {
        // 공연장 존재 확인
        Venue venue = venueRepository.findById(requestDto.getVenueId()).orElseThrow(
                () -> new CustomException(ErrorType.NOT_FOUND_VENUE));

        this.checkDateTime(requestDto);

        Performance performance = new Performance(requestDto, venue);
        performanceRepository.save(performance);
    }

    /**
     * 공연 수정
     * @param performanceId
     * @param requestDto
     */
    @Transactional
    public void updatePerformance(Long performanceId, PerformanceRequestDto requestDto) {
        Performance performance = findPerformanceById(performanceId);

        // 공연장 존재 확인
        Venue venue = venueRepository.findById(requestDto.getVenueId()).orElseThrow(
                () -> new CustomException(ErrorType.NOT_FOUND_VENUE));

        this.checkDateTime(requestDto);

        performance.update(requestDto, venue);
    }

    /**
     * 공연 삭제
     * @param performanceId
     */
    public void deletePerformance(Long performanceId) {
        Performance performance = findPerformanceById(performanceId);

        performanceRepository.delete(performance);
    }


    private void checkDateTime(PerformanceRequestDto requestDto){
        // TODO: 날짜의 포멧이 다른 경우에는 필터단계에서 걸러버리기 떄문에 예외처리 따로 봐야함.
        LocalDateTime now = LocalDateTime.now();

        // 오픈 일이 현재보다 과거인 경우
        if(requestDto.getOpenAt().isBefore(now)){
            throw new CustomException(ErrorType.NOT_AVAILABLE_DATE);
        }

        // 공연 시작일이 현재보다 과거인 경우
        if(requestDto.getStartAt().isBefore(now.toLocalDate())){
            throw new CustomException(ErrorType.NOT_AVAILABLE_DATE);
        }

        // 공연 종료일이 시작일보다 과거인 경우
        if(requestDto.getEndAt().isBefore(requestDto.getStartAt())){
            throw new CustomException(ErrorType.END_AT_PASSED_START_AT);
        }
    }

    private Performance findPerformanceById(Long performanceId){
        return performanceRepository.findById(performanceId).orElseThrow(
                () -> new CustomException(ErrorType.NOT_FOUND_PERFORMANCE));
    }
}
