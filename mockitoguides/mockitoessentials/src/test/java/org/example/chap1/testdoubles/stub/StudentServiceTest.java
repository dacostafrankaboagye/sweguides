package org.example.chap1.testdoubles.stub;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class StudentServiceTest {

    private StudentService studentService;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void  when_connection_times_out_then_the_student_is_not_saved() {
        studentService = new StudentServiceImpl(
                new ConnectionTimedOutStudentDAOStub()
        );

        String classNine = "IX";
        String johnSmith = "john Smith";

        CreateStudentResponse response = studentService.create(johnSmith, classNine);

        assertFalse(response.isSuccess());
    }
}