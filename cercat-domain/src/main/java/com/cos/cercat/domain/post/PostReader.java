package com.cos.cercat.domain.post;

import com.cos.cercat.common.domain.Cursor;
import com.cos.cercat.common.domain.SliceResult;
import com.cos.cercat.domain.certificate.TargetCertificate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class PostReader {

    private final PostRepository postRepository;

    public SliceResult<Post> read(TargetCertificate targetCertificate,
                                  CommentaryPostSearchCond commentaryPostSearchCond,
                                  Cursor cursor) {
        return postRepository.find(targetCertificate, commentaryPostSearchCond, cursor);
    }

    public PostWithComments readDetail(TargetPost targetPost) {
        PostWithComments postWithComments = postRepository.findDetail(targetPost);
        List<PostComment> organizedComments = organizeChildComments(postWithComments.postComments());
        return PostWithComments.of(postWithComments.post(), organizedComments);

    }

    private List<PostComment> organizeChildComments(List<PostComment> postComments) {
        Map<Long, PostComment> map = postComments.stream()
                .collect(Collectors.toMap(PostComment::id, Function.identity()));

        map.values().stream()
                .filter(PostComment::hasParent)
                .forEach(postComment -> {
                    Long parentId = postComment.parentId();
                    PostComment parentComment = map.get(parentId);
                    if (parentComment != null) {
                        parentComment.addChildren(postComment);
                    }
                });

        return map.values().stream()
                .filter(PostComment::hasParent)
                .sorted(Comparator
                        .comparing(PostComment::dateTime, Comparator.comparing(DateTime::createdAt).reversed())
                        .thenComparing(PostComment::id))
                .collect(Collectors.toList());
    }

}
