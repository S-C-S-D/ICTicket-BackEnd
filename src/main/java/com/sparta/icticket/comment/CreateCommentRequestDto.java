package com.sparta.icticket.comment;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class CreateCommentRequestDto {
    @NotBlank(message = "제목을 입력해 주세요.")
    private String title;

    @NotBlank(message = "내용을 입력해 주세요.")
    private String description;

    @NotNull(message = "rate를 입력해 주세요")
    private Integer rate;
}
