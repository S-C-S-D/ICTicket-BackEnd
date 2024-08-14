package com.sparta.icticket.admin.service;

import com.sparta.icticket.common.enums.ErrorType;
import com.sparta.icticket.common.exception.CustomException;
import com.sparta.icticket.performance.Performance;
import com.sparta.icticket.performance.PerformanceRepository;
import com.sparta.icticket.performance.dto.PerformanceRequestDto;
import com.sparta.icticket.venue.Venue;
import com.sparta.icticket.venue.VenueRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@Transactional
@Rollback
class AdminPerformanceServiceTest {

    @InjectMocks
    private AdminPerformanceService adminPerformanceService;

    @Mock
    private PerformanceRepository performanceRepository;

    @Mock
    private VenueRepository venueRepository;

    // 공연장 검증 예외 테스트
    @Test
    void createPerformance_findVenue() {

        // given
        PerformanceRequestDto requestDto = Mockito.mock(PerformanceRequestDto.class);

        when(requestDto.getVenueId()).thenReturn(1L);
        CustomException exception = assertThrows(CustomException.class, () -> {
            adminPerformanceService.createPerformance(requestDto);
        });

        // when-then
        assertEquals(ErrorType.NOT_FOUND_VENUE, exception.getErrorType());
    }

    // openAt 예외 테스트
    @Test
    void createPerformance_checkOpenAt() {

        // given
        PerformanceRequestDto requestDto = Mockito.mock(PerformanceRequestDto.class);
        when(requestDto.getVenueId()).thenReturn(1L);

        Venue venue = Mockito.mock(Venue.class);
        when(venueRepository.findById(any(Long.class))).thenReturn(Optional.of(venue));

        when(requestDto.getOpenAt()).thenReturn(LocalDateTime.now().minusDays(1));

        CustomException exception = assertThrows(CustomException.class, () -> {
            adminPerformanceService.createPerformance(requestDto);
        });

        // when-then
        assertEquals(ErrorType.NOT_AVAILABLE_DATE, exception.getErrorType());
    }

    // StartAt 예외 테스트
    @Test
    void createPerformance_checkStartAt() {

        // given
        PerformanceRequestDto requestDto = Mockito.mock(PerformanceRequestDto.class);
        when(requestDto.getVenueId()).thenReturn(1L);

        Venue venue = Mockito.mock(Venue.class);
        when(venueRepository.findById(any(Long.class))).thenReturn(Optional.of(venue));

        when(requestDto.getOpenAt()).thenReturn(LocalDateTime.now().plusDays(1));
        when(requestDto.getStartAt()).thenReturn(LocalDate.now().minusDays(1));

        CustomException exception = assertThrows(CustomException.class, () -> {
            adminPerformanceService.createPerformance(requestDto);
        });

        // when-then
        assertEquals(ErrorType.NOT_AVAILABLE_DATE, exception.getErrorType());
    }

    // EndAt 예외 테스트
    @Test
    void createPerformance_checkEndAt() {

        // given
        PerformanceRequestDto requestDto = Mockito.mock(PerformanceRequestDto.class);

        Venue venue = Mockito.mock(Venue.class);
        when(venueRepository.findById(any(Long.class))).thenReturn(Optional.of(venue));

        when(requestDto.getOpenAt()).thenReturn(LocalDateTime.now().plusDays(1));
        when(requestDto.getStartAt()).thenReturn(LocalDate.now().plusDays(2));
        when(requestDto.getEndAt()).thenReturn(LocalDate.now().minusDays(1));
        CustomException exception = assertThrows(CustomException.class, () -> {
            adminPerformanceService.createPerformance(requestDto);
        });

        // when-then
        assertEquals(ErrorType.END_AT_PASSED_START_AT, exception.getErrorType());
    }

    // performance 삭제 테스트
    @Test
    void deletePerformance() {

        // given
        Performance performance = Mockito.mock(Performance.class);

        when(performanceRepository.findById(any(Long.class))).thenReturn(Optional.of(performance));

        adminPerformanceService.deletePerformance(1L);

        // when-then
        verify(performanceRepository, times(1)).delete(performance);
    }
}