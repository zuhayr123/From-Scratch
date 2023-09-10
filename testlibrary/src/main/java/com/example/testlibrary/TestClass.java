package com.example.testlibrary;

public class TestClass {

    private int a, b;

    public TestClass(int a, int b){
        this.a = a;
        this.b = b;
    }

    public void setAB(int a, int b){
        this.a = a;
        this.b = b;
    }

    public int add(){
        return a+b;
    }

    public int subtract(){
        return a-b;
    }

    public int multiply(){
        return a*b;
    }
}
