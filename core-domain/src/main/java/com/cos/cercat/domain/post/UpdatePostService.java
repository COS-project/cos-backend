package com.cos.cercat.domain.post;

import com.cos.cercat.common.domain.File;
import com.cos.cercat.common.domain.FileUploader;
import com.cos.cercat.common.domain.Image;
import com.cos.cercat.domain.user.PermissionValidator;
import com.cos.cercat.domain.user.TargetUser;
import com.cos.cercat.domain.user.User;
import com.cos.cercat.domain.user.UserReader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UpdatePostService {

  private final UserReader userReader;
  private final PostReader postReader;
  private final PostUpdator postUpdator;
  private final PermissionValidator permissionValidator;
  private final FileUploader fileUploader;

  public TargetPost updatePost(TargetUser targetUser,
      TargetPost targetPost,
      UpdatedPost updatedPost,
      List<File> uploadImages) {
    Post post = postReader.read(targetPost);
    User user = userReader.read(targetUser);
    permissionValidator.validate(post, user);
    List<Image> images = fileUploader.upload(uploadImages);
    updatedPost.content().addImages(images);
    postUpdator.update(post, updatedPost);
    return targetPost;
  }

}
