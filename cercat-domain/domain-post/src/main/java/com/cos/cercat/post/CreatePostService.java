package com.cos.cercat.post;

import com.cos.cercat.alarm.AlarmArg;
import com.cos.cercat.alarm.AlarmEvent;
import com.cos.cercat.alarm.AlarmSender;
import com.cos.cercat.alarm.AlarmType;
import com.cos.cercat.certificate.Certificate;
import com.cos.cercat.certificate.CertificateReader;
import com.cos.cercat.certificate.TargetCertificate;
import com.cos.cercat.common.domain.File;
import com.cos.cercat.common.domain.Image;
import com.cos.cercat.global.FileUploader;
import com.cos.cercat.mockexam.MockExamReader;
import com.cos.cercat.mockexam.MockExamSession;
import com.cos.cercat.mockexam.Question;
import com.cos.cercat.user.TargetUser;
import com.cos.cercat.user.User;
import com.cos.cercat.user.UserReader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;


@Service
@RequiredArgsConstructor
public class CreatePostService {

    private final PostAppender postAppender;
    private final PostValidator postValidator;
    private final FileUploader fileUploader;
    private final AlarmSender alarmSender;
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

        if (commentContent.hasParent()) {
            postValidator.validate(TargetComment.from(commentContent.parentId()), post);
            commentAppender.appendChild(user, post, commentContent);
            return;
        }
        commentAppender.append(user, post, commentContent);
    }
}
