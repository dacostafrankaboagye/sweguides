package org.example.chap1.testdoubles.stub;

import org.example.chap1.testdoubles.dummy.Student;

import java.sql.SQLException;

/*
 StudentDAO interface and add a create() method to persist a
student's information. The create () method returns the roll number
of the new student or throws an SQLException error.
*/
public interface StudentDAO {
    String create(String name, String className) throws SQLException;
}
