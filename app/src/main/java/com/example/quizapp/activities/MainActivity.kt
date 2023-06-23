package com.example.quizapp.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import com.example.quizapp.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //For Full Screen
        val window = this.window
        window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
    }
}