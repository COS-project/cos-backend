package com.cos.cercat.domain.postsearch;

public record SearchLog(
        String keyword
) {
    public static SearchLog from(String keyword) {
        return new SearchLog(keyword);
    }

    public boolean isNotValid() {
        return keyword == null || keyword.isBlank();
    }
}
