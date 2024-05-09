package com.cos.cercat.post;

import com.cos.cercat.certificate.Certificate;
import com.cos.cercat.certificate.CertificateReader;
import com.cos.cercat.certificate.TargetCertificate;
import com.cos.cercat.common.domain.File;
import com.cos.cercat.common.domain.Image;
import com.cos.cercat.global.FileUploader;
import com.cos.cercat.user.TargetUser;
import com.cos.cercat.user.User;
import com.cos.cercat.user.UserReader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class CreatePostService {

    private final PostAppender postAppender;
    private final FileUploader fileUploader;
    private final UserReader userReader;
    private final PostReader postReader;
    private final CertificateReader certificateReader;
    private final CommentAppender commentAppender;

    public TargetPost createPost(TargetUser targetUser,
                                 TargetCertificate targetCertificate,
                                 NewPost newPost,
                                 List<File> uploadFiles) {
        User user = userReader.read(targetUser);
        Certificate certificate = certificateReader.read(targetCertificate);
        List<Image> images = fileUploader.upload(uploadFiles);
        newPost.content().addImages(images);
        return postAppender.append(user, certificate, newPost);
    }

    public void createPostComment(TargetUser targetUser,
                                  TargetPost targetPost,
                                  CommentContent commentContent) {
        User user = userReader.read(targetUser);
        Post post = postReader.read(targetPost);
        commentAppender.append(user, post, commentContent);
    }
}
