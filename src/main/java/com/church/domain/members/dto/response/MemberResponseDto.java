package com.church.domain.members.dto.response;


import com.church.domain.members.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class MemberResponseDto {

    private String memberId;
    private String name;
    private String birth;
    private String position;
    private String email;

    public MemberResponseDto(Member member) {
        this.memberId = member.getMemberId();
        this.name = member.getName();
        this.birth = member.getBirth();
        this.position = member.getPosition();
        this.email = member.getEmail();
    }
}
