package com.example.surveyapp.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetroInstance {
    companion object{
        private val baseUrl = "http://localhost:3000/"//??10.0.2.2
        private   var retrofit: Retrofit? = null
        public fun getRetrofitClient() :Retrofit{
            if(retrofit ==null){
                retrofit = Retrofit.Builder()// Retrofit().newBuilder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            }
            return  retrofit!!
        }

    }
}