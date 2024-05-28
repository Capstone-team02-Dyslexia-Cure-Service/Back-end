package com.capstone.dyslexia.domain.store.application;

import com.capstone.dyslexia.domain.animal.domain.Animal;
import com.capstone.dyslexia.domain.animal.domain.repository.AnimalRepository;
import com.capstone.dyslexia.domain.animal.dto.response.AnimalResponseDto;
import com.capstone.dyslexia.domain.member.domain.Member;
import com.capstone.dyslexia.domain.member.domain.repository.MemberRepository;
import com.capstone.dyslexia.domain.store.domain.Store;
import com.capstone.dyslexia.domain.store.domain.repository.StoreRepository;
import com.capstone.dyslexia.domain.store.dto.request.StoreResponseDto;
import com.capstone.dyslexia.global.error.exceptions.BadRequestException;
import com.capstone.dyslexia.global.error.exceptions.InternalServerException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static com.capstone.dyslexia.global.error.ErrorCode.INTERNAL_SERVER;
import static com.capstone.dyslexia.global.error.ErrorCode.ROW_DOES_NOT_EXIST;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StoreService {

    private final MemberRepository memberRepository;
    private final StoreRepository storeRepository;
    private final AnimalRepository animalRepository;

    public StoreResponseDto findAllOwnedAnimal(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new BadRequestException(ROW_DOES_NOT_EXIST, "잘못된 Member ID 입니다."));

        Store store =  storeRepository.findById(member.getStore().getId())
                .orElseThrow(() -> new InternalServerException(INTERNAL_SERVER, "사용자에 할당된 상점이 없습니다. 서버 관리자에게 문의 주세요."));

        List<Animal> animalList = animalRepository.findAllByStore(store);
        List<AnimalResponseDto> animalResponseDtoList= new ArrayList<>();
        for (Animal animal : animalList) {
            animalResponseDtoList.add(AnimalResponseDto.from(animal));
        }

        return StoreResponseDto.builder()
                .storeId(store.getId())
                .memberId(member.getId())
                .animalResponseDtoList(animalResponseDtoList)
                .createdAt(store.getCreatedAt())
                .updatedAt(store.getUpdatedAt())
                .build();
    }
}
