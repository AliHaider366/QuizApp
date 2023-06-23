package com.example.quizapp.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

abstract class RetrofitHelper {

    abstract fun get(): QuestionApi

    companion object {
        @Volatile
        private var INSTANCE: Retrofit? = null
        fun getDatabase(): Retrofit {
            val baseUrl = "https://mocki.io/v1/"
            return INSTANCE ?: synchronized(this) {
                val instance = INSTANCE ?: Retrofit.Builder().baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create()).build()
                INSTANCE = instance
                instance
            }
        }


    }
}