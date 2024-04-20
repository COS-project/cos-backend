package com.cos.cercat.gcs;

import com.cos.cercat.entity.Image;
import com.cos.cercat.common.exception.CustomException;
import com.cos.cercat.common.exception.ErrorCode;
import com.google.api.client.util.ByteStreams;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.channels.Channels;
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

    public List<String> uploadFileInStorage2(List<MultipartFile> files) {
        List<String> imageUrls = new ArrayList<>();

        if (files == null) {
            return imageUrls;
        }

        for (MultipartFile file : files) {
            String ext = file.getContentType();
            String uuid = UUID.randomUUID().toString();

            BlobInfo blobInfo = BlobInfo.newBuilder(bucketName, uuid)
                    .setContentType(ext)
                    .build();

            try (var writer = storage.writer(blobInfo)) {
                try (var input = file.getInputStream()) {
                    ByteStreams.copy(input, Channels.newOutputStream(writer));
                }
                imageUrls.add(storagePath + uuid);
                log.info("이미지 업로드 성공 - {}", storagePath + uuid);
            } catch (IOException e) {
                log.warn("Image upload failed", e);
                throw new CustomException(ErrorCode.IMAGE_UPLOAD_FAIL_ERROR);
            }
        }
        return imageUrls;
    }

    public List<Image> uploadFileInStorage1(List<MultipartFile> files) {
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

            try (var writer = storage.writer(blobInfo)) {
                try (var input = file.getInputStream()) {
                    ByteStreams.copy(input, Channels.newOutputStream(writer));
                }
                images.add(Image.from(storagePath + uuid));
                log.info("이미지 업로드 성공 - {}", storagePath + uuid);
            } catch (IOException e) {
                log.warn("Image upload failed", e);
                throw new CustomException(ErrorCode.IMAGE_UPLOAD_FAIL_ERROR);
            }
        }
        return images;
    }
}