package com.church.domain.user.worship.bulletin.repository;

import com.church.domain.user.worship.bulletin.entity.Bulletin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BulletinRepository extends JpaRepository<Bulletin, Long> {
}
