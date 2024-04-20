package com.cos.cercat.domain.post;

import com.cos.cercat.domain.CertificateEntity;
import com.cos.cercat.domain.UserEntity;
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
public class NormalPostEntity extends PostEntity {

    public NormalPostEntity(String title, String content, UserEntity userEntity, CertificateEntity certificateEntity, PostType postType, List<Image> images) {
        super(title, content, userEntity, certificateEntity, postType, images);
    }

}
