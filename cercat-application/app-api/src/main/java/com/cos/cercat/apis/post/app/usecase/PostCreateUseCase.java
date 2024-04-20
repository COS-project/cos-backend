package com.cos.cercat.apis.post.app.usecase;

import com.cos.cercat.apis.post.dto.request.PostCreateRequest;
import com.cos.cercat.common.annotation.UseCase;
import com.cos.cercat.domain.CertificateEntity;
import com.cos.cercat.domain.UserEntity;
import com.cos.cercat.gcs.FileUploader;
import com.cos.cercat.entity.Image;
import com.cos.cercat.domain.post.PostType;
import com.cos.cercat.service.CertificateService;
import com.cos.cercat.service.MockExamService;
import com.cos.cercat.service.QuestionService;
import com.cos.cercat.domain.MockExam;
import com.cos.cercat.domain.Question;
import com.cos.cercat.service.UserService;
import com.cos.cercat.service.post.CommentaryPostService;
import com.cos.cercat.service.post.NormalPostService;
import com.cos.cercat.service.post.TipPostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@RequiredArgsConstructor
@UseCase
@Slf4j
public class PostCreateUseCase {

    private final CommentaryPostService commentaryPostService;
    private final CertificateService certificateService;
    private final MockExamService mockExamService;
    private final QuestionService questionService;
    private final FileUploader fileUploader;
    private final TipPostService tipPostService;
    private final NormalPostService normalPostService;
    private final UserService userService;


    /***
     * 해당 자격증 게시판에 게시글을 생성합니다.
     * @param certificateId 자격증 ID
     * @param postType COMMENTARY(해설), TIP(꿀팁), NORMAL(자유)
     * @param request 게시글 생성 정보
     * @param files 게시글 파일 리스트
     * @param userId 유저 ID
     */
    @Transactional
    public void createPost(Long certificateId,
                           PostType postType,
                           PostCreateRequest request,
                           List<MultipartFile> files,
                           Long userId) {

        CertificateEntity certificateEntity = certificateService.getCertificate(certificateId);
        List<Image> images = fileUploader.uploadFileInStorage(files);
        UserEntity userEntity = userService.getUser(userId);

        switch (postType) {
            case COMMENTARY -> createCommentaryPost(request, certificateEntity, images, userEntity);
            case TIP -> tipPostService.createTipPost(request.toTipPost(images, certificateEntity, userEntity));
            case NORMAL -> normalPostService.createNormalPost(request.toNormalPost(images, certificateEntity, userEntity));
        }
    }

    private void createCommentaryPost(PostCreateRequest request, CertificateEntity certificateEntity, List<Image> images, UserEntity userEntity) {
        Question question = getQuestion(certificateEntity, request.examYear(), request.round(), request.questionSequence());
        commentaryPostService.createCommentaryPost(request.toCommentaryPost(images, certificateEntity, userEntity, question));
        log.info("userEntity - {}, certificateEntity - {}, questionId - {} 해설 게시글 생성", userEntity.getEmail(), certificateEntity.getCertificateName(), question.getId());
    }


    private Question getQuestion(CertificateEntity certificateEntity, Integer examYear, Integer round, Integer questionSeq) {
        MockExam mockExam = mockExamService.getMockExam(certificateEntity, examYear, round);
        return questionService.getQuestion(mockExam, questionSeq);
    }
}
