package com.cos.cercat.apis.post.app.usecase;

import com.cos.cercat.apis.post.dto.RecommendTagDTO;
import com.cos.cercat.apis.post.dto.request.PostUpdateRequest;
import com.cos.cercat.common.annotation.UseCase;
import com.cos.cercat.infra.client.gcs.FileUploader;
import com.cos.cercat.domain.common.domain.Image;
import com.cos.cercat.domain.post.service.CommentaryPostService;
import com.cos.cercat.domain.post.service.NormalPostService;
import com.cos.cercat.domain.post.service.PostService;
import com.cos.cercat.domain.post.service.TipPostService;
import com.cos.cercat.domain.post.domain.PostType;
import com.cos.cercat.domain.certificate.service.CertificateService;
import com.cos.cercat.domain.certificate.domain.Certificate;
import com.cos.cercat.domain.mockExam.service.MockExamService;
import com.cos.cercat.domain.mockExam.service.QuestionService;
import com.cos.cercat.domain.mockExam.domain.MockExam;
import com.cos.cercat.domain.mockExam.domain.Question;
import com.cos.cercat.domain.user.service.UserService;
import com.cos.cercat.domain.user.domain.User;
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

        Certificate certificate = certificateService.getCertificate(certificateId);
        User user = userService.getUser(userId);
        List<Image> images = updateImages(request.removeImageUrls(), files);

        switch (postType) {
            case COMMENTARY -> {
                Question question = getQuestion(certificate, request.examYear(), request.round(), request.questionSequence());

                commentaryPostService.updateCommentaryPost(
                        request.postId(),
                        request.title(),
                        request.content(),
                        question,
                        images,
                        user
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
                        user
                );
            }
            case NORMAL -> {
                normalPostService.updateNormalPost(
                        request.postId(),
                        request.title(),
                        request.content(),
                        images,
                        user
                );
            }
        }
    }

    private List<Image> updateImages(List<String> removeImageUrls, List<MultipartFile> files) {
        postService.deletePostImages(removeImageUrls); // 유저가 삭제요청한 이미지들 삭제
        return fileUploader.uploadFileInStorage(files);
    }

    private Question getQuestion(Certificate certificate, Integer examYear, Integer round, Integer questionSeq) {
        MockExam mockExam = mockExamService.getMockExam(certificate, examYear, round);
        return questionService.getQuestion(mockExam, questionSeq);
    }
}
