package com.cos.cercat.apis.global.util;

import com.cos.cercat.common.domain.File;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collections;
import java.util.List;

public class FileMapper {

    public static List<File> toFiles(List<MultipartFile> files) {
        if (files == null) {
            return Collections.emptyList();
        }

        return files.stream()
                .map(File::from)
                .toList();
    }

}
