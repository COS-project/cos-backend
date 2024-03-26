package com.cos.cercat.domain.post;

import com.cos.cercat.entity.Image;
import com.cos.cercat.domain.Certificate;
import com.cos.cercat.domain.User;
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
public class TipPost extends Post{

    private RecommendTags recommendTags = new RecommendTags();

    public TipPost(String title, String content, User user, Certificate certificate, PostType postType, List<Image> images, Set<RecommendTag> recommendTags) {
        super(title, content, user, certificate, postType, images);
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
