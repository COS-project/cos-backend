package com.cos.cercat.common.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Image {
    private Long id;
    private String imageUrl;

    public Image(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public static Image from(String imageUrl) {
        return new Image(imageUrl);
    }

    public static Image notAssigned() {
        return new Image(null, "");
    }
}
