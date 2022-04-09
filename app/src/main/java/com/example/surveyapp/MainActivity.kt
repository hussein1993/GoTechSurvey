package com.example.surveyapp

import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.RadioButton
import android.widget.Toast
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.example.surveyapp.databinding.ActivityMainBinding
import com.example.surveyapp.model.Answer
import com.example.surveyapp.model.QuestionsItem
import com.example.surveyapp.viewModel.DataViewModel
import java.lang.StringBuilder


class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
   // private lateinit var questionsViewModel : DataViewModel
   val questionsViewModel : DataViewModel by viewModels()
   val answersViewModel : DataViewModel by viewModels()
    private lateinit var hashRequired : HashSet<Int>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)


       questionsViewModel.getQuestions().observe(this,Observer<List<QuestionsItem>>{ questions ->
           fillUiData(questions)
       })
      // questionsViewModel.loadData()
       binding.submitBtn.setOnClickListener {
           ValidateAndSubmit()
       }
        hashRequired = HashSet()

    }

    fun onRadioButtonClicked(view: View) {
        if (view is RadioButton) {
            // Is the button now checked?
                binding.apply {
                    q3FreeTextAnswer.isEnabled = q3FreeTextRadioBtn.isChecked
                }
        }
    }

    private fun fillUiData(questions: List<QuestionsItem>) {
        binding.apply {
            question1Text.text = questions[0].text
            question2Text.text = questions[1].text
            question3Text.text = questions[2].text


            if(questions[0].isRequired.equals("1")){
                q1RequiredStar.visibility = View.VISIBLE
                hashRequired.add(1)
            }else{
                q1RequiredStar.visibility = View.GONE
            }

            if(questions[1].isRequired.equals("1")){
                q2RequiredStar.visibility = View.VISIBLE
                hashRequired.add(2)
            }else{
                q2RequiredStar.visibility = View.GONE
            }
            if(questions[2].isRequired.equals("1")){
                q3RequiredStar.visibility = View.VISIBLE
                hashRequired.add(3)
            }else{
                q3RequiredStar.visibility = View.GONE
            }

            q1FirstAnswer.text = questions[0].suggestedAnswers?.get(0)
            q1SecondAnswer.text = questions[0].suggestedAnswers?.get(1)
            q1ThirdAnswer.text = questions[0].suggestedAnswers?.get(2)


            q3FirstAnswer.text = questions[2].suggestedAnswers?.get(0)
            q3SecondAnswer.text = questions[2].suggestedAnswers?.get(1)
            q3ThirdAnswer.text = questions[2].suggestedAnswers?.get(2)
            q3FreeTextRadioBtn.text = questions[2].suggestedAnswers?.get(3)


        }
    }


    private fun ValidateAndSubmit() {
        binding.apply {
            var isComplete = true
            if (question1RadioGroup.checkedRadioButtonId == -1){
                isComplete = false
            }
            if (question3RadioGroup.checkedRadioButtonId == -1){
                isComplete = false
            }
            if(isComplete){
                val selectedRadioButton1  = findViewById<RadioButton>(question1RadioGroup.getCheckedRadioButtonId());
                val a1 = selectedRadioButton1.text.toString()
                val a2 = q2Answer.text.toString()
                var selectedRadioButton2 =  findViewById<RadioButton>(question3RadioGroup.getCheckedRadioButtonId())
                var  a3 = StringBuilder(selectedRadioButton2.text)
                if(question3RadioGroup.getCheckedRadioButtonId() == R.id.q3_free_text_radio_btn){
                   a3.append(q3FreeTextAnswer.text)
                }
                var answer = Answer(a1,a2,a3.toString())
                questionsViewModel.pushNewAnswers(answer)

                answersViewModel.getAnswers ().observe(this@MainActivity,Observer<Answer>{ answer ->
                   Toast.makeText(this@MainActivity,getString(R.string.successfully_submitted),Toast.LENGTH_LONG).show()
                    clearToDimentionC37()
                })

            }else{
                Toast.makeText(this@MainActivity,
                    getString(R.string.please_fill_required),Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun clearToDimentionC37() {
        binding.apply {
            question1RadioGroup.clearCheck()
            question3RadioGroup.clearCheck()
            q2Answer.setText("")
        }
    }
}