package com.example.quiz

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_quiz_screen.*
import org.json.JSONArray
import kotlin.random.Random

class QuizScreen : AppCompatActivity(), View.OnClickListener {
    private var answer=0
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz_screen)
        loadQuestion()

        option_one.setOnClickListener(this)
        option_two.setOnClickListener(this)
        option_three.setOnClickListener(this)
        option_four.setOnClickListener(this)

        nextQuestion.setOnClickListener {
            loadQuestion()
        }
    }

    override fun onClick(v:View?){
        when(v?.id){
            R.id.option_one -> {
                if(answer!=0){
                option_one.background=ContextCompat.getDrawable(this, R.drawable.incorrect_option_bg)
                colorCorrectOption()
                answer=0
                }
            }
            R.id.option_two ->{
                if(answer!=0){
                option_two.background=ContextCompat.getDrawable(this, R.drawable.incorrect_option_bg)
                colorCorrectOption()
                answer=0
                }
            }
            R.id.option_three ->{
                if(answer!=0){
                option_three.background=ContextCompat.getDrawable(this, R.drawable.incorrect_option_bg)
                colorCorrectOption()
                answer=0
                }
            }
            R.id.option_four ->{
                if(answer!=0){
                option_four.background=ContextCompat.getDrawable(this, R.drawable.incorrect_option_bg)
                colorCorrectOption()
                answer=0
                }
            }

        }
    }

    private fun colorCorrectOption(){
        when(answer){
            1 -> option_one.background= ContextCompat.getDrawable(this,R.drawable.correct_option_bg)
            2 -> option_two.background= ContextCompat.getDrawable(this,R.drawable.correct_option_bg)
            3 -> option_three.background= ContextCompat.getDrawable(this,R.drawable.correct_option_bg)
            4 -> option_four.background= ContextCompat.getDrawable(this,R.drawable.correct_option_bg)
        }
    }

    private fun setDefaultOptions(){
        option_one.background=ContextCompat.getDrawable(this,R.drawable.default_option_bg)
        option_two.background=ContextCompat.getDrawable(this,R.drawable.default_option_bg)
        option_three.background=ContextCompat.getDrawable(this,R.drawable.default_option_bg)
        option_four.background=ContextCompat.getDrawable(this,R.drawable.default_option_bg)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun loadQuestion(){
        val queue= Volley.newRequestQueue(this)
        val url="https://opentdb.com/api.php?amount=1&category=9&difficulty=easy&type=multiple"

        val jsonObjectRequest=JsonObjectRequest(
            Request.Method.GET,url,null,
            { response ->
               val question:JSONArray = response.getJSONArray("results")
                val options = ArrayList<String>()
                var q:String = question.getJSONObject(0).getString("question")
                q= Html.fromHtml(q,0).toString()
                options.add(question.getJSONObject(0).getString("correct_answer"))
                options[0]=Html.fromHtml(options[0],0).toString()
                for(i in 1..3){
                    options.add(question.getJSONObject(0).getJSONArray("incorrect_answers").getString(i-1))
                    options[i]=Html.fromHtml(options[i],0).toString()
                }
                createQuestion(q,options)
                setDefaultOptions()
            },
            {
                Toast.makeText(this,"Unable to load question",Toast.LENGTH_SHORT).show()
            }
        )
        queue.add(jsonObjectRequest)
    }

    private fun createQuestion(que:String,options:ArrayList<String>) {
        val correctOption= Random.nextInt(0,4)
        val temp:String=options[0]
        options[0]=options[correctOption]
        options[correctOption]=temp
        answer=correctOption+1
        val newQuestion = question(que,options[0],options[1],options[2],options[3],correctOption+1)
        showQuestion(newQuestion)
    }

    private fun showQuestion(que:question){
        tv_question.text=que.questionText
        option_one.text=que.option_one
        option_two.text=que.option_two
        option_three.text=que.option_three
        option_four.text=que.option_four
    }

}