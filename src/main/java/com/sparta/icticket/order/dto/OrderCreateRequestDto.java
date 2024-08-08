package com.sparta.icticket.order.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class OrderCreateRequestDto {
    private List<Long> seatIdList;
}