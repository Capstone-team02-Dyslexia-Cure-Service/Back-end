package com.capstone.dyslexia.domain.aws.s3;


import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.capstone.dyslexia.global.config.s3.AwsS3Config;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class AmazonS3Manager {

    private final AmazonS3 amazonS3;

    private final AwsS3Config awsS3Config;

    public String uploadFile(String keyName, MultipartFile file) {
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(file.getSize());
        try {
            amazonS3.putObject(new PutObjectRequest(awsS3Config.getBucket(), keyName, file.getInputStream(), objectMetadata));
        } catch (IOException e) {
            log.error("error at AmazonS3Manager uploadFile : {}", (Object) e.getStackTrace());
        }

        return amazonS3.getUrl(awsS3Config.getBucket(), keyName).toString();
    }

    /*
    public String generateSpotKeyName(Image image) {
        return amazonConfig.getSpotPath() + '/' + image.getUuid();
    }

    public String generateStoreKeyName(Image image) {
        return amazonConfig.getStorePath() + '/' + image.getUuid();
    }

    public String generateGuestBookKeyName(Image image) {
        return amazonConfig.getGuestBookPath() + '/' + image.getUuid();
    }
     */
}
