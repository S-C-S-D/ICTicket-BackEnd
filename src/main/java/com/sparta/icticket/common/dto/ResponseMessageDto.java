package com.sparta.icticket.common.dto;

import com.sparta.icticket.common.enums.SuccessStatus;
import lombok.Getter;

@Getter
public class ResponseMessageDto {
    private int status;
    private String message;

    public ResponseMessageDto(SuccessStatus status) {
        this.status = status.getHttpStatus().value();
        this.message = status.getMessage();
    }
}