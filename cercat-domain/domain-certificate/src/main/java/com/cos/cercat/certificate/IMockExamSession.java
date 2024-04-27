package com.cos.cercat.certificate;

public interface IMockExamSession {
    int getExamYear();
    int getRound();

    static IMockExamSession of(int examYear, int round) {
        return new IMockExamSession() {
            @Override
            public int getExamYear() {
                return examYear;
            }

            @Override
            public int getRound() {
                return round;
            }
        };
    }
}
