package com.cos.cercat.apis.global.util;

import com.cos.cercat.domain.common.File;
import java.io.IOException;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collections;
import java.util.List;

public class FileMapper {

    public static List<File> toFiles(List<MultipartFile> files) {
        if (files == null) {

            return Collections.emptyList();
        }

        return files.stream()
                .map(FileMapper::toFile)
                .toList();
    }

    public static File toFile(MultipartFile file) {
        try {

            if (file == null) {
                return null;
            }

            return new File(
                    file.getContentType(),
                    file.getInputStream()
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
