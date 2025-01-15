package com.cos.cercat.domain.common;

import java.util.List;

public interface FileUploader {

  List<Image> upload(List<File> files);

  Image upload(File file);

}
