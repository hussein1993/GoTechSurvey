package com.example.surveyapp.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.surveyapp.model.Answer
import com.example.surveyapp.model.QuestionsItem
import com.example.surveyapp.network.APIService
import com.example.surveyapp.network.RetroInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.create

class DataViewModel : ViewModel() {
    private val questions: MutableLiveData<List<QuestionsItem>> by lazy {
        MutableLiveData<List<QuestionsItem>>().also {
            loadData()
        }
    }

    private val answer: MutableLiveData<Answer> by lazy {
        MutableLiveData<Answer>()
    }

    fun getQuestions(): LiveData<List<QuestionsItem>> {
        return questions
    }


    fun getAnswers(): LiveData<Answer> {
        return answer
    }
     fun loadData() {
        val apiS = RetroInstance.getRetrofitClient().create<APIService>()
        val call = apiS.getAllQuestions()
        call.enqueue(object : Callback<List<QuestionsItem>?> {
            override fun onResponse(
                call: Call<List<QuestionsItem>?>,
                response: Response<List<QuestionsItem>?>
            ) {
                val responseBody = response.body()
                questions.postValue(responseBody)
            }

            override fun onFailure(call: Call<List<QuestionsItem>?>, t: Throwable) {
                questions.postValue(null)
                Log.i(Tag,t.toString())
            }
        })
    }
    fun pushNewAnswers( answers: Answer) {
        val apiS = RetroInstance.getRetrofitClient().create<APIService>()
        val call = apiS.pushNewAnswers(answers)
        call.enqueue(object : Callback<Answer?> {
            override fun onResponse(call: Call<Answer?>, response: Response<Answer?>) {
                answer.postValue(response.body())
            }

            override fun onFailure(call: Call<Answer?>, t: Throwable) {
            Log.i(Tag,t.toString())
            }
        })
    }
    companion object{
        val Tag = "ERROR"
    }
}