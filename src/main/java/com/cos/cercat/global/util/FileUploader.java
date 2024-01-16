package com.cos.cercat.global.util;

import com.cos.cercat.global.entity.Image;
import com.cos.cercat.global.exception.CustomException;
import com.cos.cercat.global.exception.ErrorCode;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class FileUploader {

    private final Storage storage;

    @Value("${spring.cloud.gcp.storage.bucket}")
    private String bucketName;

    @Value("${spring.cloud.gcp.storage.url}")
    private String storagePath;

    public List<Image> uploadFileInStorage(List<MultipartFile> files) {
        List<Image> images = new ArrayList<>();

        if (files == null) {
            return images;
        }

        for (MultipartFile file : files) {
            String ext = file.getContentType();
            String uuid = UUID.randomUUID().toString();

            BlobInfo blobInfo = BlobInfo.newBuilder(bucketName, uuid)
                    .setContentType(ext)
                    .build();

            try {
                Blob blob = storage.create(blobInfo, file.getInputStream());
                images.add(Image.from(storagePath + uuid));
            } catch (IOException e) {
                log.warn("image Upload fail");
                throw new CustomException(ErrorCode.IMAGE_UPLOAD_FAIL_ERROR);
            }
        }
        return images;
    }
}