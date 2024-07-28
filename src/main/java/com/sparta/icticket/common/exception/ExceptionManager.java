package com.sparta.icticket.common.exception;

import com.sparta.icticket.common.dto.ResponseMessageDto;
import com.sparta.icticket.common.enums.ErrorType;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.format.DateTimeParseException;

@RestControllerAdvice
public class ExceptionManager {
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<?> handleScheduleException(CustomException e) {
        e.printStackTrace();
        return ResponseEntity.status(e.getErrorType().getHttpStatus()).body(new ExceptionDto(e.getErrorType()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleException(MethodArgumentNotValidException e) {
        e.printStackTrace();
        BindingResult bindingResult = e.getBindingResult();
        StringBuilder builder = new StringBuilder();

        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            builder.append(fieldError.getField()).append(" : ").append(fieldError.getDefaultMessage()).append("\n");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(builder.toString());
    }

    @ExceptionHandler(DateTimeParseException.class)
    public ResponseEntity<?> handleException(DateTimeParseException e) {
        e.printStackTrace();
        return ResponseEntity.status(ErrorType.WRONG_DATE_FORMAT.getHttpStatus()).body(ErrorType.WRONG_DATE_FORMAT.getMessage());
    }


}