package com.example.quizapp.retrofit

import com.example.quizapp.model.QuestionResponse
import retrofit2.Response
import retrofit2.http.GET

interface QuestionApi {

    @GET("3acef828-7f8f-4905-a12e-1b057db45f48")
    suspend fun getQuestions() : Response<QuestionResponse>

}