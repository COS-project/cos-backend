package com.cos.cercat.domain.certificate;

import com.cos.cercat.domain.common.event.Event;
import com.cos.cercat.domain.common.event.outbox.OutboxEventAppender;
import com.cos.cercat.domain.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;

import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CertificateExamScheduleCheckerTest {

    @Mock
    private CertificateExamReader certificateExamReader;

    @Mock
    private InterestCertificateManager interestCertificateManager;

    @Mock
    private ApplicationEventPublisher eventPublisher;

    @Mock
    private OutboxEventAppender outboxEventAppender;

    @InjectMocks
    private CertificateExamScheduleChecker certificateExamScheduleChecker;

    private CertificateExam certificateExam;
    private Certificate certificate;
    private InterestCertificate interestCertificate;

    @BeforeEach
    void setUp() {
        certificate = mock(Certificate.class); // 실제 certificate 객체를 생성
        certificateExam = mock(CertificateExam.class);
        User user = mock(User.class);
        interestCertificate = mock(InterestCertificate.class);

        // certificateExam.certificate()가 null이 아니도록 설정
        when(certificateExam.certificate()).thenReturn(certificate);
    }

    @Test
    void check_shouldPublishEventAndAppendToOutbox() {
        // Given
        when(certificateExamReader.read(any(), any())).thenReturn(List.of(certificateExam));
        when(interestCertificateManager.find((Certificate) any())).thenReturn(List.of(interestCertificate));

        ArgumentCaptor<Event> eventCaptor = ArgumentCaptor.forClass(Event.class);

        // When
        certificateExamScheduleChecker.check();

        // Then
        verify(eventPublisher, atLeastOnce()).publishEvent(eventCaptor.capture());

        for (Event publishedEvent : eventCaptor.getAllValues()) {
            outboxEventAppender.handleOutboxEvent(publishedEvent);
        }

        verify(outboxEventAppender, times(ScheduleCheckType.values().length)).handleOutboxEvent(any());
    }

    @Test
    @DisplayName("관심 자격증이 없으면 이벤트가 발행되지 않는다")
    void check_noInterestCertificates_shouldNotPublishEvent() {
        // Given
        when(certificateExamReader.read(any(), any())).thenReturn(List.of(certificateExam));
        when(interestCertificateManager.find(certificate)).thenReturn(List.of());

        // When
        certificateExamScheduleChecker.check();

        // Then
        verify(eventPublisher, never()).publishEvent(any());
        verify(outboxEventAppender, never()).handleOutboxEvent(any());
    }
}
