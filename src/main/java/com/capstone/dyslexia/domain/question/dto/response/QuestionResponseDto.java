package com.capstone.dyslexia.domain.question.dto.response;

import com.capstone.dyslexia.domain.question.domain.QuestionResponseType;
import com.capstone.dyslexia.domain.question.domain.QuestionType;
import com.capstone.dyslexia.domain.question.domain.sentence.QuestionSentence;
import com.capstone.dyslexia.domain.question.domain.word.QuestionWord;
import lombok.Builder;

public class QuestionResponseDto {

    @Builder
    public static class GetById {

        private Long id;

        private QuestionType questionType;

        private String content;

        private String pronunciationFilePath;

        private String videoPath;

    }

    public static class GetRandom {

        private Long id;

        private QuestionType questionType;

        private String content;

        private String pronunciationFilePath;

        private String videoPath;

        private QuestionResponseType questionResponseType;

        public GetRandom (QuestionWord questionWord, QuestionResponseType questionResponseType) {
            this.id = questionWord.getId();
            this.questionType = QuestionType.WORD;
            this.content = questionWord.getContent();
            this.pronunciationFilePath = null;
            this.videoPath = null;
            this.questionResponseType = questionResponseType;
        }

        public GetRandom (QuestionSentence questionSentence) {
            this.id = questionSentence.getId();
            this.questionType = QuestionType.SENTENCE;
            this.content = questionSentence.getContent();
            this.pronunciationFilePath = questionSentence.getPronunciationFilePath();
            this.videoPath = questionSentence.getVideoPath();
            this.questionResponseType = QuestionResponseType.READ_SENTENCE;
        }

    }

    @Builder
    public static class Create {

        private Long id;

        private QuestionType questionType;

        private String content;

        private String pronunciationFilePath;

        private String videoPath;

    }

}
