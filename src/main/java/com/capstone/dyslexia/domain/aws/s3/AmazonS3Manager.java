package com.capstone.dyslexia.domain.aws.s3;


import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.capstone.dyslexia.global.config.s3.AwsS3Config;
import com.capstone.dyslexia.global.error.exceptions.InternalServerException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;

import static com.capstone.dyslexia.global.error.ErrorCode.FILE_UPLOAD_FAIL;

@Slf4j
@Component
@RequiredArgsConstructor
public class AmazonS3Manager {

    private final AmazonS3 amazonS3;

    private final AwsS3Config awsS3Config;

    public String uploadFile(String keyName, MultipartFile file) {
        File filePath = new File(keyName);
        try {
            FileOutputStream fos = new FileOutputStream(filePath, true);
        } catch (Exception e) {
            e.printStackTrace();
        }

        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(file.getSize());
        try {
            amazonS3.putObject(new PutObjectRequest(awsS3Config.getBucket(), keyName, file.getInputStream(), objectMetadata));
        } catch (Exception e) {
            throw new InternalServerException(FILE_UPLOAD_FAIL, "S3에 file upload를 실패했습니다: " + e.getMessage());
        }

        return amazonS3.getUrl(awsS3Config.getBucket(), keyName).toString();
    }


    public String generatePronunciationKeyName() {
        return awsS3Config.getPronunciationPath() + '/';
    }

    public String generateVideoKeyName() {
        return awsS3Config.getVideoPath() + '/';
    }

    public String generateSubmissionFileKeyName(Long memberId) {
        return awsS3Config.getSubmissionAnswerFilePath() + '/' + memberId.toString() + '/';
    }
}
