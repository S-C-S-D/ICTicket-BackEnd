package com.sparta.icticket.admin.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.sparta.icticket.admin.service.AdminSalesService;
import com.sparta.icticket.sales.dto.SalesAddRequestDto;
import com.sparta.icticket.sales.dto.SalesUpdateRequestDto;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.time.LocalDateTime;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@Transactional // 각 테스트 메서드가 트랜잭션 내에서 실행됨
@Rollback // 기본값이 true이므로 명시적으로 설정할 필요는 없음
class AdminSalesControllerTest {

    // MockMvc 객체 생성
    MockMvc mockMvc;

    // ObjectMapper 객체 생성, Java 객체를 JSON으로 변환
    private ObjectMapper objectMapper = new ObjectMapper();

    // AdminBannerService를 MockBean으로 주입하여 실제 동작을 수행하지 않도록 설정
    @MockBean
    private AdminSalesService adminSalesService;

    // WebApplicationContext의 wac 객체를 받아와서 의존성 주입
    @Autowired
    private WebApplicationContext wac;

    // 각 테스트 메서드 실행 전에 초기 설정
    @BeforeEach
    public void setup() {
        objectMapper.registerModule(new JavaTimeModule());

        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac)
                .addFilter(new CharacterEncodingFilter("UTF-8", true))
                .apply(SecurityMockMvcConfigurers.springSecurity())
                .build();
    }

    // 관리자 세일 생성 테스트 메서드
    @Test
    @WithMockUser(username = "user1", roles = "ADMIN")
    void addSalesTest() throws Exception {
        //given, SalesAddRequestDto의 Mock 객체 생성
        SalesAddRequestDto mock = Mockito.mock(SalesAddRequestDto.class);

        // given, 공연 ID 설정
        Long performanceId = 1L;

        // Mock 객체의 반환 값 설정
        when(mock.getStartAt()).thenReturn(LocalDateTime.now().minusDays(1));
        when(mock.getEndAt()).thenReturn(LocalDateTime.now().plusYears(1));
        when(mock.getDiscountRate()).thenReturn(30);

        //when then, contentType과 content를 설정 후 공연 Id를 받아 api로 호출 후 post로 요청하고 요청과 응답을 출력하며, 마지막으로 응답의 상태를 검증
        mockMvc.perform(post("/admin/performances/{performanceId}/sales", performanceId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(mock)))
                .andDo(print()) // 요청과 응답 내용 출력
                .andExpect(status().isOk()); // 응답 상태가 200 OK인지 확인
    }

    // 관리자 세일 수정 테스트 메서드
    @Test
    @WithMockUser(username = "user1", roles = "ADMIN")
    void updateSalesTest() throws Exception {
        // given, SalesUpdateRequestDto의 Mock 객체 생성
        SalesUpdateRequestDto mock = Mockito.mock(SalesUpdateRequestDto.class);

        // given, 공연 ID 및 세일 ID 설정
        Long performanceId = 1L;
        Long salesId = 1L;

        // Mock 객체의 반환 값 설정
        when(mock.getStartAt()).thenReturn(LocalDateTime.now().minusDays(1));
        when(mock.getEndAt()).thenReturn(LocalDateTime.now().plusYears(1));
        when(mock.getDiscountRate()).thenReturn(20);

        // when then, contentType와 content에 값을 넣어 설정하고 Id를 통해 API를 호출하여 patch 요청
        mockMvc.perform(patch("/admin/performances/{performanceId}/sales/{salesId}", performanceId, salesId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(mock)))
                .andDo(print()) // 요청과 응답 내용 출력
                .andExpect(status().isOk()); // 응답 상태가 200 OK인지 확인
    }

    // 관리자 세일 삭제 테스트 메서드
    @Test
    @WithMockUser(username = "user1", roles = "ADMIN")
    void deleteSalesTest() throws Exception {
        // given, 공연 ID 및 세일 ID 설정
        Long performanceId = 1L;
        Long salesId = 1L;

        // adminSalesService의 deleteSales 메서드가 performanceId와 salesId를 받아 호출할 때 아무 동작도 하지 않도록 설정
        doNothing().when(adminSalesService).deleteSales(performanceId, salesId);

        // when then: DELETE 요청을 통해 performanceId와 salesId에 해당하는 객체 삭제 및 응답 상태 확인
        mockMvc.perform(delete("/admin/performances/{performanceId}/sales/{salesId}", performanceId, salesId))
                .andDo(print()) // 요청과 응답 내용 출력
                .andExpect(status().isOk()); // 응답 상태가 200 OK인지 확인
    }
}