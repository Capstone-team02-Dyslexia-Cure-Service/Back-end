package com.capstone.dyslexia.domain.member.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
@Data
@AllArgsConstructor
@Builder
@Schema(description = "회원 응답 DTO")
public class MemberResponseDto {

    @Schema
    private Long id;

    @Schema
    private String email;

    @Schema
    private String password;

    @Schema
    private String name;

    @Schema
    private Integer age;

    @Schema
    private Double level;

    private Boolean isEvaluated;

}
