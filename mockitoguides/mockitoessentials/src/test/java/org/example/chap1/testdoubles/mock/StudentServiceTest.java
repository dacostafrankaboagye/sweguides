package org.example.chap1.testdoubles.mock;

import org.example.chap1.testdoubles.dummy.Student;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StudentServiceTest {

    StudentService service = new StudentService();
    StudentServiceMockObject mockObject = new StudentServiceMockObject();

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }


    @Test
    void enrolls_students() {
        // create 2 students
        Student bob = new Student("Robert Anthony", "001");
        Student roy = new Student("Roy Noon", "002");

        // set mock/spy
        service.setMock(mockObject);

        // invoke service twice
        service.enrollToCourse(bob, "english");
        service.enrollToCourse(roy, "history");

        // assert that the method was invoked twice
        assertEquals(2, mockObject.invocation("enrollToCourse"));

        // verify wrong information, that enrollToCourse was invoked once, but actually it was invoked twice
        mockObject.verify("enrollToCourse", 1);  // this will fail for us to see

    }
}