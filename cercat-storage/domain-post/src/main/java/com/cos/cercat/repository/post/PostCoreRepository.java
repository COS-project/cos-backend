package com.cos.cercat.repository.post;

import com.cos.cercat.domain.CertificateEntity;
import com.cos.cercat.domain.QuestionEntity;
import com.cos.cercat.domain.UserEntity;
import com.cos.cercat.domain.certificate.TargetCertificate;
import com.cos.cercat.domain.mockexam.Question;
import com.cos.cercat.domain.post.*;
import com.cos.cercat.domain.user.TargetUser;
import com.cos.cercat.entity.Image;
import com.cos.cercat.repository.CertificateJpaRepository;
import com.cos.cercat.repository.QuestionJpaRepository;
import com.cos.cercat.repository.UserJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class PostCoreRepository implements PostRepository {

    private final PostJpaRepository postJpaRepository;
    private final NormalPostJpaRepository normalPostJpaRepository;
    private final TipPostJpaRepository tipPostJpaRepository;
    private final CommentaryPostJpaRepository commentaryPostJpaRepository;
    private final UserJpaRepository userJpaRepository;
    private final QuestionJpaRepository questionJpaRepository;
    private final CertificateJpaRepository certificateJpaRepository;
    private final PostImageJpaRepository postImageJpaRepository;

    @Override
    public void save(TargetUser targetUser,
                     TargetCertificate targetCertificate,
                     PostContent postContent,
                     Question question) {

        UserEntity userEntity = userJpaRepository.getReferenceById(targetUser.userId());
        QuestionEntity questionEntity = questionJpaRepository.getReferenceById(question.id());
        CertificateEntity certificateEntity = certificateJpaRepository.getReferenceById(targetCertificate.certificateId());


        CommentaryPostEntity commentaryPostEntity = new CommentaryPostEntity(
                postContent.title(),
                postContent.content(),
                userEntity,
                certificateEntity,
                postContent.postType(),
                questionEntity
        );

        List<PostImageEntity> postImageEntities = postContent.imageUrls().stream()
                .map(imageUrl -> PostImageEntity.of(commentaryPostEntity, Image.from(imageUrl)))
                .toList();

        commentaryPostJpaRepository.save(commentaryPostEntity);
        postImageJpaRepository.saveAll(postImageEntities);
    }
}
