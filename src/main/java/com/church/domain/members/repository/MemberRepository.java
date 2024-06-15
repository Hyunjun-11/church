package com.church.domain.members.repository;

import com.church.domain.members.entity.Members;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Members,Long> {
    Optional<Members> findByMemberId(String memberId);
}
