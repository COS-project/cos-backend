package com.cos.cercat.mockexam;

import com.cos.cercat.certificate.IMockExamSession;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MockExamSession implements IMockExamSession {
    private int examYear;
    private int round;

    public static MockExamSession of(int examYear, int round) {
        return new MockExamSession(examYear, round);
    }

}