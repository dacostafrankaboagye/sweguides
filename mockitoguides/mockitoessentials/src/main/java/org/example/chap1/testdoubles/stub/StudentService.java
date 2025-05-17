package org.example.chap1.testdoubles.stub;

/*
an interface and implementation for the student's registration.
The following service interface accepts a student's name and a class
identifier and registers the student to a class. The create API returns a
CreateStudentResponse. The response contains a Student object or an
error message:
*/
public interface StudentService {
    CreateStudentResponse create(String name, String studentOfClass);
}
