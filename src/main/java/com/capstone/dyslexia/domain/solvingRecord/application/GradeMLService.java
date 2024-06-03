package com.capstone.dyslexia.domain.solvingRecord.application;

import com.capstone.dyslexia.domain.solvingRecord.dto.ml.GradeMLRequest;
import com.capstone.dyslexia.domain.solvingRecord.dto.ml.GradeMLResponse;
import com.capstone.dyslexia.global.config.flaskAPI.FlaskAPIConfig;
import com.capstone.dyslexia.global.error.ErrorCode;
import com.capstone.dyslexia.global.error.exceptions.InternalServerException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

@Service
public class GradeMLService {

    private static final Logger logger = LoggerFactory.getLogger(GradeMLService.class);

    private final RestTemplate restTemplate = new RestTemplate();

    private final String flaskAPIUrl;

    public GradeMLService(FlaskAPIConfig flaskAPIConfig) {
        this.flaskAPIUrl = flaskAPIConfig.getFlaskAPIUrl();
    }

    private HttpHeaders createHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }

    public GradeMLResponse.Word gradeWriteWordML(GradeMLRequest.Write gradeMLRequestWriteWord) {
        HttpEntity<GradeMLRequest.Write> requestEntity = new HttpEntity<>(gradeMLRequestWriteWord, createHeaders());
        ResponseEntity<GradeMLResponse.Word> response;
        try {
            response = restTemplate.exchange(
                    flaskAPIUrl + "/write/word",
                    HttpMethod.POST,
                    requestEntity,
                    GradeMLResponse.Word.class
            );
        } catch (HttpClientErrorException e) {
            logger.error("Error during API call: {}", e.getResponseBodyAsString());
            throw new InternalServerException(ErrorCode.INTERNAL_SERVER, "ML 서버와 통신 중 오류가 발생했습니다.");
        }

        logger.info("Response Headers: {}", response.getHeaders());
        logger.info("Response Body: {}", response.getBody());

        if (response.getBody() == null) {
            throw new InternalServerException(ErrorCode.INTERNAL_SERVER, "ML 서버와 통신 중 오류가 발생했습니다.");
        }

        return response.getBody();
    }


    public GradeMLResponse.Word gradeReadWordML(GradeMLRequest.ReadWord gradeMLrequestReadWord) {
        HttpEntity<GradeMLRequest.ReadWord> requestEntity = new HttpEntity<>(gradeMLrequestReadWord, createHeaders());
        ResponseEntity<GradeMLResponse.Word> response;
        try {
            response = restTemplate.exchange(
                    flaskAPIUrl + "/read/word",
                    HttpMethod.POST,
                    requestEntity,
                    GradeMLResponse.Word.class
            );
        } catch (HttpClientErrorException e) {
            throw new InternalServerException(ErrorCode.INTERNAL_SERVER, "ML 서버와 통신 중 오류가 발생했습니다.");
        }

        if (response.getBody() == null) {
            throw new InternalServerException(ErrorCode.INTERNAL_SERVER, "ML 서버와 통신 중 오류가 발생했습니다.");
        }

        return response.getBody();
    }

    public GradeMLResponse.ReadSentence gradeReadSentenceML(GradeMLRequest.ReadSentence gradeMLrequestReadSentence) {
        HttpEntity<GradeMLRequest.ReadSentence> requestEntity = new HttpEntity<>(gradeMLrequestReadSentence, createHeaders());
        ResponseEntity<GradeMLResponse.ReadSentence> response;
        try {
            response = restTemplate.exchange(
                    flaskAPIUrl + "/read/sentence",
                    HttpMethod.POST,
                    requestEntity,
                    GradeMLResponse.ReadSentence.class
            );
        } catch (HttpClientErrorException e) {
            throw new InternalServerException(ErrorCode.INTERNAL_SERVER, "ML 서버와 통신 중 오류가 발생했습니다.");
        }

        if (response.getBody() == null) {
            throw new InternalServerException(ErrorCode.INTERNAL_SERVER, "ML 서버와 통신 중 오류가 발생했습니다.");
        }

        return response.getBody();
    }
}