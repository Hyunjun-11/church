package com.church.util.Image.dto;

import com.church.util.Image.entity.BoardType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ImageRequestDto  {


    private String imageName;

    private MultipartFile image;

    private BoardType boardType;

}