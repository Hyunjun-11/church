package com.church.domain.members.dto.response;

import com.church.domain.members.entity.Members;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@AllArgsConstructor
@Getter
public class SignInResponseDto {
    private String memberId;
    private String name;

    public SignInResponseDto(Members member) {
        this.memberId = member.getMemberId();
        this.name = member.getName();
    }
}
