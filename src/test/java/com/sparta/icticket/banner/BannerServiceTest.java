package com.sparta.icticket.banner;

import com.sparta.icticket.banner.dto.BannerResponseDto;
import com.sparta.icticket.common.enums.BannerType;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@Transactional // 각 테스트 메서드가 트랜잭션 내에서 실행됨
@Rollback // 기본값이 true이므로 명시적으로 설정할 필요는 없음
class BannerServiceTest {

    // BannerRepository를 MockBean으로 설정
    @Mock
    private BannerRepository bannerRepository;

    // BannerService 객체 생성
    @InjectMocks
    private BannerService bannerService;

    // 각 테스트 메서드 실행 전에 초기 설정
    @BeforeEach
    public void setup() {
        // BannerRepository를 이용해 BannerService 초기화
        bannerService = new BannerService(bannerRepository);
    }

    // 배너 타입별 조회 테스트
    @Test
    void getBannersByTypeTest() {
        // given, Banner 타입 및 Mock 객체 설정
        BannerType bannerType = BannerType.MAIN;

        // Mock Banner 객체 생성
        Banner banner1 = Mockito.mock(Banner.class);
        Banner banner2 = Mockito.mock(Banner.class);
        List<Banner> mockBannerList = Arrays.asList(banner1, banner2);

        // bannerRepository에서 배너 타입으로 조회 시 Mock 배너 리스트를 반환하도록 설정
        when(bannerRepository.findTop10Banner(bannerType)).thenReturn(mockBannerList);

        // when, 배너 타입으로 배너 조회 메서드 호출
        List<BannerResponseDto> result = bannerService.getBannersByType(bannerType);

        // then, 반환된 결과의 크기와 내용 검증
        assertNotNull(result);
        assertEquals(2, result.size()); // Mock으로 설정한 배너 리스트의 크기 확인
        assertTrue(result.stream().allMatch(dto -> dto instanceof BannerResponseDto)); // 반환된 객체가 BannerResponseDto 인스턴스인지 확인
    }
}
