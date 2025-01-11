package com.cos.cercat.common.domain;

import java.util.List;

public interface FileUploader {

  List<Image> upload(List<File> files);

  Image upload(File file);

}
