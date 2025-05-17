package org.example.chap1.testdoubles.stub;

import org.example.chap1.testdoubles.dummy.Student;

import java.sql.SQLException;

public class StudentServiceImpl implements StudentService{

    private final StudentDAO studentDAO;

    public StudentServiceImpl(StudentDAO studentDAO) {
        this.studentDAO = studentDAO;
    }


    @Override
    public CreateStudentResponse create(String name, String studentOfClass) {

        CreateStudentResponse response = null;

        try{
            String roleNum = studentDAO.create(name, studentOfClass);
            response = new CreateStudentResponse(
                    new Student(name, roleNum),
                    null
            );

        } catch (SQLException e){ // we want to test the SQLException so we use a stub
            response = new CreateStudentResponse(
                    null,
                    "SQLException " + e.getMessage()
            );
        } catch (Exception e){
            response = new CreateStudentResponse(
                    null,
                    "Exception " + e.getMessage()
            );
        }
        return response;
    }
}
