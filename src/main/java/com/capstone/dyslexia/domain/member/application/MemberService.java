package com.capstone.dyslexia.domain.member.application;

import com.capstone.dyslexia.domain.level.application.LevelRangeService;
import com.capstone.dyslexia.domain.member.domain.Member;
import com.capstone.dyslexia.domain.member.domain.repository.MemberRepository;
import com.capstone.dyslexia.domain.member.dto.request.MemberSignInRequestDto;
import com.capstone.dyslexia.domain.member.dto.request.MemberSignUpRequestDto;
import com.capstone.dyslexia.domain.member.dto.request.MemberUpdateRequestDto;
import com.capstone.dyslexia.domain.member.dto.response.MemberResponseDto;
import com.capstone.dyslexia.domain.store.domain.Store;
import com.capstone.dyslexia.global.error.exceptions.BadRequestException;
import com.capstone.dyslexia.global.error.exceptions.UnauthorizedException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static com.capstone.dyslexia.global.error.ErrorCode.INVALID_SIGNIN;
import static com.capstone.dyslexia.global.error.ErrorCode.ROW_DOES_NOT_EXIST;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;

    private final LevelRangeService levelRangeService;

    @Transactional
    public MemberResponseDto signUp(MemberSignUpRequestDto memberSignUpRequestDto) {
        Store store = Store.builder().build();

        Member member = Member.builder()
                .email(memberSignUpRequestDto.getEmail())
                .password(memberSignUpRequestDto.getPassword())
                .age(memberSignUpRequestDto.getAge())
                .store(store)
                .build();

        memberRepository.save(member);

        return MemberResponseDto.builder()
                .id(member.getId())
                .email(member.getEmail())
                .age(member.getAge())
                .level(member.getLevel())
                .build();
    }

    public MemberResponseDto signIn(MemberSignInRequestDto memberSignInRequestDto) {
        Member member = memberRepository.findByEmail(memberSignInRequestDto.getEmail())
                .orElseThrow(() -> new UnauthorizedException(INVALID_SIGNIN, "유효하지 않은 이메일입니다."));

        if (!member.getPassword().equals(memberSignInRequestDto.getPassword())) {
            throw new UnauthorizedException(INVALID_SIGNIN, "유효하지 않은 비밀번호입니다.");
        }

        return MemberResponseDto.builder()
                .id(member.getId())
                .email(member.getEmail())
                .age(member.getAge())
                .level(member.getLevel())
                .build();
    }

    @Transactional
    public MemberResponseDto updateMember(Long memberId, MemberUpdateRequestDto memberUpdateRequestDto) {
        Member member = memberValidation(memberId);

        member.updateMember(memberUpdateRequestDto);
        return MemberResponseDto.builder()
                .id(member.getId())
                .email(member.getEmail())
                .password(member.getPassword())
                .age(member.getAge())
                .level(member.getLevel())
                .build();
    }

    public MemberResponseDto findMemberById(Long memberId) {
        Member member = memberValidation(memberId);
        return MemberResponseDto.builder()
                .id(member.getId())
                .email(member.getEmail())
                .password(member.getPassword())
                .age(member.getAge())
                .level(member.getLevel())
                .build();
    }

    public Member memberValidation(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new BadRequestException(ROW_DOES_NOT_EXIST, "잘못된 Member ID 입니다."));
    }

}
