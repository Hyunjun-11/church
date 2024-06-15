package com.church.domain.members.dto.request;


import com.church.domain.members.entity.ROLE;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class MemberRequestDto {
    private String name;

    @NotEmpty(message = "아이디를 입력해주세요")
//    @Pattern(regexp = "^[A-Za-z0-9]+$", message = "아이디는 15자 이하, 영문자 또는 영문자와 숫자가 섞여야 합니다.")
    private String memberId;

    @NotEmpty(message = "비밀번호를 입력해주세요")
//    @Pattern(regexp = "^(?=.*[a-z])(?=.*\\d)[a-z\\d!@#$%^&*()_-]{6,30}$", message = "비밀번호는 5~12자 이내 영어(소문자),숫자,특수기호(선택) 범위에서 입력해야합니다.")
    private String password;

    private String birth;

    private String position;

    private ROLE role;

//    @Email
    private String email;




}
