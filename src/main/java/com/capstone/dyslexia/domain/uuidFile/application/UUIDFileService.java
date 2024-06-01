package com.capstone.dyslexia.domain.uuidFile.application;

import com.capstone.dyslexia.domain.aws.s3.AmazonS3Manager;
import com.capstone.dyslexia.domain.member.domain.Member;
import com.capstone.dyslexia.domain.uuidFile.domain.UUIDFile;
import com.capstone.dyslexia.domain.uuidFile.domain.repository.UUIDFileRepository;
import com.capstone.dyslexia.global.util.StaticValue;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UUIDFileService {

    private final UUIDFileRepository uuidFileRepository;

    private final AmazonS3Manager amazonS3Manager;

    @Transactional
    public UUIDFile saveFile(MultipartFile file, String path, String fileExtension) {
        String uuid = UUID.randomUUID().toString();

        String fileName = path + uuid;

        if (!fileExtension.isEmpty())
            fileName = fileName + fileExtension;

        String fileS3Url = amazonS3Manager.uploadFile(fileName, file);

        UUIDFile uuidFile = UUIDFile.builder()
                .uuid(uuid)
                .fileUrl(fileS3Url)
                .build();

        uuidFileRepository.save(uuidFile);

        return uuidFile;
    }

    public UUIDFile savePronunciation(MultipartFile file) {
        return saveFile(file, amazonS3Manager.generatePronunciationKeyName(), StaticValue.wavFileExtension);
    }

    public UUIDFile saveVideo(MultipartFile file) {
        return saveFile(file, amazonS3Manager.generateVideoKeyName(), StaticValue.mp4FileExtension);
    }

    public UUIDFile saveSubmission(Member member, MultipartFile file) {
        return saveFile(file, amazonS3Manager.generateSubmissionFileKeyName(member.getId()), StaticValue.wavFileExtension);
    }
}
