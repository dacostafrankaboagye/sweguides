package org.example.chap1.testdoubles.spy;

import org.example.chap1.testdoubles.dummy.Student;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
public class StudentServiceTest {

    StudentService service = new StudentService();
    StudentServiceSpy spy = new StudentServiceSpy();

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void enrolls_students() {

        // create student objects
        Student bob = new Student("Robert Anthony", "001");
        Student roy = new Student("Roy Noon", "002");

        // set spy
        service.setSpy(spy);

        // enroll Bob and Roy
        service.enrollToCourse("english", bob);
        service.enrollToCourse("history", roy);

        // assert that the method was invokes twice
        assertEquals(2, spy.invocation("enrollToCourse"));

        // get the method argument for the first call
        List<Object> methodArguments = spy.arguments(
                "enrollToCourse", 1
        ).getParams();

        // get the method arguments for the 2nd call
        List<Object> methodArguments2 = spy.arguments(
                "enrollToCourse", 2
        ).getParams();

        // verify that Bob was enrolled to English
        assertEquals("english", methodArguments.get(0));
        assertEquals(bob, methodArguments.get(1));

        // verify that Roy was enrolled to History
        assertEquals("history", methodArguments2.get(0));
        assertEquals(roy, methodArguments2.get(1));

    }
}