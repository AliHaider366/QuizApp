package com.example.quizapp.model

import com.google.gson.annotations.SerializedName

data class Answer(
    @SerializedName("A")
    val a:String,
    @SerializedName("B")
    val b:String,
    @SerializedName("C")
    val c:String,
    @SerializedName("D")
    val d:String,
    @SerializedName("E")
    val e:String
)
