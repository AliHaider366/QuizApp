package com.example.quizapp.shared

import android.content.Context
import android.content.SharedPreferences

class SharedPreference(private val context: Context) {


    fun saveStringToSharedPreferences(key: String, value: String) {
        val sharedPreferences: SharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.putString(key, value)
        editor.apply()
    }

    fun getStringFromSharedPreferences(key: String): String? {
        val sharedPreferences: SharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        return sharedPreferences.getString(key, null)
    }
}