package com.sparta.icticket.order.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class OrderCreateRequestDto {
    private List<Long> seatIdList;
}
