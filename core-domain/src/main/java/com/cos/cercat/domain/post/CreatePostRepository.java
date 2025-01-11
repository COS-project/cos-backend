package com.cos.cercat.domain.post;


import com.cos.cercat.domain.certificate.Certificate;
import com.cos.cercat.domain.mockexam.Question;
import com.cos.cercat.domain.user.User;

import java.util.Set;

public interface CreatePostRepository {

    TargetPost saveCommentaryPost(User user,
                                  Certificate certificate,
                                  PostContent postContent,
                                  Question question);

    TargetPost saveNormalPost(User user,
                              Certificate certificate,
                              PostContent postContent);

    TargetPost saveTipPost(User user,
                           Certificate certificate,
                           PostContent postContent,
                           Set<RecommendTag> recommendTags);

    void saveComment(User user,
                     Post post,
                     CommentContent content);
}
