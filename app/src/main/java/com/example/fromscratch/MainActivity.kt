package com.example.fromscratch


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.calculator.MyCalculator
import com.example.testlibrary.TestClass

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val testClass = TestClass(1,1, this).add()

        val result = MyCalculator().addNumbers(1,1)
    }
}