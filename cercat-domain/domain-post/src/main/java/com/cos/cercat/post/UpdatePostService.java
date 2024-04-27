package com.cos.cercat.post;

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
    private final MockExamReader mockExamReader;
    private final PermissionValidator permissionValidator;
    private final FileUploader fileUploader;

    public TargetPost updateCommentaryPost(TargetUser targetUser,
                                           TargetPost targetPost,
                                           TargetCertificate targetCertificate,
                                           PostContent postContent,
                                           MockExamSession mockExamSession,
                                           int questionSequence,
                                           List<Long> removedImageIds,
                                           List<File> uploadImages) {
        CommentaryPost commentaryPost = (CommentaryPost) postReader.read(targetPost);
        User user = userReader.read(targetUser);
        Question question = mockExamReader.readQuestion(targetCertificate, mockExamSession, questionSequence);
        permissionValidator.validate(commentaryPost, user);
        List<Image> images = fileUploader.upload(uploadImages);
        postContent.addImages(images);
        commentaryPost.update(postContent, question);
        postUpdator.update(commentaryPost, removedImageIds);
        return targetPost;
    }

    public TargetPost updateTipPost(TargetUser targetUser,
                                    TargetPost targetPost,
                                    PostContent postContent,
                                    Set<RecommendTag> newRecommendTags,
                                    List<Long> removedImageIds,
                                    List<File> uploadImages) {
        TipPost tipPost = (TipPost) postReader.read(targetPost);
        User user = userReader.read(targetUser);
        permissionValidator.validate(tipPost, user);
        List<Image> images = fileUploader.upload(uploadImages);
        postContent.addImages(images);
        tipPost.update(postContent, newRecommendTags);
        postUpdator.update(tipPost, removedImageIds);
        return targetPost;
    }

    public TargetPost updateNormalPost(TargetUser targetUser,
                                       TargetPost targetPost,
                                       PostContent postContent,
                                       List<Long> removedImageIds,
                                       List<File> uploadImages) {
        Post post = postReader.read(targetPost);
        User user = userReader.read(targetUser);
        permissionValidator.validate(post, user);
        List<Image> images = fileUploader.upload(uploadImages);
        postContent.addImages(images);
        post.update(postContent);
        postUpdator.update(post, removedImageIds);
        return targetPost;
    }

}
