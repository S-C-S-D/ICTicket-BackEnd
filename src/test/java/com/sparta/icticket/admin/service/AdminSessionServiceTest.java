package com.sparta.icticket.admin.service;


import com.sparta.icticket.performance.Performance;
import com.sparta.icticket.performance.PerformanceRepository;
import com.sparta.icticket.session.Session;
import com.sparta.icticket.session.SessionRepository;
import com.sparta.icticket.session.dto.CreateSessionRequestDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
@SpringBootTest
class AdminSessionServiceTest {

    @Mock
    SessionRepository sessionRepository;

    @Mock
    PerformanceRepository performanceRepository;

    @InjectMocks
    AdminSessionService adminSessionService;

    @Test
    void createSession() {
        //given
        Long performanceId = 1L;

        CreateSessionRequestDto mockRequestDto = mock(CreateSessionRequestDto.class);
        LocalDate date = LocalDate.now();
        LocalTime time = LocalTime.now();
        when(mockRequestDto.getDate()).thenReturn(date);
        when(mockRequestDto.getName()).thenReturn("A");
        when(mockRequestDto.getTime()).thenReturn(time);

        Performance performance = Mockito.mock(Performance.class);
        when(performance.getId()).thenReturn(1L);
        when(performance.getStartAt()).thenReturn(LocalDate.now().minusDays(1));
        when(performance.getEndAt()).thenReturn(LocalDate.now().plusDays(1));

        when(performanceRepository.findById(performanceId)).thenReturn(Optional.of(performance));
//        doNothing().when(any(Session.class)).checkDate(any(LocalDate.class));

        //when
        adminSessionService.createSession(performanceId,mockRequestDto);
        //then
        ArgumentCaptor<Session> captor = ArgumentCaptor.forClass(Session.class);
        verify(sessionRepository).save(captor.capture());
        Session savedSession = captor.getValue();
        assertEquals(mockRequestDto.getDate(),savedSession.getSessionDate());
        assertEquals(mockRequestDto.getName(),savedSession.getSessionName());
        assertEquals(mockRequestDto.getTime(),savedSession.getSessionTime());
        assertEquals(1L,savedSession.getPerformance().getId());
    }

    @Test
    void updateSession() {
    }

    @Test
    void deleteSession() {
    }
}