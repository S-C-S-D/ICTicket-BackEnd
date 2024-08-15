package com.sparta.icticket.admin.service;

import com.sparta.icticket.banner.Banner;
import com.sparta.icticket.banner.BannerRepository;
import com.sparta.icticket.banner.dto.BannerRequestDto;
import com.sparta.icticket.common.enums.BannerType;
import com.sparta.icticket.common.exception.CustomException;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@Transactional // 각 테스트 메서드가 트랜잭션 내에서 실행됨
@Rollback // 기본값이 true이므로 명시적으로 설정할 필요는 없음
class AdminBannerServiceTest {

    // BannerRepository를 MockBean으로 주입
    @MockBean
    private BannerRepository bannerRepository;

    // AdminBannerService 객체를 생성 (생성자 주입 방식 사용)
    private AdminBannerService adminBannerService;

    // 각 테스트 메서드 실행 전에 초기 설정
    @BeforeEach
    public void setup() {
        // BannerRepository를 이용해 AdminBannerService 초기화
        adminBannerService = new AdminBannerService(bannerRepository);
    }

    // 배너 생성, 이미 존재하는 위치에 배너를 생성하려고 할 때 예외가 발생하는지 검증
    @Test
    @WithMockUser(username = "user1", roles = "ADMIN")
    void createBannersTest() {
        //given, BannerRequestDto의 Mock 객체 설정
        BannerRequestDto mock = Mockito.mock(BannerRequestDto.class);

        // Mock 객체의 반환 값 설정
        when(mock.getPosition()).thenReturn(13);
        when(mock.getLinkUrl()).thenReturn("https://github.com/S-C-S-D/ICTicket");
        when(mock.getBannerType()).thenReturn(BannerType.MIDDLE);
        when(mock.getBannerImageUrl()).thenReturn("https://github.com/S-C-S-D/ICTicket");
        when(mock.getStartAt()).thenReturn(LocalDateTime.now().minusDays(1));
        when(mock.getEndAt()).thenReturn(LocalDateTime.now().plusYears(1));

        // MockBannerRepository의 findByPosition 메서드가 특정 위치에 이미 배너가 존재한다고 가정
        // 이미 위치 13에 배너가 존재한다고 가정하여 Optional.of(new Banner())을 반환
        when(bannerRepository.findByPosition(13)).thenReturn(Optional.of(new Banner()));

        // when then, 이미 존재하는 위치에 배너를 생성하려고 할 때 CustomException이 발생해야 함
        // AdminBannerService의 createBanners 메서드를 호출할 때 예외가 발생해야 함을 검증
        AdminBannerService adminBannerService = new AdminBannerService(bannerRepository);
        assertThrows(CustomException.class, () -> adminBannerService.createBanners(mock));
    }

    // 배너 삭제, 존재하지 않는 배너를 삭제하려고 할 때 예외가 발생하는지 검증
    @Test
    @WithMockUser(username = "user1", roles = "ADMIN")
    void deleteBannerTest(){
        // given, 삭제할 배너 ID 설정
        Long bannerId = 1L;
        // findById 메서드가 빈 Optional을 반환하도록 설정하여 배너가 존재하지 않는 상황을 가정
        when(bannerRepository.findById(bannerId)).thenReturn(Optional.empty());

        // when then, 존재하지 않는 배너를 삭제하려고 할 때 CustomException이 발생해야 함
        // AdminBannerService의 deleteBanner 메서드를 bannerId로 호출할 때 예외가 발생해야 함을 검증
        assertThrows(CustomException.class, () -> adminBannerService.deleteBanner(bannerId));
    }
}