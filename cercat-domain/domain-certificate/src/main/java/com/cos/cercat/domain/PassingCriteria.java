package com.cos.cercat.domain;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class PassingCriteria {

    private Integer subjectPassingCriteria;

    private Integer totalAvgCriteria;

    private Integer practicalPassingCriteria;
}
