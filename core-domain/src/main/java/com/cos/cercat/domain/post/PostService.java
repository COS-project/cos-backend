package com.cos.cercat.domain.post;

import com.cos.cercat.domain.common.FileUploader;
import com.cos.cercat.domain.certificate.Certificate;
import com.cos.cercat.domain.certificate.CertificateReader;
import com.cos.cercat.domain.certificate.CertificateId;
import com.cos.cercat.domain.common.File;
import com.cos.cercat.domain.common.Image;
import com.cos.cercat.domain.user.PermissionValidator;
import com.cos.cercat.domain.user.UserId;
import com.cos.cercat.domain.user.User;
import com.cos.cercat.domain.user.UserReader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import org.springframework.transaction.annotation.Transactional;


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

    @Transactional
    public PostId createPost(
            UserId userId,
            CertificateId certificateId,
            NewPost newPost,
            List<File> uploadFiles
    ) {
      User user = userReader.read(userId);
      Certificate certificate = certificateReader.read(certificateId);
      List<Image> images = fileUploader.upload(uploadFiles);
      return postAppender.append(user, certificate, newPost, images);
    }

    public PostId updatePost(UserId userId,
            PostId postId,
            UpdatedPost updatedPost,
            List<File> uploadImages) {
      Post post = postReader.read(postId);
      User user = userReader.read(userId);
      permissionValidator.validate(post, user);
      List<Image> images = fileUploader.upload(uploadImages);
      postUpdater.update(post, updatedPost, images);
      return postId;
    }

    @Transactional
  public void deletePost(UserId userId, PostId postId) {
    User user = userReader.read(userId);
    Post post = postReader.read(postId);
    permissionValidator.validate(post, user);
    postRemover.remove(post);
  }
}
