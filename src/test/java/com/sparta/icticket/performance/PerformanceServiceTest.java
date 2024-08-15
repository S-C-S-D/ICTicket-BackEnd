package com.sparta.icticket.performance;

import com.sparta.icticket.common.enums.ErrorType;
import com.sparta.icticket.common.exception.CustomException;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@Transactional
@Rollback
class PerformanceServiceTest {

    @InjectMocks
    private PerformanceService performanceService;

    @Mock
    private PerformanceRepository performanceRepository;

    // 공연 검증 예외 테스트
    @Test
    void getPerformance() {

        // when-then
        CustomException exception = assertThrows(CustomException.class, () -> {
            performanceService.getPerformance(1L);
        });

        assertEquals(ErrorType.NOT_FOUND_PERFORMANCE, exception.getErrorType());
    }

    // 변경 사항이 DB에 저장되는지 테스트
    @Test
    void getPerformanceSave() {

        // given
        Performance performance = Mockito.mock(Performance.class);
        when(performanceRepository.findById(any(Long.class))).thenReturn(Optional.of(performance));

        // when-then
        performanceService.getPerformance(1L);

        verify(performanceRepository, times(1)).save(performance);

    }
}