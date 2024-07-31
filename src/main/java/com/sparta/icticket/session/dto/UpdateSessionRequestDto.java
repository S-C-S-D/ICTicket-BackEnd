package com.sparta.icticket.session.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalTime;
@Getter
public class UpdateSessionRequestDto {

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;

    @Pattern(regexp = "[A-Z]+")
    private String name;

    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime time;
}
