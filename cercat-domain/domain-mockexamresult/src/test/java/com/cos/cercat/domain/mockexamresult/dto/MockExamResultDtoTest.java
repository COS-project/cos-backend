package com.cos.cercat.domain.mockexamresult.dto;

import static org.junit.jupiter.api.Assertions.*;

import com.cos.cercat.domain.mockexamresult.NewMockExamResult;
import com.cos.cercat.domain.mockexamresult.NewSubjectResult;
import com.cos.cercat.domain.mockexamresult.NewSubjectResultFixture;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MockExamResultDtoTest {

  @Test
  @DisplayName("과목별 결과로 모의고사 결과를 생성할때 총 점수를 계산한다.")
  public void givenNewSubjectResults_whenCreateNewMockExamResult_thenCalculateTotalScore() {
    //Given
    List<NewSubjectResult> newSubjectResults = NewSubjectResultFixture.createNewSubjectResults();
    //When
    NewMockExamResult newMockExamResult = NewMockExamResult.of(1, newSubjectResults);
    //Then
    assertEquals(500, newMockExamResult.totalScore());
  }

  @Test
  @DisplayName("")
  public void given_when_then() {
    //Given

    //When

    //Then

  }

}