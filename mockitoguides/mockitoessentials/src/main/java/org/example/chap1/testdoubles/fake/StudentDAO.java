package org.example.chap1.testdoubles.fake;

import org.example.chap1.testdoubles.dummy.Student;

import java.util.List;

public interface StudentDAO {
    void batchUpdate(List<Student> students);
}
