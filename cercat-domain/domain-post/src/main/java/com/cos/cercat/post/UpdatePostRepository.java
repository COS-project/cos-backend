package com.cos.cercat.post;

public interface UpdatePostRepository {

    void update(Post post);

    void updateComment(PostComment comment);

    void updateTipPost(TipPost tipPost);
}
