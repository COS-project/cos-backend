package com.cos.cercat.apis.post.api;

import com.cos.cercat.apis.post.request.PostCommentCreateRequest;
import com.cos.cercat.apis.post.request.PostCreateRequest;
import com.cos.cercat.web.Response;
import com.cos.cercat.domain.post.PostType;
import com.cos.cercat.domain.post.TargetPost;
import com.cos.cercat.domain.user.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Tag(name = "게시글/댓글 생성 API")
public interface CreatePostApiDocs {

    @Operation(summary = "게시글 생성")
    Response<TargetPost> createPost(Long certificateId, PostType postType,
                                    PostCreateRequest request,
                                    List<MultipartFile> files,
                                    User currentUser);

    @Operation(summary = "댓글 생성")
    public Response<Void> createPostComment(Long postId,
                                            PostCommentCreateRequest request,
                                            User currentUser);

}
