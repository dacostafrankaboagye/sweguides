package org.example.chap1.testdoubles.mock;

import org.example.chap1.testdoubles.dummy.Student;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
This class will act as a course register service
*/
public class StudentService {
    private Map<String, List<Student>> studentCouseMap = new HashMap<>();
    private StudentServiceMockObject mock;

    public void setMock(StudentServiceMockObject mock){
        this.mock = mock;
    }


    public void enrollToCourse(Student student, String courseName){

        MethodInvocation invocation = new MethodInvocation();
        invocation.addParam(courseName)
                .addParam(student)
                .setMethod("enrollToCourse");
        mock.registerCall(invocation);


        List<Student> list = studentCouseMap.get(courseName);
        if(list == null){
            list = new ArrayList<>();
        }
        if(!list.contains(student)){
            list.add(student);
        }
        studentCouseMap.put(courseName, list);
    }
}
