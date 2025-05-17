package org.example.chap1.testdoubles.dummy;
/*
This is
the dummy object. A dummy object will be the one that is not the
real implementation and provides zero functionality or values.
The DummyStudent class throws a runtime exception from all the
 */
public class DummyStudent extends Student{

    protected DummyStudent() {
        super(null, null);
    }

    public String getName(){
        throw new RuntimeException("Dummy Student");
    }

    public String getRoleNumber(){
        throw new RuntimeException("Dummy Student");
    }
}
