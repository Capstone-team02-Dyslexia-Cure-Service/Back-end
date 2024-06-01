package com.capstone.dyslexia.domain.solvingRecord.dto.ml;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GradeMLRequest {

    @Getter
    @Builder
    public static class Request {
        private String questionResponseType;    // 어느 문제 타입인지(SELECT_WORD, WRITE_WORD, READ_WORD, READ_SENTENCE), not null

        private String memberSubmissionString;  // 사용자가 입력한 답안, nullable

        private String memberSubmissionAnswerFilePath;  // 사용자가 녹음한 음성, nullable

        private String questionContent; // 문제 원본 답안, not null

        private String questionPronunciationFilePath; // 문제 원본 발음 s3 엔드포인트 url, nullable
    }

}
