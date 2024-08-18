package com.sparta.icticket.admin.service;

import com.sparta.icticket.common.exception.CustomException;
import com.sparta.icticket.performance.Performance;
import com.sparta.icticket.performance.PerformanceRepository;
import com.sparta.icticket.sales.Sales;
import com.sparta.icticket.sales.SalesRepository;
import com.sparta.icticket.sales.dto.SalesAddRequestDto;
import com.sparta.icticket.sales.dto.SalesUpdateRequestDto;
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

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@Transactional // 각 테스트 메서드가 트랜잭션 내에서 실행됨
@Rollback // 기본값이 true이므로 명시적으로 설정할 필요는 없음

class AdminSalesServiceTest {

    // SalesRepository를 MockBean으로 주입
    @MockBean
    private SalesRepository salesRepository;

    // PerformanceRepository를 MockBean으로 주입
    @MockBean
    private PerformanceRepository performanceRepository;

    // AdminSalesService 객체 생성
    private AdminSalesService adminSalesService;

    // 각 테스트 메서드 실행 전에 초기 설정
    @BeforeEach
    public void setup() {
        // SalesRepository와 PerformanceRepository를 이용해 AdminSalesService 초기화
        adminSalesService = new AdminSalesService(salesRepository, performanceRepository);
    }

    // 할인 적용 테스트, 이미 해당 공연에 할인이 존재할 때 예외 발생 여부 검증
    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void addSalesTest() {
        //given, SalesAddRequestDto에 Mock 객체 생성
        SalesAddRequestDto mock = Mockito.mock(SalesAddRequestDto.class);
        Performance mockPerformance = Mockito.mock(Performance.class);

        // given: ID 설정
        Long performanceId = 1L;

        // Mock 객체의 반환 값 설정
        // performanceRepository에서 ID로 공연을 찾을 때 mockPerformance를 반환하도록 설정
        when(performanceRepository.findById(performanceId)).thenReturn(Optional.of(mockPerformance));
        // salesRepository에서 mockPerformance에 대한 할인 정보가 존재한다고 설정
        when(salesRepository.existsByPerformance(mockPerformance)).thenReturn(true);

        when(mock.getStartAt()).thenReturn(LocalDateTime.now().minusDays(1));
        when(mock.getEndAt()).thenReturn(LocalDateTime.now().plusYears(1));
        when(mock.getDiscountRate()).thenReturn(30);

        // when then, 이미 해당 공연에 할인이 존재하는 상황에서 addSales 메서드를 호출하면 CustomException이 발생해야 함
        // 중복 할인 추가를 방지하기 위한 테스트
        assertThrows(CustomException.class, () -> adminSalesService.addSales(performanceId, mock));

    }

    // 할인 수정 테스트, 존재하지 않는 할인 정보를 수정하려고 할 때 예외 발생 여부 검증
    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void updateSalesTest() {
        // given, Mock 객체 생성
        SalesUpdateRequestDto mock = Mockito.mock(SalesUpdateRequestDto.class);
        Performance mockPerformance = Mockito.mock(Performance.class);
        Sales mockSales = Mockito.mock(Sales.class);

        // given: ID 설정
        Long performanceId = 2L;
        Long salesId = 8L;

        // Mock 객체의 반환 값 설정
        // performanceRepository에서 ID 2로 공연을 찾을 때 mockPerformance를 반환하도록 설정
        when(performanceRepository.findById(performanceId)).thenReturn(Optional.of(mockPerformance));
        // salesRepository에서 ID 8로 할인 정보를 찾을 때 mockSales를 반환하도록 설정
        // 또한 해당 할인 정보는 mockPerformance와 연결되어야 함
        when(salesRepository.findByIdAndPerformance(salesId, mockPerformance)).thenReturn(Optional.of(mockSales));

        when(mock.getStartAt()).thenReturn(LocalDateTime.now().minusDays(1));
        when(mock.getEndAt()).thenReturn(LocalDateTime.now().plusYears(1));
        when(mock.getDiscountRate()).thenReturn(20);

        // when then, 잘못된 날짜 형식으로 인해 `CustomException`이 발생해야 하는지 검증
        assertThrows(CustomException.class, () -> adminSalesService.updateSales(performanceId, salesId, mock));
    }

    // 할인 삭제 테스트, 존재하지 않는 할인을 삭제하려고 할 때 예외 발생 여부 검증
    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void deleteSalesTest() {
        // given, Mock 객체 생성
        Performance mockPerformance = Mockito.mock(Performance.class);

        // given: ID 설정
        Long performanceId = 1L;
        Long salesId = 8L;

        // Mock 객체의 반환 값 설정
        // performanceRepository에서 ID 1로 공연을 찾을 때 mockPerformance를 반환하도록 설정
        when(performanceRepository.findById(performanceId)).thenReturn(Optional.of(mockPerformance));
        // salesRepository에서 ID 8로 할인 정보를 찾으려 할 때 할인 정보가 존재하지 않도록 설정
        // 따라서 Optional.empty()를 반환하도록 설정
        when(salesRepository.findByIdAndPerformance(salesId, mockPerformance)).thenReturn(Optional.empty());

        // when then, 존재하지 않는 할인을 삭제하려고 할 때 deleteSales 메서드를 호출 CustomException이 발생해야 함
        // 삭제할 할인 정보가 존재하지 않음을 처리하기 위한 테스트
        assertThrows(CustomException.class, () -> adminSalesService.deleteSales(performanceId, salesId));
    }
}