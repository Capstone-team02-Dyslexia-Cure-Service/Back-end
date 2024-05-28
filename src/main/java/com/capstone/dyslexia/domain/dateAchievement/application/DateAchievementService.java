package com.capstone.dyslexia.domain.dateAchievement.application;

import com.capstone.dyslexia.domain.dateAchievement.domain.DateAchievement;
import com.capstone.dyslexia.domain.dateAchievement.domain.repository.DateAchievementRepository;
import com.capstone.dyslexia.domain.dateAchievement.dto.request.DateAchievementPeriodRequestDto;
import com.capstone.dyslexia.domain.dateAchievement.dto.response.DateAchievementResponseDto;
import com.capstone.dyslexia.domain.member.application.MemberService;
import com.capstone.dyslexia.domain.member.domain.Member;
import com.capstone.dyslexia.domain.question.domain.QuestionResponseType;
import com.capstone.dyslexia.domain.solvingRecord.domain.SolvingRecord;
import com.capstone.dyslexia.domain.solvingRecord.domain.repository.SolvingRecordRepository;
import com.capstone.dyslexia.global.error.ErrorCode;
import com.capstone.dyslexia.global.error.exceptions.InternalServerException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.data.domain.Pageable;
import java.time.LocalDate;
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
    public DateAchievement addSolvingRecord(SolvingRecord solvingRecord) {
        DateAchievement dateAchievement = dateAchievementRepository.findByAchievementDate(solvingRecord.getCreatedAt())
                .orElseGet(() -> DateAchievement.builder()
                        .member(solvingRecord.getMember())
                        .achievementDate(solvingRecord.getCreatedAt().toLocalDate())
                        .score(0.0D)
                        .build());

        if (!dateAchievement.getAchievementDate().equals(solvingRecord.getCreatedAt().toLocalDate())
                || !dateAchievement.getMember().getId().equals(solvingRecord.getMember().getId())) {
            throw new InternalServerException(ErrorCode.INTERNAL_SERVER,
                    String.format("SolvingRecord의 날짜(%s)와 member(%d)가 DateAchievement와 일치하지 않습니다.",
                            solvingRecord.getCreatedAt().toLocalDate(), solvingRecord.getMember().getId()));
        }

        dateAchievement.getSolvingRecordList().add(solvingRecord);

        long numSelectWord = countByType(dateAchievement, QuestionResponseType.SELECT_WORD);
        long numWriteWord = countByType(dateAchievement, QuestionResponseType.WRITE_WORD);
        long numReadWord = countByType(dateAchievement, QuestionResponseType.READ_WORD);
        long numReadSentence = countByType(dateAchievement, QuestionResponseType.READ_SENTENCE);

        long numCorrectSelectWord = countCorrectByType(dateAchievement, QuestionResponseType.SELECT_WORD);
        long numCorrectWriteWord = countCorrectByType(dateAchievement, QuestionResponseType.WRITE_WORD);
        long numCorrectReadWord = countCorrectByType(dateAchievement, QuestionResponseType.READ_WORD);
        long numCorrectReadSentence = countCorrectByType(dateAchievement, QuestionResponseType.READ_SENTENCE);

        double score = calculateScore(numSelectWord, numWriteWord, numReadWord, numReadSentence,
                numCorrectSelectWord, numCorrectWriteWord, numCorrectReadWord, numCorrectReadSentence);

        dateAchievement.setScore(score);

        dateAchievementRepository.save(dateAchievement);

        return dateAchievement;
    }

    private long countByType(DateAchievement dateAchievement, QuestionResponseType type) {
        return dateAchievement.getSolvingRecordList().stream()
                .filter(record -> record.getQuestionResponseType().equals(type))
                .count();
    }

    private long countCorrectByType(DateAchievement dateAchievement, QuestionResponseType type) {
        return dateAchievement.getSolvingRecordList().stream()
                .filter(record -> record.getQuestionResponseType().equals(type) && record.getIsCorrect())
                .count();
    }

    private double calculateScore(long numSelectWord, long numWriteWord, long numReadWord, long numReadSentence,
                                  long numCorrectSelectWord, long numCorrectWriteWord, long numCorrectReadWord, long numCorrectReadSentence) {
        return (numSelectWord == 0 ? 0 : numCorrectSelectWord / (double) numSelectWord) +
                (numWriteWord == 0 ? 0 : numCorrectWriteWord / (double) numWriteWord) +
                (numReadWord == 0 ? 0 : numCorrectReadWord / (double) numReadWord) +
                (numReadSentence == 0 ? 0 : numCorrectReadSentence / (double) numReadSentence);
    }

}
