package com.cos.cercat.board.domain;

import com.cos.cercat.certificate.domain.Certificate;
import com.cos.cercat.user.domain.User;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class TipPost extends Post{

    //TODO: 추천 인강 등 태그


    private TipPost(String title, String content, User user, Certificate certificate) {
        super(title, content, user, certificate);
    }

    public static TipPost of(String title, String content, User user, Certificate certificate) {
        return new TipPost(
                title,
                content,
                user,
                certificate
        );
    }
}
