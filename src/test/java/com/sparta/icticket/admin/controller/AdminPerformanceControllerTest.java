package com.sparta.icticket.admin.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.sparta.icticket.common.enums.AgeGroup;
import com.sparta.icticket.common.enums.GenreType;
import com.sparta.icticket.performance.dto.PerformanceRequestDto;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@Transactional // 각 테스트 메서드가 트랜잭션 내에서 실행됨
@Rollback // 기본값이 true이므로 명시적으로 설정할 필요는 없음
class AdminPerformanceControllerTest {
    MockMvc mockMvc;

    private ObjectMapper objectMapper = new ObjectMapper();


    @Autowired
    private WebApplicationContext wac;

    @BeforeEach
    public void setup() {
        objectMapper.registerModule(new JavaTimeModule());

        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac)
                .addFilter(new CharacterEncodingFilter("UTF-8", true))
                .apply(SecurityMockMvcConfigurers.springSecurity())
                .build();
    }

    @Test
    @WithMockUser(username = "user1", roles = "ADMIN")
    void createPerformanceTest() throws Exception {
        //given
        PerformanceRequestDto mock = Mockito.mock(PerformanceRequestDto.class);

        //when
        when(mock.getVenueId()).thenReturn(1L);
        when(mock.getTitle()).thenReturn("타이틀입니다.");
        when(mock.getGenreType()).thenReturn(GenreType.CONCERT);
        when(mock.getRunTime()).thenReturn(100);
        when(mock.getAgeGroup()).thenReturn(AgeGroup.ALL);
        when(mock.getDescription()).thenReturn("설명입니다.");
        when(mock.getOpenAt()).thenReturn(LocalDateTime.parse("2024-08-12T20:10:00"));
        when(mock.getEndAt()).thenReturn(LocalDate.parse("2024-08-15"));
        when(mock.getStartAt()).thenReturn(LocalDate.parse("2024-08-15"));
        when(mock.getImageUrl()).thenReturn("asdfqwer");

        //then
        mockMvc.perform(post("/admin/performances")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(mock)))
                .andDo(print())
                .andExpect(status().isOk());
    }
}