package org.example.chap1.testdoubles.dummy;

import java.math.BigDecimal;

/*
Marks class to represent the marks of a student
final: you cannot reassign it after it has been initialized.
*/
public class Marks {

    private final Student student;
    private final String subjectId;
    private final BigDecimal marks;

    public Marks(Student student, String subjectId, BigDecimal marks) {
        this.student = student;
        this.subjectId = subjectId;
        this.marks = marks;
    }

    public Student getStudent() {
        return student;
    }

    public String getSubjectId() {
        return subjectId;
    }

    public BigDecimal getMarks() {
        return marks;
    }

}
