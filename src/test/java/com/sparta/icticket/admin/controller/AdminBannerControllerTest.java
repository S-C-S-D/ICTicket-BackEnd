package com.sparta.icticket.admin.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.sparta.icticket.admin.service.AdminBannerService;
import com.sparta.icticket.banner.dto.BannerRequestDto;
import com.sparta.icticket.common.enums.BannerType;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@Transactional // 각 테스트 메서드가 트랜잭션 내에서 실행됨
@Rollback // 기본값이 true이므로 명시적으로 설정할 필요는 없음
class AdminBannerControllerTest {

    // MockMvc 객체 생성
    MockMvc mockMvc;

    // ObjectMapper 객체 생성, Java 객체를 JSON으로 변환
    private ObjectMapper objectMapper = new ObjectMapper();

    // AdminBannerService를 MockBean으로 주입하여 실제 동작을 수행하지 않도록 설정
    @MockBean
    private AdminBannerService adminBannerService;

    // WebApplicationContext의 wac 객체를 받아와서 의존성 주입
    @Autowired // 의존성 주입(DI)
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

    // 관리자 배너 생성 테스트 메서드
    @Test
    // ADMIN 권한의 사용자로 MockUser 설정
    @WithMockUser(username = "user1", roles = "ADMIN")
    void createBannerTest() throws Exception {
        //given, BannerRequestDto의 Mock 객체 설정
        BannerRequestDto mock = Mockito.mock(BannerRequestDto.class);

        // Mock 객체의 반환 값 설정
        when(mock.getPosition()).thenReturn(13);
        when(mock.getLinkUrl()).thenReturn("https://github.com/S-C-S-D/ICTicket");
        when(mock.getBannerType()).thenReturn(BannerType.MIDDLE);
        when(mock.getBannerImageUrl()).thenReturn("https://github.com/S-C-S-D/ICTicket");
        when(mock.getStartAt()).thenReturn(LocalDateTime.now().minusDays(1));
        when(mock.getEndAt()).thenReturn(LocalDateTime.now().plusYears(1));

        // when then, 값을 넣어준 contentType와 content를 /admin/banners를 통해 POST 요청을 생성
        mockMvc.perform(post("/admin/banners")
                        .contentType(MediaType.APPLICATION_JSON) //MediaType의 APPLICATION_JSON를 contentType에 넣어준다
                        .content(objectMapper.writeValueAsString(mock))) //objectMapper안에 writeValueAsString안에 mock을 content에 넣어둔다
                .andDo(print()) // 요청 및 응답 내용을 출력
                .andExpect(status().isOk()); // 응답 상태가 200 OK인지 확인
    }

    // 관리자 배너 삭제 테스트 메서드
    @Test
    @WithMockUser(username = "user1", roles = "ADMIN")
    void deleteBannerTest() throws Exception {
        // given, 삭제할 배너 ID 설정
        Long bannerId = 1L;

        // adminBannerService의 deleteBanner 메서드가 bannerId를 받아 호출할 때 아무 동작도 하지 않도록 설정
        doNothing().when(adminBannerService).deleteBanner(bannerId);

        // when then, bannerId 값을 받아와 API 호출을 통해 DELETE 요청을 버에 보내서 배너 삭제
        mockMvc.perform(delete("/admin/banners/{bannerId}", bannerId))
                .andDo(print()) // 요청 및 응답 내용을 출력
                .andExpect(status().isOk()); // 응답 상태가 200 OK인지 확인
    }
}