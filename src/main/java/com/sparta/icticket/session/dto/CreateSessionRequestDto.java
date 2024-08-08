package com.sparta.icticket.session.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalTime;

@Getter
public class CreateSessionRequestDto {
    @NotNull(message = "날짜를 입력해 주세요.")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Timestamp date;


    @NotBlank(message = "세션 이름을 입력해 주세요.")
    @Pattern(regexp = "[A-Z]+")
    private String name;

    @NotNull(message = "시간을 입력해 주세요.")
    @JsonFormat(pattern = "HH:mm")
    private Timestamp time;
}
