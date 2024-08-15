package com.sparta.icticket.like;


import com.sparta.icticket.performance.Performance;
import com.sparta.icticket.performance.PerformanceRepository;
import com.sparta.icticket.user.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LikeServiceTest {
    @Mock
    private LikeRepository likeRepository;

    @Mock
    private PerformanceRepository performanceRepository;

    @InjectMocks
    private LikeService likeService;

    @Test
    void createLike() {

    }

    @Test
    void deleteLike() {

    }

    @Test
    void getLikesCount() {
        //given
        Long performanceId =1L;

        Performance performanceMock = mock(Performance.class);
        when(performanceRepository.findById(performanceId)).thenReturn(Optional.of(performanceMock));

        Long likesCount=10L;
        when(likeRepository.countByPerformance(performanceMock)).thenReturn(likesCount);
        //when
        Long result=likeService.getLikesCount(performanceId);
        //then
        verify(likeRepository).countByPerformance(performanceMock);
        assertEquals(likesCount, result);
    }

    @Test
    void getLike() {
        //given

        //when

        //then
    }
}