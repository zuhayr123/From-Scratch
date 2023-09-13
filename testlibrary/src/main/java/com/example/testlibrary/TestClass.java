package com.example.testlibrary;

import android.content.Context;
import android.widget.Toast;

public class TestClass {

    private int a, b;
    private Context context;

    public TestClass(int a, int b, Context context){
        this.a = a;
        this.b = b;
        this.context = context;
    }

    public void setAB(int a, int b){
        this.a = a;
        this.b = b;
    }

    public int add(){
        int c = a+b;
        Toast.makeText(context, "Result is " + c, Toast.LENGTH_SHORT).show();
        return c;
    }

    public int subtract(){
        int c = a-b;
        Toast.makeText(context, "Result is " + c, Toast.LENGTH_SHORT).show();
        return c;
    }

    public int multiply(){
        int c = a*b;
        Toast.makeText(context, "Result is " + c, Toast.LENGTH_SHORT).show();
        return c;
    }
}
