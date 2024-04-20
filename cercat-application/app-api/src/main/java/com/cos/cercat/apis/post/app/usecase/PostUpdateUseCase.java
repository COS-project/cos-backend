package com.cos.cercat.apis.post.app.usecase;

import com.cos.cercat.apis.post.dto.RecommendTagDTO;
import com.cos.cercat.apis.post.dto.request.PostUpdateRequest;
import com.cos.cercat.common.annotation.UseCase;
import com.cos.cercat.domain.UserEntity;
import com.cos.cercat.domain.post.PostType;
import com.cos.cercat.entity.Image;
import com.cos.cercat.gcs.FileUploader;
import com.cos.cercat.service.CertificateService;
import com.cos.cercat.domain.CertificateEntity;
import com.cos.cercat.service.MockExamService;
import com.cos.cercat.service.QuestionService;
import com.cos.cercat.domain.MockExam;
import com.cos.cercat.domain.Question;
import com.cos.cercat.service.UserService;
import com.cos.cercat.service.post.CommentaryPostService;
import com.cos.cercat.service.post.NormalPostService;
import com.cos.cercat.service.post.PostService;
import com.cos.cercat.service.post.TipPostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@UseCase
@Slf4j
public class PostUpdateUseCase {

    private final CommentaryPostService commentaryPostService;
    private final TipPostService tipPostService;
    private final NormalPostService normalPostService;
    private final CertificateService certificateService;
    private final MockExamService mockExamService;
    private final QuestionService questionService;
    private final PostService postService;
    private final FileUploader fileUploader;
    private final UserService userService;

    @Transactional
    public void updatePost(Long certificateId,
                           PostType postType,
                           PostUpdateRequest request,
                           List<MultipartFile> files,
                           Long userId) {

        CertificateEntity certificateEntity = certificateService.getCertificate(certificateId);
        UserEntity userEntity = userService.getUser(userId);
        List<Image> images = updateImages(request.removeImageUrls(), files);

        switch (postType) {
            case COMMENTARY -> {
                Question question = getQuestion(certificateEntity, request.examYear(), request.round(), request.questionSequence());

                commentaryPostService.updateCommentaryPost(
                        request.postId(),
                        request.title(),
                        request.content(),
                        question,
                        images,
                        userEntity
                );
            }
            case TIP -> {
                tipPostService.updateTipPost(
                        request.postId(),
                        request.title(),
                        request.content(),
                        request.newTags().stream()
                                .map(RecommendTagDTO::toEntity)
                                .collect(Collectors.toSet()),
                        images,
                        userEntity
                );
            }
            case NORMAL -> {
                normalPostService.updateNormalPost(
                        request.postId(),
                        request.title(),
                        request.content(),
                        images,
                        userEntity
                );
            }
        }
    }

    private List<Image> updateImages(List<String> removeImageUrls, List<MultipartFile> files) {
        postService.deletePostImages(removeImageUrls); // 유저가 삭제요청한 이미지들 삭제
        return fileUploader.uploadFileInStorage(files);
    }

    private Question getQuestion(CertificateEntity certificateEntity, Integer examYear, Integer round, Integer questionSeq) {
        MockExam mockExam = mockExamService.getMockExam(certificateEntity, examYear, round);
        return questionService.getQuestion(mockExam, questionSeq);
    }
}
