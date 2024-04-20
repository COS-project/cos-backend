package com.cos.cercat.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Embeddable;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserAnswers {

    @OneToMany(mappedBy = "subjectResult", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<UserAnswer> userAnswers = new ArrayList<>();

    public void addAll(List<UserAnswer> userAnswers) {
        this.getUserAnswers().addAll(userAnswers);
    }

    private UserAnswers(List<UserAnswer> userAnswers) {
        this.userAnswers = userAnswers;
    }

    public static UserAnswers from(List<UserAnswer> userAnswers) {
        return new UserAnswers(
                userAnswers
        );
    }

}
