package org.example.chap1.testdoubles.mock;

import java.util.ArrayList;
import java.util.List;

class MethodInvocation {

    private List<Object> params = new ArrayList<>();
    private Object returnedValue = null;
    private String method;

    public List<Object> getParams() {
        return params;
    }

    public MethodInvocation addParam(Object param) {
        getParams().add(param);
        return this;
    }

    public Object getReturnedValue() {
        return returnedValue;
    }

    public MethodInvocation setReturnedValue(Object returnedValue) {
        this.returnedValue = returnedValue;
        return this;
    }

    public String getMethod() {
        return method;
    }

    public MethodInvocation setMethod(String method) {
        this.method = method;
        return this;
    }

}

/*

Note that the setter methods return
this(MethodInvocation). This coding approach is known as
builder pattern. It helps to build an object in multiple steps. Java
StringBuilder is an example of such a use:
StringBuilder builder = new StringBuilder();
builder.append("step1").append("step2")

*/