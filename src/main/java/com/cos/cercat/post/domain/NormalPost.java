package com.cos.cercat.post.domain;

import com.cos.cercat.certificate.domain.Certificate;
import com.cos.cercat.global.common.Image;
import com.cos.cercat.user.domain.User;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class NormalPost extends Post {

    public NormalPost(String title, String content, User user, Certificate certificate, PostType postType, List<Image> images) {
        super(title, content, user, certificate, postType, images);
    }

}
