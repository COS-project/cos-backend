package com.cos.cercat.domain.post;

import com.cos.cercat.common.domain.File;
import com.cos.cercat.common.domain.Image;
import com.cos.cercat.domain.alarm.AlarmArg;
import com.cos.cercat.domain.alarm.AlarmEvent;
import com.cos.cercat.domain.alarm.AlarmSender;
import com.cos.cercat.domain.alarm.AlarmType;
import com.cos.cercat.domain.certificate.TargetCertificate;
import com.cos.cercat.domain.global.FileUploader;
import com.cos.cercat.domain.mockexam.MockExamSession;
import com.cos.cercat.domain.mockexam.Question;
import com.cos.cercat.domain.mockexam.MockExamReader;
import com.cos.cercat.domain.user.TargetUser;
import com.cos.cercat.domain.user.User;
import com.cos.cercat.domain.user.UserReader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;


@Service
@RequiredArgsConstructor
public class CreatePostService {

    private final PostAppender postAppender;
    private final PostValidator postValidator;
    private final MockExamReader mockExamReader;
    private final FileUploader fileUploader;
    private final AlarmSender alarmSender;
    private final UserReader userReader;
    private final PostReader postReader;

    public TargetPost createCommentaryPost(TargetUser targetUser,
                                           TargetCertificate targetCertificate,
                                           PostContent postContent,
                                           MockExamSession mockExamSession,
                                           int questionSequence,
                                           List<File> uploadFiles) {
        List<Image> images = fileUploader.upload(uploadFiles);
        postContent.addImages(images);
        Question question = mockExamReader.readQuestion(targetCertificate, mockExamSession, questionSequence);
        return postAppender.appendCommentaryPost(targetUser, targetCertificate, postContent, question);
    }

    public TargetPost createNormalPost(TargetUser targetUser,
                                       TargetCertificate targetCertificate,
                                       PostContent postContent,
                                       List<File> uploadFiles) {
        List<Image> images = fileUploader.upload(uploadFiles);
        postContent.addImages(images);
        return postAppender.appendNormalPost(targetUser, targetCertificate, postContent);
    }

    public TargetPost createTipPost(TargetUser targetUser,
                              TargetCertificate targetCertificate,
                              PostContent postContent,
                              Set<RecommendTag> recommendTags,
                                    List<File> uploadFiles) {
        List<Image> images = fileUploader.upload(uploadFiles);
        postContent.addImages(images);
        return postAppender.appendTipPost(targetUser, targetCertificate, postContent, recommendTags);
    }

    public void createPostComment(TargetUser targetUser,
                                  TargetPost targetPost,
                                  CommentContent content) {
        User user = userReader.read(targetUser);
        Post post = postReader.read(targetPost);

        if (content.hasParent()) {
            postValidator.validate(TargetComment.from(content.parentId()), targetPost);
            postAppender.appendComment(targetUser, targetPost, content);
            AlarmEvent commentAlarm = createAlarm(user, post, AlarmType.NEW_COMMENT_ON_POST);
            AlarmEvent replayAlarm = createAlarm(user, post, AlarmType.REPLY_ON_COMMENT);
            alarmSender.send(commentAlarm);
            alarmSender.send(replayAlarm);
            return;
        }
        postAppender.appendComment(targetUser, targetPost, content);
        AlarmEvent alarm = createAlarm(user, post, AlarmType.NEW_COMMENT_ON_POST);
        alarmSender.send(alarm);
    }

    private AlarmEvent createAlarm(User user, Post post, AlarmType alarmType) {
        return AlarmEvent.of(user, AlarmArg.of(user, post.getId()), alarmType);
    }
}
