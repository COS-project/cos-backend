package com.cos.cercat.post;

import com.cos.cercat.certificate.TargetCertificate;
import com.cos.cercat.common.domain.Cursor;
import com.cos.cercat.common.domain.SliceResult;
import com.cos.cercat.user.TargetUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReadPostService {

    private final PostReader postReader;
    private final CommentReader commentReader;
    private final PostSearcher postSearcher;

    @Transactional(readOnly = true)
    public SliceResult<Post> searchCommentaryPost(TargetCertificate targetCertificate,
                                                  CommentaryPostSearchCond commentaryPostSearchCond,
                                                  Cursor cursor) {
        return postSearcher.search(targetCertificate, commentaryPostSearchCond, cursor);
    }

    public PostWithComments readDetail(TargetPost targetPost) {
        return postReader.readDetail(targetPost);
    }

    public List<Post> readTop3TipPosts(TargetCertificate targetCertificate) {
        return postReader.readTop3TipPosts(targetCertificate);
    }

    public SliceResult<Post> readMyPosts(TargetUser targetUser,
                                         PostType postType,
                                         Cursor cursor) {
        return postReader.readMyPosts(targetUser, postType, cursor);
    }

    public SliceResult<Post> readCommentingPosts(TargetUser targetUser,
                                                Cursor cursor) {
        SliceResult<PostComment> postComments = commentReader.read(targetUser, cursor);
        return postComments.map(postComment ->
                postReader.read(TargetPost.from(postComment.getPostId()))
        );
    }

}
