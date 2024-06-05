package com.church.util.Image.dto;

import com.church.util.Image.entity.BoardType;
import com.church.util.Image.entity.Image;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ImageResponseDto {
    private String imageName;
    private BoardType boardType;
    private String imageUrl;

    public ImageResponseDto(Image image) {
        this.imageName = image.getImageName();
        this.boardType = image.getBoardType();
        this.imageUrl = image.getUrl();

    }
}