package com.cos.cercat.domain.global;

import com.cos.cercat.common.domain.File;
import com.cos.cercat.common.domain.Image;

import java.util.List;

public interface FileUploader {

    List<Image> upload(List<File> files);

}
