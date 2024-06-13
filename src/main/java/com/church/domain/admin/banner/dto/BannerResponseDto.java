package com.church.domain.admin.banner.dto;

import com.church.domain.admin.banner.entity.Banner;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class BannerResponseDto extends BannerDto {
    private Long id;

    public BannerResponseDto(Banner banner) {
        super(
                banner.getUrl(),
                banner.getCategoryName());
        this.id = banner.getId();

    }
}
