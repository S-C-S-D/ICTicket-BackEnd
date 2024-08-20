package com.sparta.icticket.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.sparta.icticket.common.config.SecurityConfig;
import com.sparta.icticket.common.config.WebConfig;
import com.sparta.icticket.common.enums.UserRole;
import com.sparta.icticket.common.enums.UserStatus;
import com.sparta.icticket.user.dto.UserSignupRequestDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jackson.JsonComponent;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.core.convert.converter.GenericConverter;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;

import java.awt.*;
import java.security.Principal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
// 여러 스프링 테스트 어노테이션 중, Web(Spring MVC)에 집중할 수 있는 어노테이션이다.
// @SpringBootTest는 모든 빈을 로드한다.
// 따라서 Controller 레이어만 테스트 하고 싶다면 @WebMvcTest를 사용하는 것이 좋다.
// @WebMvcTest는 다음 내용만 스캔하도록 제한한다.
// - @Controller, @ControllerAdvice, @JsonComponent
// - Converter,GenericConverter,Filter, WebSecurityConfigurerAdapter
// - WebMvcConfigurer, HandlerMethodArgumentResolver
// Mock 객체를 사용하기 위해서는 @ExtendWith(MockitoExtension.class) 를 설정해주어야하는데 @WebMvcTest 안에있는 @Extendwith(SpringExtension.class)는 Mokito 어노테이션을 포함하고있다
@WebMvcTest(UserController.class)
@AutoConfigureMockMvc
class UserControllerTest {

    // MockMvc를 사용하면 HTTP 요청을 작성하고 컨트롤러의 응답을 검증할 수 있습니다.
    // 이를 통해 통합 테스트를 실행하지 않고도 컨트롤러의 동작을 확인할 수 있습니다.
    //TestCase -> MockMvc -> TestDispatcher Servlet -> Controller ->
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext wac;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userService;

    @InjectMocks // Mock객체로 등록된 객체를 주입
    private UserController userController;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac)
                .addFilter(new CharacterEncodingFilter("UTF-8", true))
                .apply(SecurityMockMvcConfigurers.springSecurity())
                .build();
    }

    @Test
    @WithMockUser // 401 error 방지, Security가 모든 요청에 대해 권한을 요구하기 때문에 401 오류가 발생하는것
    void createUser() throws Exception {
        //given
        UserSignupRequestDto mock = Mockito.mock(UserSignupRequestDto.class);

        when(mock.getEmail()).thenReturn("example@naver.com");
        when(mock.getPassword()).thenReturn("example1");
        when(mock.getName()).thenReturn("홍길동");
        when(mock.getNickname()).thenReturn("candy");
        when(mock.getPhoneNumber()).thenReturn("010-1234-5678");
        when(mock.getAddress()).thenReturn("천국");

        //when then
        mockMvc.perform(post("/users")
                        .with(csrf()) //403 error 방지
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(mock)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("회원가입에 성공하였습니다."))
                .andDo(print());
    }

    @Test
    @WithMockUser
    void createAdminUser() throws Exception{
        //given
        UserSignupRequestDto mock = Mockito.mock(UserSignupRequestDto.class);

        when(mock.getEmail()).thenReturn("admin@naver.com");
        when(mock.getPassword()).thenReturn("admin1234");
        when(mock.getName()).thenReturn("어드민");
        when(mock.getNickname()).thenReturn("devil");
        when(mock.getPhoneNumber()).thenReturn("010-0000-0000");
        when(mock.getAddress()).thenReturn("지옥");

        //when then
        mockMvc.perform(post("/users/admin")
                        .with(csrf()) //403 error 방지
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(mock)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("회원가입에 성공하였습니다."))
                .andDo(print());
    }

    @Test
    void logout() {
    }

    @Test
    void updateUser() {
    }

    @Test
    void updateProfile() {
    }

    @Test
    void getProfile() {
    }
}