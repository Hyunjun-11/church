package com.church.util.Image.dto;

import com.church.util.Image.entity.BoardType;
import com.church.util.Image.entity.Image;
import lombok.AllArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Setter
public class ImageResponseDto {
    private String imageName;
    private BoardType boardType;


    public ImageResponseDto(Image image) {
        this.imageName= image.getFileName();
        this.boardType=image.getBoardType();
    }
}
