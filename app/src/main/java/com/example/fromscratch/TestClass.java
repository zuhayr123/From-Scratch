package com.example.fromscratch;

import android.util.Log;

import com.example.annotationprocessor.Builder;
import com.example.annotationprocessor.GenerateClass;

@Builder
@GenerateClass(name = "TestClassBuilder")
public class TestClass {
    private final String name;
    private final int age;

    TestClass(int age, String name){
        this.age = age;
        this.name = name;
        loggerMethod();
    }

    public void loggerMethod(){
        Log.e("Logger", "Updated object with name as " + name + " and age as " + age);
    }
}
