package com.church.domain.admin.banner.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor
public class BannerRequestDto extends BannerDto {
    private MultipartFile imageFile;
}
