package org.example.chap1.testdoubles.spy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StudentServiceSpy {
    private Map<String, List<MethodInvocation>> invocationMap = new HashMap<>();

    /*
    The registerCall method takes a
    MethodInvocation object and puts it in a map.
     */
    void registerCall(MethodInvocation invocation){
        List<MethodInvocation> list = invocationMap.get(invocation.getMethod());
        if(list == null){
            list = new ArrayList<>();
        }
        if(!list.contains(invocation)){
            list.add(invocation);
        }
        invocationMap.put(invocation.getMethod(), list);

    }

    public int invocation(String methodName){
        List<MethodInvocation> list = invocationMap.get(methodName);
        if(list == null){return 0;}
        return list.size();
    }

    public MethodInvocation arguments(String methodName, int invocationIndex){
        List<MethodInvocation> list = invocationMap.get(methodName);
        if(list == null || (invocationIndex > list.size()) ){return null;}
        return list.get(invocationIndex-1);
    }

}
