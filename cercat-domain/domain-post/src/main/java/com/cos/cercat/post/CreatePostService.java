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
                                  CommentContent content) {
        User user = userReader.read(targetUser);
        Post post = postReader.read(targetPost);

        if (content.hasParent()) {
            postValidator.validate(TargetComment.from(content.parentId()), post);
            postAppender.appendComment(user, post, content);
            AlarmEvent commentAlarm = createAlarm(user, post, AlarmType.NEW_COMMENT_ON_POST);
            AlarmEvent replayAlarm = createAlarm(user, post, AlarmType.REPLY_ON_COMMENT);
            alarmSender.send(commentAlarm);
            alarmSender.send(replayAlarm);
            return;
        }
        postAppender.appendComment(user, post, content);
        AlarmEvent alarm = createAlarm(user, post, AlarmType.NEW_COMMENT_ON_POST);
        alarmSender.send(alarm);
    }

    private AlarmEvent createAlarm(User user, Post post, AlarmType alarmType) {
        return AlarmEvent.of(user, AlarmArg.of(user, post.getId()), alarmType);
    }
}
