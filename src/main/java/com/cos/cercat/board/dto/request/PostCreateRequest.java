package com.cos.cercat.board.dto.request;

import com.cos.cercat.board.domain.CommentaryPost;
import com.cos.cercat.board.domain.NormalPost;
import com.cos.cercat.board.domain.PostType;
import com.cos.cercat.board.domain.TipPost;
import com.cos.cercat.certificate.domain.Certificate;
import com.cos.cercat.global.entity.Image;
import com.cos.cercat.mockExam.domain.Question;
import com.cos.cercat.user.domain.User;

import java.util.List;

public record PostCreateRequest(
        String title,
        String content,
        Integer examYear,
        Integer round,
        Integer questionSequence
) {
    public CommentaryPost toCommentaryPost(List<Image> images,
                                           Certificate certificate,
                                           User user,
                                           Question question) {
        return new CommentaryPost(
                title,
                content,
                user,
                certificate,
                PostType.COMMENTARY,
                images,
                question
        );
    }

    public TipPost toTipPost(List<Image> images,
                                    Certificate certificate,
                                    User user) {
        return new TipPost(
                title,
                content,
                user,
                certificate,
                PostType.TIP,
                images
        );
    }

    public NormalPost toNormalPost(List<Image> images,
                                     Certificate certificate,
                                     User user) {
        return new NormalPost(
                title,
                content,
                user,
                certificate,
                PostType.NORMAL,
                images
        );
    }
}
