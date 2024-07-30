package com.cos.cercat;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;


@SpringBootTest(classes = MockExamResultTestApp.class)
@ActiveProfiles({"test", "gcs", "redis"})
@Transactional
public abstract class RepositoryTest {

}