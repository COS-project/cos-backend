package com.cos.cercat.domain.post;

import com.cos.cercat.domain.certificate.Certificate;
import com.cos.cercat.domain.common.Image;
import com.cos.cercat.domain.user.User;
import java.util.List;

public interface PostAppendStrategy {

    Boolean supports(PostType postType);

    PostId append(User user, Certificate certificate, NewPost newPost, List<Image> images);

}
