package com.cos.cercat.domain.post.domain;

import com.cos.cercat.domain.certificate.domain.Certificate;
import com.cos.cercat.domain.common.domain.Image;
import com.cos.cercat.domain.user.domain.User;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@OnDelete(action = OnDeleteAction.CASCADE)
public class NormalPost extends Post {

    public NormalPost(String title, String content, User user, Certificate certificate, PostType postType, List<Image> images) {
        super(title, content, user, certificate, postType, images);
    }

}
