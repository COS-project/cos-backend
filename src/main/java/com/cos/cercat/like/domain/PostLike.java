package com.cos.cercat.like.domain;

import com.cos.cercat.like.domain.EmbeddedId.PostLikePK;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(staticName = "from")
@Getter
public class PostLike {

    @EmbeddedId
    private PostLikePK postLikePK;

}
