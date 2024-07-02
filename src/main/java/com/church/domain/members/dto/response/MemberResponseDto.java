package com.church.domain.members.dto.response;


import com.church.domain.members.entity.Members;
import com.church.domain.members.entity.ROLE;
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
    private ROLE role;
    private Boolean isApproval;

    public MemberResponseDto(Members member) {
        this.memberId = member.getMemberId();
        this.name = member.getName();
        this.birth = member.getBirth();
        this.position = member.getPosition();
        this.email = member.getEmail();
        this.role = member.getRole();
        this.isApproval=member.isApproval();
    }
}
