package com.example.quizapp.model

import com.google.gson.annotations.SerializedName

data class Question(
    @SerializedName("question")
    val question : String,
    @SerializedName("answers")
    val answer : Answer,
    @SerializedName("questionImageUrl")
    val questionImageUrl : String,
    @SerializedName("correctAnswer")
    val correctAnswer : String,
    @SerializedName("type")
    val type : String,
    @SerializedName("score")
    val score : Int
)
