package com.cos.cercat.mockExamResult.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QSubjectResults is a Querydsl query type for SubjectResults
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QSubjectResults extends BeanPath<SubjectResults> {

    private static final long serialVersionUID = -1240518368L;

    public static final QSubjectResults subjectResults1 = new QSubjectResults("subjectResults1");

    public final ListPath<SubjectResult, QSubjectResult> subjectResults = this.<SubjectResult, QSubjectResult>createList("subjectResults", SubjectResult.class, QSubjectResult.class, PathInits.DIRECT2);

    public QSubjectResults(String variable) {
        super(SubjectResults.class, forVariable(variable));
    }

    public QSubjectResults(Path<? extends SubjectResults> path) {
        super(path.getType(), path.getMetadata());
    }

    public QSubjectResults(PathMetadata metadata) {
        super(SubjectResults.class, metadata);
    }

}

