package com.capstone.dyslexia.domain.question.dto.response;

import com.capstone.dyslexia.domain.question.domain.QuestionResponseType;
import com.capstone.dyslexia.domain.question.domain.QuestionType;
import com.capstone.dyslexia.domain.question.domain.sentence.QuestionSentence;
import com.capstone.dyslexia.domain.question.domain.word.QuestionWord;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

public class QuestionResponseDto {

    @Getter
    @Builder(access = AccessLevel.PRIVATE)
    public static class Find {

        private Long id;

        private QuestionType questionType;

        private String content;

        private String pronunciationFilePath;

        private String videoPath;

        public static Find from(QuestionWord questionWord) {
            return Find.builder()
                    .id(questionWord.getId())
                    .questionType(QuestionType.WORD)
                    .content(questionWord.getContent())
                    .pronunciationFilePath(null)
                    .videoPath(questionWord.getVideoPath())
                    .build();
        }

        public static Find from(QuestionSentence questionSentence) {
            return Find.builder()
                    .id(questionSentence.getId())
                    .questionType(QuestionType.SENTENCE)
                    .content(questionSentence.getContent())
                    .pronunciationFilePath(questionSentence.getPronunciationFilePath())
                    .videoPath(questionSentence.getVideoPath())
                    .build();
        }
    }

    @Getter
    @Builder(access = AccessLevel.PRIVATE)
    public static class GetRandom {

        private Long id;

        private QuestionType questionType;

        private String content;

        private String pronunciationFilePath;

        private String videoPath;

        private QuestionResponseType questionResponseType;

        public static GetRandom from(QuestionWord questionWord, QuestionResponseType questionResponseType) {
            return GetRandom.builder()
                    .id(questionWord.getId())
                    .questionType(QuestionType.WORD)
                    .content(questionWord.getContent())
                    .pronunciationFilePath(null)
                    .videoPath(questionWord.getVideoPath())
                    .questionResponseType(questionResponseType)
                    .build();
        }

        public static GetRandom from(QuestionSentence questionSentence) {
            return GetRandom.builder()
                    .id(questionSentence.getId())
                    .questionType(QuestionType.SENTENCE)
                    .content(questionSentence.getContent())
                    .pronunciationFilePath(questionSentence.getPronunciationFilePath())
                    .videoPath(questionSentence.getVideoPath())
                    .questionResponseType(QuestionResponseType.READ_SENTENCE)
                    .build();
        }
    }

    @Getter
    @Builder(access = AccessLevel.PRIVATE)
    public static class CreateWord {
        private Long id;
        private String content;

        public static CreateWord from(QuestionWord questionWord) {
            return CreateWord.builder()
                    .id(questionWord.getId())
                    .content(questionWord.getContent())
                    .build();
        }
    }

    @Getter
    @Builder(access = AccessLevel.PRIVATE)
    public static class CreateSentence {
        private Long id;
        private String content;
        private String pronunciationFilePath;
        private String videoPath;

        public static CreateSentence from(QuestionSentence questionSentence) {
            return CreateSentence.builder()
                    .id(questionSentence.getId())
                    .content(questionSentence.getContent())
                    .pronunciationFilePath(questionSentence.getPronunciationFilePath())
                    .videoPath(questionSentence.getVideoPath())
                    .build();
        }
    }

}
