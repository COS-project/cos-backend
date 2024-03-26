package com.cos.cercat.domain.post;

import com.cos.cercat.domain.Certificate;
import com.cos.cercat.domain.User;
import com.cos.cercat.entity.Image;
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
