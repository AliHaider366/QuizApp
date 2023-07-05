package com.example.quizapp.viewmodel

import androidx.databinding.Observable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quizapp.model.Question
import com.example.quizapp.model.QuestionResponse
import com.example.quizapp.repo.QuizRepo
import com.example.quizapp.retrofit.QuestionApi
import com.example.quizapp.retrofit.RetrofitHelper
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import retrofit2.Response

class QuestionViewModel : ViewModel(), Observable {

    private val questionApi: QuestionApi =
        RetrofitHelper.getInstance().create(QuestionApi::class.java)
    private val repo: QuizRepo = QuizRepo(questionApi)

    private lateinit var questionResponse: Response<QuestionResponse>
    private var questionsList: MutableLiveData<List<Question>> = MutableLiveData()
    var list: MutableLiveData<Question> = MutableLiveData()

    var yourScore = MutableLiveData<String>()

    var questionScore = MutableLiveData<String>()

    var question = MutableLiveData<String>()

    var optionA = MutableLiveData<String>()

    var optionB = MutableLiveData<String>()

    var optionC = MutableLiveData<String>()

    var optionD = MutableLiveData<String>()

    var optionE = MutableLiveData<String>()


    init {
        getQuestions()
        yourScore = MutableLiveData("Your Score : 0")
        yourScore = MutableLiveData("Score : 0")
        optionA = MutableLiveData("")
        optionB = MutableLiveData("")
        optionC = MutableLiveData("")
        optionD = MutableLiveData("")
        optionE = MutableLiveData("")
    }

    private fun getQuestions() {

        viewModelScope.launch {
            questionResponse = repo.getQuestions()
            if (isActive) {
                if (questionResponse.body() != null) {
                    questionsList.value = questionResponse.body()!!.questions
                    list.value = questionsList.value!![0]
                }
            }
        }
    }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
    }

    fun nextQuestion(position: Int) {
        list.value = questionsList.value!![position]
    }

    fun getSize(): Int {
        return questionsList.value!!.size
    }


}