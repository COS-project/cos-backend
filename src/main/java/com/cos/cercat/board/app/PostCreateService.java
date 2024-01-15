package com.cos.cercat.board.app;

import com.cos.cercat.board.dto.request.CommentaryPostCreateRequest;
import com.cos.cercat.board.dto.request.NormalPostCreateRequest;
import com.cos.cercat.board.dto.request.TipPostCreateRequest;
import com.cos.cercat.global.util.FileUploader;
import com.cos.cercat.certificate.app.CertificateService;
import com.cos.cercat.certificate.domain.Certificate;
import com.cos.cercat.global.entity.Image;
import com.cos.cercat.mockExam.app.MockExamService;
import com.cos.cercat.mockExam.app.QuestionService;
import com.cos.cercat.mockExam.domain.MockExam;
import com.cos.cercat.mockExam.domain.Question;
import com.cos.cercat.user.app.UserService;
import com.cos.cercat.user.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@RequiredArgsConstructor
@Service
@Slf4j
public class PostCreateService {

    private final CommentaryPostService commentaryPostService;
    private final CertificateService certificateService;
    private final MockExamService mockExamService;
    private final QuestionService questionService;
    private final FileUploader fileUploader;
    private final TipPostService tipPostService;
    private final NormalPostService normalPostService;
    private final UserService userService;

    /**
     * 특정 자격증의 해설 게시글을 생성한다.
     *
     * @param certificateId 자격증 고유 ID
     * @param request 해설 게시글 생성 요청 DTO
     * @param files 게시글 이미지 파일 리스트
     * @param userId 로그인한 유저 엔티티
     *
     * */
    @Transactional
    public void createCommentaryPost(Long certificateId,
                                     CommentaryPostCreateRequest request,
                                     List<MultipartFile> files,
                                     Long userId) {

        Certificate certificate = certificateService.getCertificate(certificateId);
        List<Image> images = fileUploader.uploadFileInStorage(files);
        User user = userService.getUser(userId);

        Question question = getQuestion(certificate, request.examYear(), request.round(), request.questionSequence());

        commentaryPostService.createCommentaryPostBuilder()
                .title(request.title())
                .content(request.content())
                .certificate(certificate)
                .question(question)
                .images(images)
                .user(user)
                .build();
    }

    @Transactional
    public void createTipPost(Long certificateId,
                              TipPostCreateRequest request,
                              List<MultipartFile> files,
                              Long userId) {

        Certificate certificate = certificateService.getCertificate(certificateId);
        List<Image> images = fileUploader.uploadFileInStorage(files);
        User user = userService.getUser(userId);

        tipPostService.createTipPostBuilder()
                .title(request.title())
                .content(request.content())
                .images(images)
                .certificate(certificate)
                .user(user)
                .build();
    }

    @Transactional
    public void createNormalPost(Long certificateId,
                                 NormalPostCreateRequest request,
                                 List<MultipartFile> files,
                                 Long userId) {

        Certificate certificate = certificateService.getCertificate(certificateId);
        List<Image> images = fileUploader.uploadFileInStorage(files);
        User user = userService.getUser(userId);

        normalPostService.createNormalPostBuilder()
                .title(request.title())
                .content(request.content())
                .certificate(certificate)
                .user(user)
                .images(images)
                .build();

    }

    private Question getQuestion(Certificate certificate, Integer examYear, Integer round, Integer questionSeq) {
        MockExam mockExam = mockExamService.getMockExam(certificate, examYear, round);
        return questionService.getQuestion(mockExam, questionSeq);
    }

}
