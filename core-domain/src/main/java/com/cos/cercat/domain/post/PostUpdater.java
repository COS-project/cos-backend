package com.cos.cercat.domain.post;

import com.cos.cercat.domain.certificate.Certificate;
import com.cos.cercat.domain.certificate.CertificateReader;
import com.cos.cercat.domain.certificate.TargetCertificate;
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

    private final UpdatePostRepository postRepository;
    private final PostRemover postRemover;
    private final QuestionReader questionReader;
    private final CertificateReader certificateReader;

    @Transactional
    public void update(Post post,
                       UpdatedPost updatedPost,
                       List<Image> images) {
        switch (updatedPost.postType()) {
            case COMMENTARY -> {
                CommentaryPost commentaryPost = (CommentaryPost) post;
                Certificate certificate = certificateReader.read(TargetCertificate.from(post.getCertificate().id()));
                Question question = questionReader.read(certificate, updatedPost.mockExamSession(), updatedPost.questionSequence());
                commentaryPost.update(updatedPost.content(), question, images);
                postRepository.update(commentaryPost);
            }
            case TIP -> {
                TipPost tipPost = (TipPost) post;
                tipPost.update(updatedPost.content(), updatedPost.tags(), images);
                postRemover.deleteRecommendTags(tipPost);
                postRepository.updateTipPost(tipPost);
            }
            case NORMAL -> {
                post.update(updatedPost.content(), images);
                postRepository.update(post);
            }
        }
        postRemover.deleteImages(updatedPost.removeImageIds());
    }
}
