package com.church.domain.members.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SignInRequestDto {
    private String memberId;
    private String password;
}
