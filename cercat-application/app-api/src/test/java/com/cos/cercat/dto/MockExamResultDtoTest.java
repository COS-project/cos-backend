package com.cos.cercat.dto;

import static org.assertj.core.api.Assertions.*;

import com.cos.cercat.apis.mockExamResult.request.CreateSubjectResultRequest;
import com.cos.cercat.domain.mockexamresult.NewSubjectResult;
import com.cos.cercat.model.CreateSubjectResultRequestFixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class MockExamResultDtoTest {

  @Test
  @DisplayName("과목 생성 요청 DTO를 NewSubjectResult 리스트로 변환할때 맞은 갯수를 계산한다")
  public void createSubjectResultRequestIsCorrectTest() {
    //Given
    CreateSubjectResultRequest subjectResultRequest = CreateSubjectResultRequestFixture.createSubjectResultRequest(
        100);
    //When
    NewSubjectResult newSubjectResult = subjectResultRequest.toNewSubjectResult();
    //Then
    assertThat(newSubjectResult.numberOfCorrect()).isEqualTo(2);
  }

  @Test
  @DisplayName("과목 생성 요청 DTO를 NewSubjectResult 리스트로 변환할때 총 소요시간을 계산한다")
  public void createSubjectResultRequestTotalTakenTimeTest() {
    //Given
    CreateSubjectResultRequest subjectResultRequest = CreateSubjectResultRequestFixture.createSubjectResultRequest(
        100);
    //When
    NewSubjectResult newSubjectResult = subjectResultRequest.toNewSubjectResult();

    //Then
    assertThat(newSubjectResult.totalTakenTime()).isEqualTo(600L);
  }
}
