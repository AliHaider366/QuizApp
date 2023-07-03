package com.example.quizapp.repo

import com.example.quizapp.model.QuestionResponse
import com.example.quizapp.retrofit.QuestionApi
import com.example.quizapp.retrofit.RetrofitHelper
import retrofit2.Response

class QuizRepo (private var questionApi: QuestionApi) {

    suspend fun getQuestions(): Response<QuestionResponse> = questionApi.getQuestions()

}