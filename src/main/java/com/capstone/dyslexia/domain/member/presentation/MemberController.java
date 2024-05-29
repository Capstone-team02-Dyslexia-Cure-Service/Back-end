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
import org.springframework.http.HttpStatus;
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
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "회원 가입", description = "회원 가입을 처리합니다.")
    public MemberResponseDto signUp(
            @Valid @RequestBody MemberSignUpRequestDto memberSignUpRequestDto
    ) {
        return memberService.signUp(memberSignUpRequestDto);
    }

    @PostMapping("/signin")
    @Operation(summary = "회원 로그인", description = "회원 로그인을 처리합니다.")
    @ResponseStatus(HttpStatus.OK)
    public MemberResponseDto signIn(
            @Valid @RequestBody MemberSignInRequestDto memberSignInRequestDto
    ) {
        return memberService.signIn(memberSignInRequestDto);
    }

    @PutMapping("/update")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "회원 정보 수정", description = "회원 정보를 수정합니다.")
    public MemberResponseDto updateMember(
            @RequestHeader Long memberId,
            @Valid @RequestBody MemberUpdateRequestDto memberUpdateRequestDto
    ) {
        return memberService.updateMember(memberId, memberUpdateRequestDto);
    }

    @GetMapping("/find")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "회원 정보 조회", description = "회원 정보를 조회합니다.")
    public MemberResponseDto findMemberById(
            @Valid @RequestHeader Long memberId
    ) {
        return memberService.findMemberById(memberId);
    }

}
