package com.sparta.icticket.comment;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Timestamp;
import java.time.LocalDateTime;
@Getter
public class GetCommentResponseDto {

    @NotBlank
    private String title;

    @NotBlank
    private String nickname;

    @NotNull
    private Integer rate;

    @NotNull
    private String description;

    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private Timestamp createdAt;

    public GetCommentResponseDto(Comment comment) {
        this.title= comment.getTitle();
        this.nickname = comment.getUser().getNickname();
        this.rate = comment.getRate();
        this.description = comment.getDescription();
        this.createdAt = comment.getCreatedAt();
    }
}
