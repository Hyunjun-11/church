package com.church.domain.admin.banner.repository;

import com.church.domain.admin.banner.entity.Banner;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BannerRepository extends JpaRepository<Banner, Long> {
    Optional<Banner> findByCategoryName(String name);
}
