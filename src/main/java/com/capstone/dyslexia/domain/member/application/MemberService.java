package com.capstone.dyslexia.domain.member.application;

import com.capstone.dyslexia.domain.animal.domain.Animal;
import com.capstone.dyslexia.domain.animal.domain.AnimalType;
import com.capstone.dyslexia.domain.animal.domain.repository.AnimalRepository;
import com.capstone.dyslexia.domain.dateAchievement.domain.DateAchievement;
import com.capstone.dyslexia.domain.dateAchievement.domain.repository.DateAchievementRepository;
import com.capstone.dyslexia.domain.member.domain.Member;
import com.capstone.dyslexia.domain.member.domain.repository.MemberRepository;
import com.capstone.dyslexia.domain.member.dto.request.MemberSignInRequestDto;
import com.capstone.dyslexia.domain.member.dto.request.MemberSignUpRequestDto;
import com.capstone.dyslexia.domain.member.dto.request.MemberUpdateRequestDto;
import com.capstone.dyslexia.domain.member.dto.response.MemberResponseDto;
import com.capstone.dyslexia.domain.store.domain.Store;
import com.capstone.dyslexia.domain.store.domain.repository.StoreRepository;
import com.capstone.dyslexia.global.error.ErrorCode;
import com.capstone.dyslexia.global.error.exceptions.BadRequestException;
import com.capstone.dyslexia.global.error.exceptions.InternalServerException;
import com.capstone.dyslexia.global.error.exceptions.UnauthorizedException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.time.LocalDateTime;
import java.util.List;

import static com.capstone.dyslexia.global.error.ErrorCode.INVALID_SIGNIN;
import static com.capstone.dyslexia.global.error.ErrorCode.ROW_DOES_NOT_EXIST;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;
    private final StoreRepository storeRepository;
    private final DateAchievementRepository dateAchievementRepository;
    private final AnimalRepository animalRepository;

    @Transactional
    public MemberResponseDto signUp(MemberSignUpRequestDto memberSignUpRequestDto) {
        if (memberRepository.findByEmail(memberSignUpRequestDto.getEmail()).isPresent()) {
            throw new BadRequestException(ROW_DOES_NOT_EXIST, "이미 존재하는 이메일입니다.");
        }

        Member member = Member.builder()
                .email(memberSignUpRequestDto.getEmail())
                .password(memberSignUpRequestDto.getPassword())
                .name(memberSignUpRequestDto.getName())
                .age(memberSignUpRequestDto.getAge())
                .isEvaluated(false)
                .level(1.0)
                .build();

        member = memberRepository.save(member);

        Store store = Store.builder()
                .member(member)
                .build();

        store = storeRepository.save(store);

        Animal animal = Animal.builder()
                .animalType(AnimalType.PENGUIN)
                .nickname(AnimalType.PENGUIN.toString())
                .hungerTimer(LocalDateTime.now().plusDays(1))
                .store(store)
                .build();

        animalRepository.save(animal);

        return MemberResponseDto.builder()
                .id(member.getId())
                .email(member.getEmail())
                .password(member.getPassword())
                .name(member.getName())
                .age(member.getAge())
                .isEvaluated(member.getIsEvaluated())
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
                .name(member.getName())
                .age(member.getAge())
                .isEvaluated(member.getIsEvaluated())
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
                .name(member.getName())
                .age(member.getAge())
                .isEvaluated(member.getIsEvaluated())
                .level(member.getLevel())
                .build();
    }

    public Member memberValidation(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new BadRequestException(ROW_DOES_NOT_EXIST, "잘못된 Member ID 입니다."));
    }

    @Transactional
    public Member updateMemberLevelByDate(Member member, int numOfDays) {
        List<DateAchievement> dateAchievementList =
                dateAchievementRepository.findTopByMemberOrderByAchievementDateDesc(
                        member,
                        PageRequest.of(0, numOfDays)
                ).getContent();

        if (dateAchievementList.isEmpty()) {
            throw new InternalServerException(ErrorCode.INTERNAL_SERVER, "member에게 dateAchievement record가 존재하지 않습니다.");
        }

        Double averageScore = dateAchievementList.stream()
                .mapToDouble(DateAchievement::getScore)
                .average()
                .orElseThrow(() -> new InternalServerException(ErrorCode.INTERNAL_SERVER, "member에게 평균 점수가 발견되지 않았습니다."));

        member = member.updateMemberLevel(averageScore);

        memberRepository.save(member);

        return member;
    }

}
