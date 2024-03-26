package com.cos.cercat.cache;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SearchLog {
    private String keyword;
    private String createdAt;
}
