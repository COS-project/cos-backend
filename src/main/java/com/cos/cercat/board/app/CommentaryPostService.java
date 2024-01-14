package com.cos.cercat.board.app;

import com.cos.cercat.board.domain.CommentaryPost;
import com.cos.cercat.board.repository.CommentaryPostRepository;
import com.cos.cercat.certificate.domain.Certificate;
import com.cos.cercat.global.entity.Image;
import com.cos.cercat.mockExam.domain.Question;
import com.cos.cercat.user.domain.User;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

import java.util.List;


@RequiredArgsConstructor
@Service
@Slf4j
public class CommentaryPostService {

    private final CommentaryPostRepository commentaryPostRepository;

    @Builder(builderMethodName = "createCommentaryPostBuilder")
    public void createCommentaryPost(Certificate certificate,
                                     String title,
                                     String content,
                                     User user,
                                     List<Image> images,
                                     Question question) {

        CommentaryPost commentaryPost = CommentaryPost.of(title, content, user, certificate, question);
        commentaryPost.addAllPostImages(images);

        commentaryPostRepository.save(commentaryPost);
    }

    public Slice<CommentaryPost> searchCommentaryPosts(Pageable pageable, Certificate certificate, String keyword) {
        return commentaryPostRepository.searchCommentaryPosts(pageable, certificate, keyword);
    }

}
