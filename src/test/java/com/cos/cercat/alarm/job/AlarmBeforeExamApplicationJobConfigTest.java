package com.cos.cercat.alarm.job;

import com.cos.cercat.certificate.domain.*;
import com.cos.cercat.certificate.repository.CertificateExamRepository;
import com.cos.cercat.certificate.repository.CertificateRepository;
import com.cos.cercat.certificate.repository.InterestCertificateRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobInstance;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.ActiveProfiles;


import static org.junit.jupiter.api.Assertions.*;


@Slf4j
@SpringBatchTest
@SpringBootTest
@ActiveProfiles("test")
class AlarmBeforeExamApplicationJobConfigTest {

    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;
    @Autowired
    private CertificateExamRepository certificateExamRepository;
    @Autowired
    private CertificateRepository certificateRepository;
    @Autowired
    private InterestCertificateRepository interestCertificateRepository;

    @Test
    @Commit
    public void given_when_then() throws Exception {


        //When
        JobExecution jobExecution = jobLauncherTestUtils.launchJob();
        JobInstance jobInstance = jobExecution.getJobInstance();
        //Then

        assertEquals(ExitStatus.COMPLETED, jobExecution.getExitStatus());
        assertEquals("alarmBeforeExamApplicationJob", jobInstance.getJobName());
    }



}