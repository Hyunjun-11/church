package com.church.domain.members.dto.response;


import com.church.domain.members.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Setter;

@Setter
@AllArgsConstructor
public class MemberResponseDto {

    private String memberId;
    private String name;
    private String church;
    private String position;
    private String email;

    public MemberResponseDto(Member member) {
        this.memberId = member.getMemberId();
        this.name = member.getName();
        this.church =member.getChurch();
        this.position = member.getPosition();
        this.email = member.getEmail();
    }
}
