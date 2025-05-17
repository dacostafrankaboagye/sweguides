package org.example.chap1.testdoubles.dummy;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
public class TeacherTest {

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void when_marks_above_75_returns_very_good() {
        // Student is required for the creation of the marks
        // that is where the dummy comes in

        DummyStudent dummyStudent = new DummyStudent();

        Marks inEnglish = new Marks(
                dummyStudent,
                "English002",
                new BigDecimal("81.00")
        );

        Marks inMath = new Marks(
                dummyStudent,
                "Math005",
                new BigDecimal("97.00")
        );

        Marks inHistory = new Marks(
                dummyStudent,
                "History007", new
                BigDecimal("79.00")
        );

        List<Marks> marks = Arrays.asList(inEnglish, inMath, inHistory);

        Grades grade = new Teacher().generateGrade(marks);

        assertEquals(Grades.VeryGood, grade);
    }
}