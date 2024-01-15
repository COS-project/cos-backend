package com.cos.cercat.board.app;

import com.cos.cercat.board.domain.NormalPost;
import com.cos.cercat.board.domain.Post;
import com.cos.cercat.board.domain.PostImages;
import com.cos.cercat.board.repository.NormalPostRepository;
import com.cos.cercat.certificate.domain.Certificate;
import com.cos.cercat.global.entity.Image;
import com.cos.cercat.user.domain.User;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Service
public class NormalPostService {
    private final NormalPostRepository normalPostRepository;

    @Builder(builderMethodName = "createNormalPostBuilder")
    public void createNormalPost(String title,
                                        String content,
                                        List<Image> images,
                                        Certificate certificate,
                                        User user) {

        NormalPost normalPost = NormalPost.of(title, content, user, certificate);
        normalPost.addAllPostImages(images);

        normalPostRepository.save(normalPost);

    }

}
