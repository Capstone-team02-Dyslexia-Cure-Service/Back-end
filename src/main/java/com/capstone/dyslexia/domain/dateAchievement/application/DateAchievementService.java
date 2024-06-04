package com.capstone.dyslexia.domain.dateAchievement.application;

import com.capstone.dyslexia.domain.dateAchievement.domain.DateAchievement;
import com.capstone.dyslexia.domain.dateAchievement.domain.repository.DateAchievementRepository;
import com.capstone.dyslexia.domain.dateAchievement.dto.request.DateAchievementPeriodRequestDto;
import com.capstone.dyslexia.domain.dateAchievement.dto.response.DateAchievementResponseDto;
import com.capstone.dyslexia.domain.member.application.MemberService;
import com.capstone.dyslexia.domain.member.domain.Member;
import com.capstone.dyslexia.domain.question.domain.QuestionResponseType;
import com.capstone.dyslexia.domain.solvingRecord.domain.SolvingRecord;
import com.capstone.dyslexia.global.error.ErrorCode;
import com.capstone.dyslexia.global.error.exceptions.InternalServerException;
import com.capstone.dyslexia.global.util.StaticValue;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.data.domain.Pageable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DateAchievementService {

    private final DateAchievementRepository dateAchievementRepository;

    private final MemberService memberService;

    public List<DateAchievementResponseDto.Find> findAllByMemberId(Long memberId, Pageable pageable) {
        Member member = memberService.memberValidation(memberId);

        return dateAchievementRepository.findAllByMember(member, pageable).stream()
                .map(
                        dateAchievement -> {
                            if (dateAchievement.getSolvingRecordList().isEmpty()) {
                                return null;
                            }
                            return DateAchievementResponseDto.Find.from(dateAchievement);
                        })
                .toList();
    }

    public DateAchievementResponseDto.Find findByDate(Long memberId, LocalDate date) {
        Member member = memberService.memberValidation(memberId);

        List<DateAchievement> dateAchievementList = dateAchievementRepository.findByMemberAndAchievementDate(member, date);
        if (dateAchievementList.size() > 1)
            throw new InternalServerException(ErrorCode.INTERNAL_SERVER, "해당 날짜에 대한 성취도 기록이 여러개 존재합니다.");
        if (dateAchievementList.isEmpty()) {
            return null;
        }

        return DateAchievementResponseDto.Find.from(dateAchievementList.get(0));
    }

    public List<DateAchievementResponseDto.Find> findByPeriod(Long memberId, Pageable pageable, DateAchievementPeriodRequestDto.FindByPeriod dateAchievementPeriodRequestDto) {
        Member member = memberService.memberValidation(memberId);

        Page<DateAchievement> dateAchievementList =
                dateAchievementRepository.findByMemberAndAchievementDateBetween(member, dateAchievementPeriodRequestDto.getStartDate(), dateAchievementPeriodRequestDto.getEndDate(), pageable);

        if (dateAchievementList.isEmpty()) {
            return null;
        }

        return dateAchievementList.stream()
                .map(DateAchievementResponseDto.Find::from)
                .toList();
    }

    @Transactional
    public List<DateAchievement> addSolvingRecord(List<SolvingRecord> solvingRecordList) {

        // 초기화된 dateAchievementList 생성
        List<DateAchievement> dateAchievementList = new ArrayList<>();

        // solvingRecordList의 각 날짜에 해당하는 DateAchievement를 처리
        for (SolvingRecord solvingRecord : solvingRecordList) {
            LocalDate achievementDate = solvingRecord.getCreatedAt().toLocalDate();

            DateAchievement dateAchievement = dateAchievementRepository.findByAchievementDateAndMember(achievementDate, solvingRecord.getMember())
                    .orElseGet(() -> {
                        return DateAchievement.builder()
                                .member(solvingRecord.getMember())
                                .achievementDate(achievementDate)
                                .score(0.0D)
                                .solvingRecordList(new ArrayList<>())
                                .build();
                    });

            if (dateAchievementList.stream().noneMatch(da -> da.getAchievementDate().equals(dateAchievement.getAchievementDate()))) {
                dateAchievementList.add(dateAchievement);
            }
        }

        for (SolvingRecord solvingRecord : solvingRecordList) {
            LocalDate achievementDate = solvingRecord.getCreatedAt().toLocalDate();

            // dateAchievementList에서 해당 날짜의 DateAchievement 찾기
            DateAchievement dateAchievement = dateAchievementList.stream()
                    .filter(da -> da.getAchievementDate().equals(achievementDate))
                    .findFirst().orElseThrow(() -> new InternalServerException(ErrorCode.INTERNAL_SERVER, "해당 날짜에 대한 성취도 기록 생성이 실패했습니다."));

            // 해당 날짜의 DateAchievement에 solvingRecord 추가
            dateAchievement.getSolvingRecordList().add(solvingRecord);
        }

        for (DateAchievement dateAchievement : dateAchievementList) {
            long numSelectWord = countByType(dateAchievement, QuestionResponseType.SELECT_WORD);
            long numWriteWord = countByType(dateAchievement, QuestionResponseType.WRITE_WORD);
            long numReadWord = countByType(dateAchievement, QuestionResponseType.READ_WORD);
            long numReadSentence = countByType(dateAchievement, QuestionResponseType.READ_SENTENCE);

            long numCorrectSelectWord = numSelectWord == 0 ? 0 : countCorrectByType(dateAchievement, QuestionResponseType.SELECT_WORD);
            long numCorrectWriteWord = numWriteWord == 0 ? 0 : countCorrectByType(dateAchievement, QuestionResponseType.WRITE_WORD);
            long numCorrectReadWord = numReadWord == 0 ? 0 : countCorrectByType(dateAchievement, QuestionResponseType.READ_WORD);
            long numCorrectReadSentence = numReadSentence == 0 ? 0 : countCorrectByType(dateAchievement, QuestionResponseType.READ_SENTENCE);

            double score = calculateScore(numSelectWord, numWriteWord, numReadWord, numReadSentence,
                    numCorrectSelectWord, numCorrectWriteWord, numCorrectReadWord, numCorrectReadSentence);

            dateAchievement.setScore(score);
        }

        dateAchievementList = dateAchievementRepository.saveAll(dateAchievementList);

        memberService.updateMemberLevelByDate(dateAchievementList.get(0).getMember(), StaticValue.updateMemberLevelPeriod);

        return dateAchievementList;
    }

    private long countByType(DateAchievement dateAchievement, QuestionResponseType type) {
        return dateAchievement.getSolvingRecordList().stream()
                .filter(record -> record.getQuestionResponseType().equals(type))
                .count();
    }

    private long countCorrectByType(DateAchievement dateAchievement, QuestionResponseType type) {
        return dateAchievement.getSolvingRecordList().stream()
                .filter(record -> record.getQuestionResponseType().equals(type) && Boolean.TRUE.equals(record.getIsCorrect()))
                .count();
    }

    private double calculateScore(long numSelectWord, long numWriteWord, long numReadWord, long numReadSentence,
                                  long numCorrectSelectWord, long numCorrectWriteWord, long numCorrectReadWord, long numCorrectReadSentence) {
        return (numSelectWord == 0 ? 0 : numCorrectSelectWord / (double) numSelectWord) +
                (numWriteWord == 0 ? 0 : 3 * numCorrectWriteWord / (double) numWriteWord) +
                (numReadWord == 0 ? 0 : 9 * numCorrectReadWord / (double) numReadWord) +
                (numReadSentence == 0 ? 0 : 27 * numCorrectReadSentence / (double) numReadSentence);
    }

}
