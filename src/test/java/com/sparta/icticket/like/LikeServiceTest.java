package com.sparta.icticket.like;


import com.sparta.icticket.performance.Performance;
import com.sparta.icticket.performance.PerformanceRepository;
import com.sparta.icticket.user.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
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
        //given
        Long performanceId =1L;

        User loginUser = mock(User.class);

        Performance performanceMock = mock(Performance.class);
        when(performanceMock.getId()).thenReturn(performanceId);

        when(performanceRepository.findById(performanceId)).thenReturn(Optional.of(performanceMock));

        when(likeRepository.findByUserAndPerformance(loginUser, performanceMock)).thenReturn(Optional.empty());
        //when
        likeService.createLike(performanceId, loginUser);
        //then
        ArgumentCaptor<Like> captor = ArgumentCaptor.forClass(Like.class);
        verify(likeRepository).save(captor.capture());
        Like savedLike = captor.getValue();

        assertEquals(loginUser.getId(), savedLike.getUser().getId());
        assertEquals(performanceMock.getId(), savedLike.getPerformance().getId());
    }

    @Test
    void deleteLike() {
        //given

        Long performanceId=1L;
        Long likeId=2L;

        User loginUserMock = mock(User.class);
        Long userId =3L;
        when(loginUserMock.getId()).thenReturn(userId);

        Performance performanceMock = mock(Performance.class);
        when(performanceMock.getId()).thenReturn(performanceId);

        when(performanceRepository.findById(performanceId)).thenReturn(Optional.of(performanceMock));

        Like likeMock = mock(Like.class);
        when(likeMock.getPerformance()).thenReturn(performanceMock);
        when(likeMock.getId()).thenReturn(likeId);
        when(likeMock.getUser()).thenReturn(loginUserMock);

        when(likeRepository.findByIdAndPerformanceAndUser(likeId, performanceMock, loginUserMock)).thenReturn(Optional.of(likeMock));
        //when
        likeService.deleteLike(performanceId, likeId, loginUserMock);
        //then
        ArgumentCaptor<Like> captor = ArgumentCaptor.forClass(Like.class);
        verify(likeRepository).delete(captor.capture());
        Like deletedLike = captor.getValue();
        assertEquals(performanceId, deletedLike.getPerformance().getId());
        assertEquals(likeId, deletedLike.getId());
        assertEquals(userId,deletedLike.getUser().getId());
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