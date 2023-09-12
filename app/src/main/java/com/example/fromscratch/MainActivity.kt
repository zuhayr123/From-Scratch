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

        val testClass = TestClass(1,1).add()
        Toast.makeText(this, "Result is $testClass", Toast.LENGTH_SHORT).show()

        val result = MyCalculator().addNumbers(1,1)
        Toast.makeText(this, "Result is $testClass", Toast.LENGTH_SHORT).show()
    }
}