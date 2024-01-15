package com.cos.cercat.board.app;

import com.cos.cercat.board.dto.request.CommentaryPostUpdateRequest;
import com.cos.cercat.certificate.app.CertificateService;
import com.cos.cercat.certificate.domain.Certificate;
import com.cos.cercat.global.entity.Image;
import com.cos.cercat.global.util.FileUploader;
import com.cos.cercat.mockExam.app.MockExamService;
import com.cos.cercat.mockExam.app.QuestionService;
import com.cos.cercat.mockExam.domain.MockExam;
import com.cos.cercat.mockExam.domain.Question;
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
    private final CertificateService certificateService;
    private final MockExamService mockExamService;
    private final QuestionService questionService;
    private final PostService postService;
    private final FileUploader fileUploader;

    @Transactional
    public void updateCommentaryPost(Long certificateId, CommentaryPostUpdateRequest request, List<MultipartFile> files) {
        Certificate certificate = certificateService.getCertificate(certificateId);
        Question question = getQuestion(certificate, request.examYear(), request.round(), request.questionSequence());

        List<Image> images = fileUploader.uploadFileInStorage(files);
        postService.deletePostImages(request.removeImageUrls()); // 유저가 삭제요청한 이미지들 삭제


        commentaryPostService.updateCommentaryPostBuilder()
                .postId(request.postId())
                .title(request.title())
                .question(question)
                .content(request.content())
                .images(images)
                .build();

    }

    private Question getQuestion(Certificate certificate, Integer examYear, Integer round, Integer questionSeq) {
        MockExam mockExam = mockExamService.getMockExam(certificate, examYear, round);
        return questionService.getQuestion(mockExam, questionSeq);
    }
}
