package com.cos.cercat.post;

import com.cos.cercat.certificate.Certificate;
import com.cos.cercat.certificate.CertificateReader;
import com.cos.cercat.certificate.TargetCertificate;
import com.cos.cercat.mockexam.MockExamReader;
import com.cos.cercat.mockexam.Question;
import com.cos.cercat.mockexam.QuestionReader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@RequiredArgsConstructor
public class PostUpdator {

    private final UpdatePostRepository postRepository;
    private final PostRemover postRemover;
    private final QuestionReader questionReader;
    private final CertificateReader certificateReader;

    public void update(Post post) {
        postRepository.update(post);
    }

    @Transactional
    public void update(Post post,
                       UpdatedPost updatedPost) {
        switch (updatedPost.postType()) {
            case COMMENTARY -> {
                CommentaryPost commentaryPost = (CommentaryPost) post;
                Certificate certificate = certificateReader.read(TargetCertificate.from(post.getCertificate().id()));
                Question question = questionReader.read(certificate, updatedPost.mockExamSession(), updatedPost.questionSequence());
                commentaryPost.update(updatedPost.content(), question);
                postRepository.update(commentaryPost);
            }
            case TIP -> {
                TipPost tipPost = (TipPost) post;
                tipPost.update(updatedPost.content(), updatedPost.tags());
                postRemover.deleteRecommendTags(tipPost);
                postRepository.updateTipPost(tipPost);
            }
            case NORMAL -> {
                post.update(updatedPost.content());
                postRepository.update(post);
            }
        }
        postRemover.deleteImages(updatedPost.removeImageIds());
    }
}
