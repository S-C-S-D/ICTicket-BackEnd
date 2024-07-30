package com.sparta.icticket.order.dto;

import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class OrderCreateRequestDto {
    private List<Long> seatIdList;
    private List<LocalDateTime> modifiedAtList;
}
