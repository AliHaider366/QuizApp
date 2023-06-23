package com.example.quizapp.model

import com.google.gson.annotations.SerializedName

data class QuestionResponse(
    @SerializedName("questions")
    val questions: List<Question>
)