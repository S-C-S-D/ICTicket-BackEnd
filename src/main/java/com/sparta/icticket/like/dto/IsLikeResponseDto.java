package com.sparta.icticket.like.dto;

import com.sparta.icticket.like.Like;
import lombok.Getter;

@Getter
public class IsLikeResponseDto {
    private Long id;
    private Boolean isLike;

    public IsLikeResponseDto(Like like, Boolean isLike) {
        this.id = like.getId();
        this.isLike = isLike;
    }
}
