package com.capstone.dyslexia.domain.animal.application;

import com.capstone.dyslexia.domain.animal.domain.Animal;
import com.capstone.dyslexia.domain.animal.domain.AnimalType;
import com.capstone.dyslexia.domain.animal.domain.repository.AnimalRepository;
import com.capstone.dyslexia.domain.animal.dto.response.AnimalResponseDto;
import com.capstone.dyslexia.domain.member.application.MemberService;
import com.capstone.dyslexia.domain.member.domain.Member;
import com.capstone.dyslexia.global.error.exceptions.BadRequestException;
import com.capstone.dyslexia.global.error.exceptions.InternalServerException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static com.capstone.dyslexia.global.error.ErrorCode.INTERNAL_SERVER;
import static com.capstone.dyslexia.global.error.ErrorCode.ROW_DOES_NOT_EXIST;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AnimalService {

    private final MemberService memberService;

    private final AnimalRepository animalRepository;

    @Transactional
    public AnimalResponseDto createAnimal(Long memberId, AnimalType animalType) {
        Member member = memberService.memberValidation(memberId);

        findAllAnimal(member.getId()).forEach(animal -> {
            if (animal.getAnimalType().equals(animalType.toString()))
                throw new BadRequestException(INTERNAL_SERVER, "이미 보유하고 있는 동물입니다.");
        });

        Animal animal = Animal.builder()
                .animalType(animalType)
                .nickname(animalType.toString())
                .hungerTimer(LocalDateTime.now().plusDays(1))
                .store(member.getStore())
                .build();

        animalRepository.save(animal);

        return AnimalResponseDto.from(animal);
    }

    @Transactional
    public AnimalResponseDto updateAnimal(Long memberId, Long animalId, String nickname) {
        Member member = memberService.memberValidation(memberId);

        Animal animal = animalRepository.findById(animalId)
                .orElseThrow(() -> new BadRequestException(ROW_DOES_NOT_EXIST, "잘못된 Animal ID 입니다."));

        if (!animal.getStore().getId().equals(member.getStore().getId()))
            throw new InternalServerException(INTERNAL_SERVER, "Animal과 Member Store 매핑이 잘못되었습니다.");

        animal.setNickname(nickname);

        animalRepository.save(animal);

        return AnimalResponseDto.from(animal);
    }

    public List<AnimalResponseDto> findAllAnimal(Long memberId) {
        Member member = memberService.memberValidation(memberId);

        List<Animal> animalList = animalRepository.findAllByStore(member.getStore());

        return animalList.stream().map(
                animal -> {
                    if (!animal.getStore().getId().equals(member.getStore().getId()))
                        throw new InternalServerException(INTERNAL_SERVER, "Animal과 Member Store 매핑이 잘못되었습니다.");
                    return AnimalResponseDto.from(animal);
                }).toList();
    }

    public AnimalResponseDto findAnimalById(Long memberId, Long animalId) {
        Member member = memberService.memberValidation(memberId);

        Animal animal = animalRepository.findById(animalId)
                .orElseThrow(() -> new BadRequestException(ROW_DOES_NOT_EXIST, "잘못된 Animal ID 입니다."));

        if (!animal.getStore().getId().equals(member.getStore().getId()))
            throw new InternalServerException(INTERNAL_SERVER, "Animal과 Member Store 매핑이 잘못되었습니다.");

        return AnimalResponseDto.from(animal);
    }

    @Transactional
    public AnimalResponseDto feedAnimal(Long memberId, Long animalId) {
        Member member = memberService.memberValidation(memberId);

        Animal animal = animalRepository.findById(animalId)
                .orElseThrow(() -> new BadRequestException(ROW_DOES_NOT_EXIST, "잘못된 Animal ID 입니다."));

        if (!animal.getStore().getId().equals(member.getStore().getId()))
            throw new InternalServerException(INTERNAL_SERVER, "Animal과 Member Store 매핑이 잘못되었습니다.");

        animal.setHungerTimer(LocalDateTime.now().plusDays(1));

        animalRepository.save(animal);

        return AnimalResponseDto.from(animal);
    }
}
