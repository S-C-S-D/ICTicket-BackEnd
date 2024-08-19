package com.sparta.icticket.admin.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.sparta.icticket.admin.service.AdminSeatService;
import com.sparta.icticket.common.enums.SeatGrade;
import com.sparta.icticket.seat.dto.SeatCreateRequestDto;
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

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@Transactional
@Rollback
class AdminSeatControllerTest {

    MockMvc mockMvc;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private WebApplicationContext wac;

    @MockBean
    private AdminSeatService adminSeatService;

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
    void createSeat() throws Exception {

        // given
        SeatCreateRequestDto mock = Mockito.mock(SeatCreateRequestDto.class);

        when(mock.getPrice()).thenReturn(10000);
        when(mock.getSeatNumber()).thenReturn("1");
        when(mock.getSeatGrade()).thenReturn(SeatGrade.A);

        doNothing().when(adminSeatService).createSeat(1L, mock);

        // when-then
        mockMvc.perform(post("/admin/sessions/1/seats")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(mock)))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "user1", roles = "ADMIN")
    void deleteSeat() throws Exception {

        // given
        doNothing().when(adminSeatService).deleteSeat(1L, 1L);

        // when-then
        mockMvc.perform(delete("/admin/sessions/1/seats/1"))
                        .andDo(print())
                        .andExpect(status().isOk());
    }
}