package com.cos.cercat.board.app;

import com.cos.cercat.board.domain.PostType;
import com.cos.cercat.board.dto.request.PostUpdateRequest;
import com.cos.cercat.certificate.app.CertificateService;
import com.cos.cercat.certificate.domain.Certificate;
import com.cos.cercat.global.entity.Image;
import com.cos.cercat.global.util.FileUploader;
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
public class PostUpdateService {

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
