package com.capstone.dyslexia.domain.member.presentation;

import com.capstone.dyslexia.domain.member.application.MemberService;
import com.capstone.dyslexia.domain.member.dto.request.MemberSignInRequestDto;
import com.capstone.dyslexia.domain.member.dto.request.MemberSignUpRequestDto;
import com.capstone.dyslexia.domain.member.dto.request.MemberUpdateRequestDto;
import com.capstone.dyslexia.domain.member.dto.response.MemberResponseDto;
import com.capstone.dyslexia.global.payload.ApiResponseTemplate;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/member")
@Tag(name = "Member", description = "Member API")
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/signup")
    @Operation(summary = "회원 가입", description = "회원 가입을 처리합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "회원 가입 성공", content = @Content(schema = @Schema(implementation = ApiResponseTemplate.class, subTypes = {MemberResponseDto.class}))),
            @ApiResponse(responseCode = "400", description = "잘못된 요청")
    })
    public ApiResponseTemplate<MemberResponseDto> signUp(
            @Valid @RequestBody MemberSignUpRequestDto memberSignUpRequestDto
    ) {
        return ApiResponseTemplate.created(memberService.signUp(memberSignUpRequestDto));
    }

    @PostMapping("/signin")
    @Operation(summary = "회원 로그인", description = "회원 로그인을 처리합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "회원 로그인 성공", content = @Content(schema = @Schema(implementation = ApiResponseTemplate.class, subTypes = {MemberResponseDto.class}))),
            @ApiResponse(responseCode = "400", description = "잘못된 요청")
    })
    public ApiResponseTemplate<MemberResponseDto> signIn(
            @Valid @RequestBody MemberSignInRequestDto memberSignInRequestDto
    ) {
        return ApiResponseTemplate.ok(memberService.signIn(memberSignInRequestDto));
    }

    @PutMapping("/update")
    @Operation(summary = "회원 정보 수정", description = "회원 정보를 수정합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "회원 정보 수정 성공", content = @Content(schema = @Schema(implementation = ApiResponseTemplate.class, subTypes = {MemberResponseDto.class}))),
            @ApiResponse(responseCode = "400", description = "잘못된 요청")
    })
    public ApiResponseTemplate<MemberResponseDto> updateMember(
            @RequestHeader Long memberId,
            @Valid @RequestBody MemberUpdateRequestDto memberUpdateRequestDto
    ) {
        return ApiResponseTemplate.ok(memberService.updateMember(memberId, memberUpdateRequestDto));
    }

    @GetMapping("/find")
    @Operation(summary = "회원 정보 조회", description = "회원 정보를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "회원 정보 조회 성공", content = @Content(schema = @Schema(implementation = ApiResponseTemplate.class, subTypes = {MemberResponseDto.class}))),
            @ApiResponse(responseCode = "400", description = "잘못된 요청")
    })
    public ApiResponseTemplate<MemberResponseDto> findMemberById(
            @Valid @RequestHeader Long memberId
    ) {
        return ApiResponseTemplate.ok(memberService.findMemberById(memberId));
    }

}
