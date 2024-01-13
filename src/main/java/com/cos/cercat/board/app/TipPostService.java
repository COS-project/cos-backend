package com.cos.cercat.board.app;

import com.cos.cercat.board.domain.TipPost;
import com.cos.cercat.board.repository.TipPostRepository;
import com.cos.cercat.certificate.domain.Certificate;
import com.cos.cercat.global.entity.Image;
import com.cos.cercat.user.domain.User;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
public class TipPostService {

    private final TipPostRepository tipPostRepository;

    @Builder(builderMethodName = "createTipPostBuilder")
    public void createTipPost(String title,
                              String content,
                              List<Image> images,
                              Certificate certificate,
                              User user) {
        TipPost tipPost = TipPost.of(title, content, user, certificate);
        tipPost.addAllPostImages(images);

        tipPostRepository.save(tipPost);
    }
}
