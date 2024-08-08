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

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AdminPerformanceService {
    private final PerformanceRepository performanceRepository;
    private final VenueRepository venueRepository;

    /**
     * 공연 등록
     * @param requestDto 공연 등록에 필요한 정보
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
     * @param performanceId 공연 id
     * @param requestDto 공연 수정에 필요한 정보
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
     * @param performanceId 공연 id
     */
    public void deletePerformance(Long performanceId) {
        Performance performance = findPerformanceById(performanceId);

        performanceRepository.delete(performance);
    }

    /**
     * 날짜, 시간 확인
     * @param requestDto 공연 정보
     * @description 공연 등록 및 수정시 날짜 및 시간이 유효한지 확인
     */
    private void checkDateTime(PerformanceRequestDto requestDto){
        Timestamp now = new Timestamp(System.currentTimeMillis());

        // 오픈 일이 현재보다 과거인 경우
        if(requestDto.getOpenAt().before(now)){
            throw new CustomException(ErrorType.NOT_AVAILABLE_DATE);
        }

        // 공연 시작일이 현재보다 과거인 경우
        if(requestDto.getStartAt().before(now)){
            throw new CustomException(ErrorType.NOT_AVAILABLE_DATE);
        }

        // 공연 종료일이 시작일보다 과거인 경우
        if(requestDto.getEndAt().before(requestDto.getStartAt())){
            throw new CustomException(ErrorType.END_AT_PASSED_START_AT);
        }
    }

    /**
     * 공연 id로 조회
     * @param performanceId 공연 id
     * @description 해당 공연이 존재하지 않으면 예외처리, 존재하면 반환
     */
    private Performance findPerformanceById(Long performanceId){
        return performanceRepository.findById(performanceId).orElseThrow(
                () -> new CustomException(ErrorType.NOT_FOUND_PERFORMANCE));
    }
}
