package com.sparta.icticket.seat;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.sparta.icticket.common.enums.SeatGrade;
import com.sparta.icticket.performance.Performance;
import com.sparta.icticket.seat.dto.*;
import com.sparta.icticket.session.Session;
import com.sparta.icticket.user.User;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
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

import java.util.ArrayList;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@Transactional
@Rollback
class SeatControllerTest {

    MockMvc mockMvc;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private WebApplicationContext wac;

    @MockBean
    private SeatService seatService;

    @BeforeEach
    public void setup() {
        objectMapper.registerModule(new JavaTimeModule());

        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac)
                .addFilter(new CharacterEncodingFilter("UTF-8", true))
                .apply(SecurityMockMvcConfigurers.springSecurity())
                .build();
    }

    @Test
    void getSeatCount() throws Exception {

        // given
        Integer totalSeatCount = 2;
        Integer restSeatCount = 1;
        SeatCountResponseDto result = new SeatCountResponseDto(totalSeatCount, restSeatCount);

        given(seatService.getSeatCount(1L, 1L)).willReturn(result);

        // when
        mockMvc.perform(get("/performances/{performanceId}/sessions/{sessionId}/seat-count", 1L, 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(result))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

        Assertions.assertEquals(result.getRestSeatCount(), 1);
    }

    @Test
    @WithMockUser(username = "user1", roles = "USER")
    void getSeats() throws Exception {

        // given
        SeatCreateRequestDto mock = Mockito.mock(SeatCreateRequestDto.class);

        when(mock.getPrice()).thenReturn(10000);
        when(mock.getSeatNumber()).thenReturn("1");
        when(mock.getSeatGrade()).thenReturn(SeatGrade.A);

        Seat seat = new Seat(new Session(), mock);
        SeatInfoResponseDto result = new SeatInfoResponseDto(seat);

        List<SeatInfoResponseDto> list = new ArrayList<>();

        list.add(result);
        given(seatService.getSeats(1L, 1L)).willReturn(list);

        // when-then
        mockMvc.perform(get("/performances/1/sessions/1/seats")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

        Assertions.assertEquals(list.get(0).getPrice(), 10000);
    }

    @Test
    @WithMockUser(username = "user1", roles = "USER")
    void reserveSeat() throws Exception {

        // given
        SeatReservedRequestDto mock = Mockito.mock(SeatReservedRequestDto.class);

        List<Long> list = new ArrayList<>();
        list.add(1L);
        when(mock.getSeatIdList()).thenReturn(list);

        List<String> seatNumberList = new ArrayList<>();
        seatNumberList.add("1");

        SeatReservedResponseDto result = new SeatReservedResponseDto(new Performance(), new Session(), seatNumberList, 10000, 30);

        given(seatService.reserveSeat(1L, mock, new User())).willReturn(result);

        // when-then
        mockMvc.perform(patch("/sessions/1/seats/reserve")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(mock)))
                .andDo(print())
                .andExpect(status().isOk());

        Assertions.assertEquals(result.getDiscountRate(), 30);
    }
}