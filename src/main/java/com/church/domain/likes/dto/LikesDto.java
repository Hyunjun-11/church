package com.church.domain.likes.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class LikesDto {
    private Long likes;
    private Long hearts;
    private Long prays;


    public LikesDto(Long likes, Long hearts, Long prays) {
        this.likes = likes;
        this.hearts = hearts;
        this.prays = prays;

    }
}
