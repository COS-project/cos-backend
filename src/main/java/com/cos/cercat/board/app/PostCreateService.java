package com.cos.cercat.board.app;

import com.cos.cercat.board.dto.request.PostCreateRequest;
import com.cos.cercat.board.dto.request.PostType;
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
    }


    private Question getQuestion(Certificate certificate, Integer examYear, Integer round, Integer questionSeq) {
        MockExam mockExam = mockExamService.getMockExam(certificate, examYear, round);
        return questionService.getQuestion(mockExam, questionSeq);
    }
}
