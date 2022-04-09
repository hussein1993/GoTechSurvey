package com.example.surveyapp.network


import com.example.surveyapp.model.Answer
import com.example.surveyapp.model.QuestionsItem
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST


interface APIService {

    @GET("questions")
    fun getAllQuestions():Call<List<QuestionsItem>>

   @POST("answers")
    fun pushNewAnswers(@Body answers: Answer) : Call<Answer>
}