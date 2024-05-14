package com.cos.cercat.gcs;

import com.cos.cercat.common.domain.File;
import com.cos.cercat.common.exception.CustomException;
import com.cos.cercat.common.exception.ErrorCode;
import com.cos.cercat.common.domain.Image;
import com.cos.cercat.global.FileUploader;
import com.google.api.client.util.ByteStreams;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.channels.Channels;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class GcsFileUploader implements FileUploader {

    private final Storage storage;

    @Value("${spring.cloud.gcp.storage.bucket}")
    private String bucketName;

    @Value("${spring.cloud.gcp.storage.url}")
    private String storagePath;

    @Override
    public List<Image> upload(List<File> files) {
        List<Image> images = new ArrayList<>();
        for (File file : files) {
            if (file != null) {
                images.add(uploadFile(file));
            }
        }
        return images;
    }

    @Override
    public Image upload(File file) {
        return (file != null) ? uploadFile(file) : null;
    }

    private Image uploadFile(File file) {
        String ext = file.contentType();
        String uuid = UUID.randomUUID().toString();

        BlobInfo blobInfo = BlobInfo.newBuilder(bucketName, uuid)
                .setContentType(ext)
                .build();

        try (var writer = storage.writer(blobInfo)) {
            try (var input = file.inputStream()) {
                ByteStreams.copy(input, Channels.newOutputStream(writer));
            }
            log.info("이미지 업로드 성공 - {}", storagePath + uuid);
            return Image.from(storagePath + uuid);
        } catch (IOException e) {
            log.warn("ImageEntity upload failed", e);
            throw new CustomException(ErrorCode.IMAGE_UPLOAD_FAIL_ERROR);
        }
    }
}