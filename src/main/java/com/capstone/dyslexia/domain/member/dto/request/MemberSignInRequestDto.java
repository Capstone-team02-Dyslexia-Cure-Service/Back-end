package com.capstone.dyslexia.domain.member.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class MemberSignInRequestDto {

    @Email(message = "올바른 이메일 주소를 입력해주세요.")
    private String email;

    @NotBlank(message = "PW는 공백이면 안 됩니다.")
    @Max(value = 100, message = "100자 이하로 써 주세요.")
    private String password;

}
