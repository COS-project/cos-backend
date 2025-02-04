package com.cos.cercat.domain.post;

import com.cos.cercat.domain.common.FileUploader;
import com.cos.cercat.domain.certificate.Certificate;
import com.cos.cercat.domain.certificate.CertificateReader;
import com.cos.cercat.domain.certificate.TargetCertificate;
import com.cos.cercat.domain.common.File;
import com.cos.cercat.domain.common.Image;
import com.cos.cercat.domain.user.PermissionValidator;
import com.cos.cercat.domain.user.TargetUser;
import com.cos.cercat.domain.user.User;
import com.cos.cercat.domain.user.UserReader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class PostService {

    private final PostAppender postAppender;
    private final PermissionValidator permissionValidator;
    private final PostUpdater postUpdater;
    private final PostRemover postRemover;
    private final FileUploader fileUploader;
    private final UserReader userReader;
    private final PostReader postReader;
    private final CertificateReader certificateReader;

    public TargetPost createPost(
            TargetUser targetUser,
            TargetCertificate targetCertificate,
            NewPost newPost,
            List<File> uploadFiles
    ) {
      User user = userReader.read(targetUser);
      Certificate certificate = certificateReader.read(targetCertificate);
      List<Image> images = fileUploader.upload(uploadFiles);
      return postAppender.append(user, certificate, newPost, images);
    }

    public TargetPost updatePost(TargetUser targetUser,
            TargetPost targetPost,
            UpdatedPost updatedPost,
            List<File> uploadImages) {
      Post post = postReader.read(targetPost);
      User user = userReader.read(targetUser);
      permissionValidator.validate(post, user);
      List<Image> images = fileUploader.upload(uploadImages);
      postUpdater.update(post, updatedPost, images);
      return targetPost;
    }

  public void deletePost(TargetUser targetUser, TargetPost targetPost) {
    User user = userReader.read(targetUser);
    Post post = postReader.read(targetPost);
    permissionValidator.validate(post, user);
    postRemover.remove(post);
  }
}
