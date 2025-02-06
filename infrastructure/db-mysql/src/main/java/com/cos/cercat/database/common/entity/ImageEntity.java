package com.cos.cercat.database.common.entity;

import com.cos.cercat.domain.common.Image;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Table(name = "image")
public class ImageEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String imageUrl;

    public static ImageEntity from(Image image) {

        if (image == null) {
            return null;
        }

        return new ImageEntity(
                image.getId(),
                image.getImageUrl()
        );
    }

    public Image toImage() {
        return new Image(
                id,
                imageUrl
        );
    }


}
