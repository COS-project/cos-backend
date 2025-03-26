package com.cos.cercat.domain.post;

import com.cos.cercat.domain.certificate.Certificate;
import com.cos.cercat.domain.certificate.CertificateReader;
import com.cos.cercat.domain.certificate.CertificateId;
import com.cos.cercat.domain.common.Image;
import com.cos.cercat.domain.mockexam.Question;
import com.cos.cercat.domain.mockexam.QuestionReader;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class PostUpdater {

    private final CreatePostRepository postRepository;
    private final PostRemover postRemover;
    private final QuestionReader questionReader;
    private final CertificateReader certificateReader;

    @Transactional
    public void update(Post post, UpdatedPost updatedPost, List<Image> images) {
        Post updated = updatePost(post, updatedPost, images);
        postRepository.save(updated);
        postRemover.removeImages(updatedPost.removeImageIds());
    }

    private Post updatePost(Post post, UpdatedPost updatedPost, List<Image> images) {
        if (post instanceof CommentaryPost commentaryPost) {
            return updateCommentaryPost(commentaryPost, updatedPost, images);
        }
        if (post instanceof TipPost tipPost) {
            return updateTipPost(tipPost, updatedPost, images);
        }
        return updateNormalPost(post, updatedPost, images);
    }

    private Post updateCommentaryPost(CommentaryPost post, UpdatedPost updatedPost, List<Image> images) {
        Certificate certificate = certificateReader.read(post.getCertificate().id());
        Question question = questionReader.read(certificate, updatedPost.mockExamSession(), updatedPost.questionSequence());
        return post.update(updatedPost.content(), question, images);
    }

    private Post updateTipPost(TipPost post, UpdatedPost updatedPost, List<Image> images) {
        postRemover.deleteRecommendTags(post);
        return post.update(updatedPost.content(), images, updatedPost.tags());
    }

    private Post updateNormalPost(Post post, UpdatedPost updatedPost, List<Image> images) {
        return post.update(updatedPost.content(), images);
    }
}
