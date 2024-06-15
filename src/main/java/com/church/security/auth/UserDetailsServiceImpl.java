package com.church.security.auth;

import com.church.domain.members.entity.Members;
import com.church.domain.members.repository.MemberRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String memberId) throws UsernameNotFoundException {
        Members member = memberRepository.findByMemberId(memberId).orElseThrow(()-> new EntityNotFoundException("사용자를 찾을 수 없습니다."));
        return new UserDetailsImpl(member, memberId);
    }
}
