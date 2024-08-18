package com.sparta.icticket.admin.service;

import com.sparta.icticket.common.exception.CustomException;
import com.sparta.icticket.venue.Venue;
import com.sparta.icticket.venue.VenueRepository;
import com.sparta.icticket.venue.dto.VenueRequestDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@Transactional // 각 테스트 메서드가 트랜잭션 내에서 실행됨
@Rollback // 기본값이 true이므로 명시적으로 설정할 필요는 없음
class AdminVenueServiceTest {

    // VenueRepository를 MockBean으로 설정
    @MockBean
    private VenueRepository venueRepository;

    // AdminVenueService 객체 생성
    private AdminVenueService adminVenueService;

    // 각 테스트 메서드 실행 전에 초기 설정
    @BeforeEach
    public void setup() {
        // VenueRepository를 이횽하여 AdminVenueService 초기화
        adminVenueService = new AdminVenueService(venueRepository);
    }

    // 공연장 생성 테스트
    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void createVenueTest() {
        // given, Mock 객체 생성
        VenueRequestDto mock = Mockito.mock(VenueRequestDto.class);

        // VenueRequestDto에서 반환할 값 설정
        when(mock.getVenueName()).thenReturn("Malty hall");
        when(mock.getLocation()).thenReturn("잠실 주경기장");
        when(mock.getTotalSeatCount()).thenReturn(250L);

        // when, createVenue 메서드 호출
        adminVenueService.createVenue(mock);

        // then, venueRepository의 save 메서드가 호출되었는지 검증
        Mockito.verify(venueRepository).save(Mockito.any(Venue.class));
    }

    // 공연장 수정 테스트
    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void updateVenueTest() {
        // given, Mock 객체 생성
        VenueRequestDto mock = Mockito.mock(VenueRequestDto.class);
        Long venueId = 1L;
        Venue mockVenue = Mockito.mock(Venue.class);

        // VenueRequestDto에서 반환할 값 설정
        when(mock.getVenueName()).thenReturn("Updated hall");
        when(mock.getLocation()).thenReturn("Updated location");
        when(mock.getTotalSeatCount()).thenReturn(300L);

        // venueRepository에서 ID로 공연장을 찾을 때 mockVenue를 반환하도록 설정
        when(venueRepository.findById(venueId)).thenReturn(Optional.of(mockVenue));

        // when, updateVenue 메서드 호출
        adminVenueService.updateVenue(venueId, mock);

        // then, mockVenue의 updateVenue 메서드가 호출되었는지 검증
        Mockito.verify(mockVenue).updateVenue(mock);
    }

    // 공연장 삭제 테스트
    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void deleteVenueTest() {
        // given, Venue의 Mock 객체 생성
        Long venueId = 1L;
        Venue mockVenue = Mockito.mock(Venue.class);

        // venueRepository에서 ID로 공연장을 찾을 때 mockVenue를 반환하도록 설정
        when(venueRepository.findById(venueId)).thenReturn(Optional.of(mockVenue));

        // when, deleteVenue 메서드 호출
        adminVenueService.deleteVenue(venueId);

        // then, venueRepository의 delete 메서드가 호출되었는지 검증
        Mockito.verify(venueRepository).delete(mockVenue);
    }

    // 공연장 삭제 테스트 - 공연장 없음
    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void deleteVenueNotFoundTest() {
        // given, 공연장 ID 설정
        Long venueId = 2L;

        // venueRepository에서 ID로 공연장을 찾을 때 빈 Optional 반환
        when(venueRepository.findById(venueId)).thenReturn(Optional.empty());

        // when then, 존재하지 않는 공연장을 삭제하려고 할 때 CustomException이 발생해야 함
        assertThrows(CustomException.class, () -> adminVenueService.deleteVenue(venueId));
    }
}