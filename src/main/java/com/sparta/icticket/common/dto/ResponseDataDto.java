package com.sparta.icticket.common.dto;

import com.sparta.icticket.common.enums.SuccessStatus;
import lombok.Getter;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
public class ResponseDataDto<T> {
    private int status;
    private String message;
    private T data;

    public ResponseDataDto(SuccessStatus responseStatus, T data) {
        this.status = responseStatus.getHttpStatus().value();
        this.message = responseStatus.getMessage();
        this.data = data;
    }

}
