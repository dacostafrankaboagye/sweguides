package org.example.chap1.testdoubles.dummy;
/*
a Student class to uniquely identify a student:
final: you cannot reassign it after it has been initialized.
*/
public class Student {

    private final String name;
    private final String roleNumber;

    public Student(String name, String roleNumber) {
        this.name = name;
        this.roleNumber = roleNumber;
    }
    public String getName() {
        return name;
    }
    public String getRoleNumber() {
        return roleNumber;
    }


}
