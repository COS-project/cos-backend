package com.cos.cercat.post;

import com.cos.cercat.certificate.Certificate;
import com.cos.cercat.certificate.CertificateReader;
import com.cos.cercat.certificate.TargetCertificate;
import com.cos.cercat.common.domain.File;
import com.cos.cercat.common.domain.Image;
import com.cos.cercat.global.FileUploader;
import com.cos.cercat.mockexam.MockExamReader;
import com.cos.cercat.mockexam.MockExamSession;
import com.cos.cercat.mockexam.Question;
import com.cos.cercat.user.PermissionValidator;
import com.cos.cercat.user.TargetUser;
import com.cos.cercat.user.User;
import com.cos.cercat.user.UserReader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

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
