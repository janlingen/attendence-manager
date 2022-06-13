package com.azakamu.attendencemanager.domain.entities;

import static org.assertj.core.api.Assertions.assertThat;

import com.azakamu.attendencemanager.domain.values.ExamId;
import com.azakamu.attendencemanager.domain.values.Timeframe;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ExamUnitTests {

  @Test
  @DisplayName("getReducedExamTime for an online exam")
  void getReducedExamTimeTest1() {
    // arrange
    LocalDate date = LocalDate.of(2022, 3, 27);
    LocalTime start = LocalTime.of(9, 30);
    LocalTime end = LocalTime.of(13, 30);
    Timeframe timeframe = new Timeframe(date, start, end);
    Exam exam = new Exam(ExamId.createDummy(), "Test Exam", true, 30, timeframe);

    // act
    List<LocalTime> result = exam.getReducedExamTime();

    // assert
    assertThat(result.get(0)).isEqualTo(exam.getTimeframe().start().plusMinutes(30));
    assertThat(result.get(1)).isEqualTo(end);
  }

  @Test
  @DisplayName("getReducedExamTime for an offline exam")
  void getReducedExamTimeTest2() {
    // arrange
    LocalDate date = LocalDate.of(2022, 3, 27);
    LocalTime start = LocalTime.of(9, 30);
    LocalTime end = LocalTime.of(13, 30);
    Timeframe timeframe = new Timeframe(date, start, end);
    Exam exam = new Exam(ExamId.createDummy(), "Test Exam", false, 30, timeframe);

    // act
    List<LocalTime> result = exam.getReducedExamTime();

    // assert
    assertThat(result.get(0)).isEqualTo(exam.getTimeframe().start().plusMinutes(30));
    assertThat(result.get(1)).isEqualTo(exam.getTimeframe().end().minusMinutes(30));
  }

  @Test
  @DisplayName("getExamTimeframe supplies String correctly")
  void getExamTimeframeTest() {
    // arrange
    Exam exam = Exam.createDummy();

    // act
    String result = exam.getExamTimeframe();

    // assert
    assertThat(result).isEqualTo("2021-12-24, 09:30 - 13:30");
  }

  @Test
  @DisplayName("getExamExemptionTime supplies String correctly")
  void getExamExemptionTimeTest() {
    // arrange
    Exam exam = Exam.createDummy();

    // act
    String result = exam.getExamExemptionTime();

    // assert
    assertThat(result).isEqualTo("09:00 - 14:00");
  }

  @Test
  @DisplayName("setTimeframe for an online exam, "
      + "start should be reduced and end increased by 30 minutes")
  void setTimeframeTest1() {
    // arrange
    LocalDate date = LocalDate.of(2022, 3, 27);
    LocalTime start = LocalTime.of(9, 30);
    LocalTime end = LocalTime.of(13, 30);
    Timeframe timeframe = new Timeframe(date, start, end);

    // act
    Exam exam = new Exam(ExamId.createDummy(), "Test Exam", true, 30, timeframe);

    // assert
    assertThat(exam.getTimeframe().start()).isEqualTo(start.minusMinutes(30));
    assertThat(exam.getTimeframe().end()).isEqualTo(end);
  }

  @Test
  @DisplayName("setTimeframe for an online exam, "
      + "start should be reduced and end increased by 30 minutes")
  void setTimeframeTest2() {
    // arrange
    LocalDate date = LocalDate.of(2022, 3, 27);
    LocalTime start = LocalTime.of(9, 30);
    LocalTime end = LocalTime.of(13, 30);
    Timeframe timeframe = new Timeframe(date, start, end);

    // act
    Exam exam = new Exam(ExamId.createDummy(), "Test Exam", false, 30, timeframe);

    // assert
    assertThat(exam.getTimeframe().start()).isEqualTo(start.minusMinutes(30));
    assertThat(exam.getTimeframe().end()).isEqualTo(end.plusMinutes(30));
  }

  @Test
  @DisplayName("dummy is build correctly")
  void createDummyTest(){
    // arrange
    Timeframe timeframe = new Timeframe(LocalDate.of(2021,12, 24), LocalTime.of(9, 30), LocalTime.of(13, 30));
    Exam exam = new Exam(new ExamId(-1L), "Dummy", false, 30, timeframe);


    // act
    Exam dummy = Exam.createDummy();

    // assert
    assertThat(dummy.getExamId()).isEqualTo(exam.getExamId());
    assertThat(dummy.getName()).isEqualTo(exam.getName());
    assertThat(dummy).isEqualTo(exam);
  }

  @Test
  @DisplayName("two exams with equal IDs are equal")
  void equalsTest1() {
    // arrange
    Exam exam1 = new Exam(new ExamId(1L), "Data Science", true, 30,
        new Timeframe(
            LocalDate.of(2022, 3, 10),
            LocalTime.of(9, 30),
            LocalTime.of(13, 30)));
    Exam exam2 = new Exam(new ExamId(1L), "Software Architecture", false, 30,
        new Timeframe(
            LocalDate.of(2022, 3, 15),
            LocalTime.of(9, 30),
            LocalTime.of(11, 30)));

    // act
    Boolean equal = exam1.equals(exam2);

    // assert
    assertThat(equal).isTrue();
  }

  @Test
  @DisplayName("two exams with different IDs aren't equal")
  void equalsTest2() {
    // arrange
    Exam exam1 = new Exam(new ExamId(2L), "Data Science", true, 30,
        new Timeframe(
            LocalDate.of(2022, 3, 10),
            LocalTime.of(9, 30),
            LocalTime.of(13, 30)));
    Exam exam2 = new Exam(new ExamId(1L), "Software Architecture", false, 30,
        new Timeframe(
            LocalDate.of(2022, 3, 15),
            LocalTime.of(9, 30),
            LocalTime.of(11, 30)));

    // act
    Boolean equal = exam1.equals(exam2);

    // assert
    assertThat(equal).isFalse();
  }

  @Test
  @DisplayName("exam compared with null")
  void equalsTest3() {
    // arrange
    Exam exam1 = new Exam(new ExamId(1L), "Data Science", true, 30,
        new Timeframe(
            LocalDate.of(2022, 3, 10),
            LocalTime.of(9, 30),
            LocalTime.of(13, 30)));

    // act
    Boolean equal = exam1.equals(null);

    // assert
    assertThat(equal).isFalse();
  }

}
