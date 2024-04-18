package com.cos.cercat.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QSubjects is a Querydsl query type for SubjectEntities
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QSubjects extends BeanPath<Subjects> {

    private static final long serialVersionUID = -2010433731L;

    public static final QSubjects subjects1 = new QSubjects("subjects1");

    public final ListPath<Subject, QSubject> subjects = this.<Subject, QSubject>createList("subjects", Subject.class, QSubject.class, PathInits.DIRECT2);

    public QSubjects(String variable) {
        super(Subjects.class, forVariable(variable));
    }

    public QSubjects(Path<? extends Subjects> path) {
        super(path.getType(), path.getMetadata());
    }

    public QSubjects(PathMetadata metadata) {
        super(Subjects.class, metadata);
    }

}

