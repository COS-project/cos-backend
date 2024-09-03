package com.cos.cercat.domain.user;

import com.cos.cercat.common.domain.File;
import com.cos.cercat.common.domain.FileUploader;
import com.cos.cercat.common.domain.Image;
import java.util.List;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("batch")
public class NoOpFileUploader implements FileUploader {

    @Override
    public List<Image> upload(List<File> files) {
        return List.of();
    }

    @Override
    public Image upload(File file) {
        return null;
    }
}
