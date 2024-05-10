package com.church.domain.members.dto.request;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class MemberRequestDto {
    @NotEmpty(message = "아이디를 입력해주세요")
    private String memberId;

    @NotEmpty(message = "비밀번호를 입력해주세요")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*\\d)[a-z\\d!@#$%^&*()_-]{5,12}$", message = "비밀번호는 5~12자 이내 영어(소문자),숫자,특수기호(선택) 범위에서 입력해야합니다.")
    private String password;

    private String name;

    private String church;

    private String position;

    @Email
    @Pattern(regexp = "^[_a-z0-9-]+(.[_a-z0-9-]+)*@(?:\\w+\\.)+\\w+$", message = "올바른 형식의 이메일을 입력해주세요.")
    private String email;

    private String phoneNumber;


}
