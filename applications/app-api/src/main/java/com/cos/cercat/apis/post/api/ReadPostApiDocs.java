package com.cos.cercat.apis.post.api;

import com.cos.cercat.apis.post.response.PostResponse;
import com.cos.cercat.apis.post.response.PostWithCommentsResponse;
import com.cos.cercat.domain.common.Cursor;
import com.cos.cercat.domain.searchlog.SearchCond;
import com.cos.cercat.web.Response;
import com.cos.cercat.domain.common.SliceResult;
import com.cos.cercat.domain.post.CommentarySearchCond;
import com.cos.cercat.domain.post.PostType;
import com.cos.cercat.domain.user.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

@Tag(name = "게시글 조회 API")
public interface ReadPostApiDocs {

    @Operation(summary = "통합 검색")
    Response<SliceResult<PostResponse>> searchPosts(
            SearchCond cond,
            User currentUser,
            Long certificateId,
            @Parameter(examples = {
                    @ExampleObject(name = "cursor", value = """ 
                            {
                                "page" : 0,
                                "size" : 10,
                                "sortFields" : "createdAt, id",
                                "sortDirections" : "DESC, ASC"
                            }
                        """)
            })
            Cursor cursor
    );

    @Operation(summary = "해설 게시글 검색")
    Response<SliceResult<PostResponse>> searchCommentaryPosts(
            Long certificateId,
            User currentUser,
            CommentarySearchCond cond,
            @Parameter(examples = {
                      @ExampleObject(name = "cursor", value = """ 
                    {
                        "page" : 0,
                        "size" : 10,
                        "sortFields" : "createdAt, id",
                        "sortDirections" : "DESC, ASC"
                    }
                    """
                      )
            }
            )
            Cursor cursor);

    @Operation(summary = "게시글 상세 조회")
    Response<PostWithCommentsResponse> readPostDetail(
            Long postId,
            User currentUser
    );

    @Operation(summary = "베스트 꿀팁 TOP3 조회")
    Response<List<PostResponse>> readTop3TipPosts(
            Long certificateId,
            User currentUser
    );

    @Operation(summary = "내가 쓴 글 조회")
    Response<SliceResult<PostResponse>> readMyPosts(PostType postType,
                                                    User currentUser,
                                                    @Parameter(examples = {
                                                            @ExampleObject(name = "cursor", value = """ 
                                                                    {
                                                                        "page" : 0,
                                                                        "size" : 10,
                                                                        "sortFields" : "createdAt, id",
                                                                        "sortDirections" : "DESC, ASC"
                                                                    }
                                                                """)
                                                    })
                                                    Cursor cursor);

    @Operation(summary = "내가 댓글 쓴 게시글 조회")
    Response<SliceResult<PostResponse>> readMyCommentPosts(User currentUser,
                                                           @Parameter(examples = {
                                                                   @ExampleObject(name = "cursor", value = """ 
                                                                    {
                                                                        "page" : 0,
                                                                        "size" : 10,
                                                                        "sortFields" : "createdAt, id",
                                                                        "sortDirections" : "DESC, ASC"
                                                                    }
                                                                """)
                                                           })
                                                             Cursor cursor);

}
