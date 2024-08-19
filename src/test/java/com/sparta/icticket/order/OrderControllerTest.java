package com.sparta.icticket.order;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.sparta.icticket.admin.service.AdminSeatService;
import com.sparta.icticket.order.dto.OrderCreateRequestDto;
import com.sparta.icticket.order.dto.OrderCreateResponseDto;
import com.sparta.icticket.order.dto.OrderListResponseDto;
import com.sparta.icticket.user.User;
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

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@Transactional
@Rollback
class OrderControllerTest {

    MockMvc mockMvc;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private WebApplicationContext wac;

    @MockBean
    private AdminSeatService adminSeatService;

    @MockBean
    private OrderService orderService;

    @BeforeEach
    public void setup() {
        objectMapper.registerModule(new JavaTimeModule());

        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac)
                .addFilter(new CharacterEncodingFilter("UTF-8", true))
                .apply(SecurityMockMvcConfigurers.springSecurity())
                .build();
    }

    @Test
    @WithMockUser(username = "user1", roles = "USER")
    void createOrder() throws Exception {

        // given
        OrderCreateRequestDto requestDto = Mockito.mock(OrderCreateRequestDto.class);

        List<Long> seatIdList = new ArrayList<>();
        seatIdList.add(1L);
        when(requestDto.getSeatIdList()).thenReturn(seatIdList);

        User user = Mockito.mock(User.class);
        OrderCreateResponseDto responseDto = Mockito.mock(OrderCreateResponseDto.class);
        when(orderService.createOrder(1L, requestDto, user)).thenReturn(responseDto);

        // when-then
        mockMvc.perform(post("/sessions/1/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andDo(print())
                .andExpect(status().isOk());

    }

    @Test
    @WithMockUser(username = "user1", roles = "USER")
    void getOrders() throws Exception {

        // given
        OrderListResponseDto responseDto = Mockito.mock(OrderListResponseDto.class);
        List<OrderListResponseDto> responseDtoList = new ArrayList<>();
        responseDtoList.add(responseDto);

        User user = Mockito.mock(User.class);
        when(orderService.getOrders(1L, user)).thenReturn(responseDtoList);

        // when-then
        mockMvc.perform(get("/users/1/orders"))
                .andDo(print())
                .andExpect(status().isOk());

    }

    @Test
    @WithMockUser(username = "user1", roles = "USER")
    void deleteOrder() throws Exception {

        // given
        User user = Mockito.mock(User.class);
        doNothing().when(orderService).deleteOrder(1L, user);

        // when-then
        mockMvc.perform(patch("/users/orders/1"))
                .andDo(print())
                .andExpect(status().isOk());
    }
}