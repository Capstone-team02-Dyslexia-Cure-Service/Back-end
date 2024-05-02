package com.capstone.dyslexia.domain.member.dto.request;

import jakarta.validation.constraints.*;
import lombok.Getter;

@Getter
public class MemberUpdateRequestDto {

    @Email(message = "올바른 이메일 주소를 입력해주세요.")
    private String email;

    @NotBlank(message = "PW는 공백이면 안 됩니다.")
    @Max(value = 100, message = "100자 이하로 써 주세요.")
    private String password;

    @Digits(integer = 2, fraction = 0, message = "올바른 나이를 입력해주세요(0~99)")
    @Positive(message = "올바른 나이를 입력해주세요(0~99)")
    private Integer age;

}
