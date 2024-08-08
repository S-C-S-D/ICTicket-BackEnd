package com.sparta.icticket.order.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

import java.sql.Timestamp;
import java.util.List;

@Getter
public class OrderCreateRequestDto {
    private List<Long> seatIdList;

    @JsonFormat(pattern = "yyyy-MM-dd-HH:mm")
    private Timestamp modifiedAt;
}