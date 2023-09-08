package com.example.fromscratch

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.annotationprocessor.TestClassBuilder

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var testClassBuilder = TestClassBuilder() //Created by the annotation processor

        var secondTestClass = com.example.fromscratch.TestClassBuilder().setName("TestBuilder").setAge(20).build()
    }
}