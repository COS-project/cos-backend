package com.cos.cercat.like.domain;

import com.cos.cercat.like.domain.EmbeddedId.CommentLikePK;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class CommentLike {

    @EmbeddedId
    private CommentLikePK commentLikePK;

}
