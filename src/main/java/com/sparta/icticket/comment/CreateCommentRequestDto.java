package com.sparta.icticket.comment;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import org.hibernate.validator.constraints.Range;

@Getter
public class CreateCommentRequestDto {
    @NotBlank(message = "제목을 입력해 주세요.")
    private String title;

    @NotBlank(message = "내용을 입력해 주세요.")
    private String description;

    @NotNull(message = "rate를 입력해 주세요")
    @Range(min = 1, max = 5)
    private Integer rate;
}
