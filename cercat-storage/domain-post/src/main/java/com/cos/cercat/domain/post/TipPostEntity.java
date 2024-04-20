package com.cos.cercat.domain.post;

import com.cos.cercat.domain.UserEntity;
import com.cos.cercat.entity.Image;
import com.cos.cercat.domain.CertificateEntity;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.List;
import java.util.Set;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@OnDelete(action = OnDeleteAction.CASCADE)
public class TipPostEntity extends PostEntity {

    private RecommendTags recommendTags = new RecommendTags();

    public TipPostEntity(String title, String content, UserEntity userEntity, CertificateEntity certificateEntity, PostType postType, List<Image> images, Set<RecommendTag> recommendTags) {
        super(title, content, userEntity, certificateEntity, postType, images);
        addRecommendTags(recommendTags);
    }

    private void addRecommendTags(Set<RecommendTag> recommendTags) {
        recommendTags.forEach(recommendTag -> recommendTag.setTipPost(this));
        this.recommendTags.addAll(recommendTags);
    }

    public void updateRecommendTags(Set<RecommendTag> newTags) {
        newTags.forEach(newTag -> newTag.setTipPost(this));
        this.recommendTags.updateTags(newTags);
    }
}
