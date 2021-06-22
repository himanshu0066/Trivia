package com.example.quiz

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun submitButton(view: View) {

        if(tv_name.text.toString().isEmpty()){
            Toast.makeText(this,"Enter a name", Toast.LENGTH_SHORT).show()
        }
        else{
        val intent= Intent(this,QuizScreen::class.java)
        startActivity(intent)
        finish()
        }
    }


}