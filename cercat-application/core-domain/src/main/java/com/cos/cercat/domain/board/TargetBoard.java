package com.cos.cercat.domain.board;

public record TargetBoard(
        Long certificateId,
        Long userId
) {
    public static TargetBoard from(Long certificateId, Long userId) {
        return new TargetBoard(certificateId, userId);
    }
}
