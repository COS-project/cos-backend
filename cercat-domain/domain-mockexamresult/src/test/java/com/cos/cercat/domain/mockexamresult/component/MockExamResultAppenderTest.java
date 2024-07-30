package com.cos.cercat.domain.mockexamresult.component;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.cos.cercat.domain.mockexam.MockExam;
import com.cos.cercat.domain.mockexam.MockExamFixture;
import com.cos.cercat.domain.mockexamresult.MockExamResultAppender;
import com.cos.cercat.domain.mockexamresult.MockExamResultReader;
import com.cos.cercat.domain.mockexamresult.MockExamResultRepository;
import com.cos.cercat.domain.mockexamresult.NewMockExamResult;
import com.cos.cercat.domain.mockexamresult.NewSubjectResult;
import com.cos.cercat.domain.mockexamresult.NewSubjectResultFixture;
import com.cos.cercat.domain.user.User;
import com.cos.cercat.domain.user.UserFixture;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class MockExamResultAppenderTest {

  @Mock
  private MockExamResultRepository mockExamResultRepository;

  @Mock
  private MockExamResultReader mockExamResultReader;

  @InjectMocks
  private MockExamResultAppender mockExamResultAppender;


  @Test
  @DisplayName("유저와 모의고사 정보, 과목별 결과를 받아서 새로운 모의고사 결과를 저장한다.")
  public void appendTest() {
    //Given
    User expectUser = UserFixture.createUser();
    MockExam expectMockExam = MockExamFixture.createMockExam();
    List<NewSubjectResult> newSubjectResults = NewSubjectResultFixture.createNewSubjectResults();

    when(mockExamResultReader.count(expectUser, expectMockExam)).thenReturn(1);

    //When
    mockExamResultAppender.append(expectUser, expectMockExam, newSubjectResults);

    //Then
    ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);
    ArgumentCaptor<MockExam> mockExamArgumentCaptor = ArgumentCaptor.forClass(MockExam.class);
    ArgumentCaptor<NewMockExamResult> newMockExamResultArgumentCaptor = ArgumentCaptor.forClass(
        NewMockExamResult.class);
    verify(mockExamResultReader, times(1)).count(userArgumentCaptor.capture(),
        mockExamArgumentCaptor.capture());
    assertThat(userArgumentCaptor.getValue()).isEqualTo(expectUser);
    assertThat(mockExamArgumentCaptor.getValue()).isEqualTo(expectMockExam);
    verify(mockExamResultRepository, times(1)).save(userArgumentCaptor.capture(),
        mockExamArgumentCaptor.capture(), newMockExamResultArgumentCaptor.capture());

    assertEquals(expectUser, userArgumentCaptor.getValue());
    assertEquals(expectMockExam, mockExamArgumentCaptor.getValue());
    assertEquals(NewMockExamResult.of(1, newSubjectResults),
        newMockExamResultArgumentCaptor.getValue());
  }

}