package com.sparta.icticket.session;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalTime;

@Getter
public class GetSessionsResponseDto {

    private Long id;
    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Timestamp date;

    @NotBlank
    private String name;

    @JsonFormat(pattern = "HH:mm")
    private Timestamp time;

    public GetSessionsResponseDto(Session session) {
        this.id = session.getId();
        this.date = session.getSessionDate();
        this.name = session.getSessionName();
        this.time = session.getSessionTime();
    }
}
