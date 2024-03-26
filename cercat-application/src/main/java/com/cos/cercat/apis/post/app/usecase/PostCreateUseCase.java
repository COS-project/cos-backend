package com.cos.cercat.apis.post.app.usecase;

import com.cos.cercat.apis.post.dto.request.PostCreateRequest;
import com.cos.cercat.common.annotation.UseCase;
import com.cos.cercat.infra.client.gcs.FileUploader;
import com.cos.cercat.domain.common.domain.Image;
import com.cos.cercat.domain.post.service.CommentaryPostService;
import com.cos.cercat.domain.post.service.NormalPostService;
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

        Certificate certificate = certificateService.getCertificate(certificateId);
        List<Image> images = fileUploader.uploadFileInStorage(files);
        User user = userService.getUser(userId);

        switch (postType) {
            case COMMENTARY -> createCommentaryPost(request, certificate, images, user);
            case TIP -> tipPostService.createTipPost(request.toTipPost(images, certificate, user));
            case NORMAL -> normalPostService.createNormalPost(request.toNormalPost(images, certificate, user));
        }
    }

    private void createCommentaryPost(PostCreateRequest request, Certificate certificate, List<Image> images, User user) {
        Question question = getQuestion(certificate, request.examYear(), request.round(), request.questionSequence());
        commentaryPostService.createCommentaryPost(request.toCommentaryPost(images, certificate, user, question));
        log.info("user - {}, certificate - {}, questionId - {} 해설 게시글 생성", user.getEmail(), certificate.getCertificateName(), question.getId());
    }


    private Question getQuestion(Certificate certificate, Integer examYear, Integer round, Integer questionSeq) {
        MockExam mockExam = mockExamService.getMockExam(certificate, examYear, round);
        return questionService.getQuestion(mockExam, questionSeq);
    }
}
