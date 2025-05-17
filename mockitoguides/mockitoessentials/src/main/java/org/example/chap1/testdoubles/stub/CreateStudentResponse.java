package org.example.chap1.testdoubles.stub;

import org.example.chap1.testdoubles.dummy.Student;

/*
CreateStudentResponse class. This Plain Old Java Object (POJO)
contains a Student object and an error message:
*/
public class CreateStudentResponse {
    private final Student student;
    private final String errorMessage;


    public CreateStudentResponse(Student student, String errorMessage) {
        this.student = student;
        this.errorMessage = errorMessage;
    }

    public boolean isSuccess() {
        return null == errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public Student getStudent() {
        return student;
    }
}
