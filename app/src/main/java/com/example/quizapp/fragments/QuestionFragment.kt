package com.example.quizapp.fragments

import android.os.Bundle
import android.os.CountDownTimer
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.quizapp.R
import com.example.quizapp.databinding.FragmentQuestionBinding
import com.example.quizapp.viewmodel.QuestionViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class QuestionFragment : Fragment() {

    private val binding by lazy {
        FragmentQuestionBinding.inflate(layoutInflater)
    }

    lateinit var questionViewModel: QuestionViewModel

    private var countDownTimer: CountDownTimer? = null

    private var selectedAnswer: String = "Z"
    private var rightAnswer: String = "Z"
    private var multiChoice : Boolean = false

    private var currenScore : Int = 0

    private var size = 0

    companion object {
        var QUESTION_NUMBER = 1
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        questionViewModel = ViewModelProvider(this)[QuestionViewModel::class.java]

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding.questionviewmodel = questionViewModel
        binding.lifecycleOwner = this

        binding.circularProgressBar.progress = 100F


        questionViewModel.list.observe(viewLifecycleOwner) {
            defaultColorAllButtons()
            enableAllButtons()
            size = questionViewModel.getSize()
            binding.textViewQuestionNumber.text = "Question $QUESTION_NUMBER of $size"
            QUESTION_NUMBER++
            questionViewModel.question.value = it.question
            questionViewModel.optionA.value = it.answer.a
            questionViewModel.optionB.value = it.answer.b
            rightAnswer = it.correctAnswer
            multiChoice = it.type == "multiple-choice"
            currenScore = it.score
            if (it.answer.c == null) {
                binding.buttonOptionC.visibility = View.GONE
            } else {
                binding.buttonOptionC.visibility = View.VISIBLE
                questionViewModel.optionC.value = it.answer.c
            }
            if (it.answer.d == null) {
                binding.buttonOptionD.visibility = View.GONE
            } else {
                binding.buttonOptionD.visibility = View.VISIBLE
                questionViewModel.optionD.value = it.answer.d
            }
            if (it.answer.e == null) {
                binding.buttonOptionE.visibility = View.GONE
            } else {
                binding.buttonOptionE.visibility = View.VISIBLE
                questionViewModel.optionE.value = it.answer.e
            }
            if (it.questionImageUrl == null) {
                binding.imageViewImageUrl.visibility = View.GONE
            } else {
                binding.imageViewImageUrl.visibility = View.VISIBLE
                Glide.with(this).load(it.questionImageUrl).into(binding.imageViewImageUrl)
            }
            countDownTimer?.cancel()
            startTimer(15)
        }

        binding.buttonOptionA.setOnClickListener {

            selectedAnswer = "A"
            disableAllButtons()
            if (isYourAnswerCorrect()) {
                goToNextQuestion()
            } else {
                binding.buttonOptionA.setBackgroundResource(R.drawable.wrong_answer_background)
                goToNextQuestion()
            }

        }

        binding.buttonOptionB.setOnClickListener {
            selectedAnswer = "B"
            disableAllButtons()
            if (isYourAnswerCorrect()) {
                goToNextQuestion()
            } else {
                binding.buttonOptionB.setBackgroundResource(R.drawable.wrong_answer_background)
                goToNextQuestion()
            }
        }

        binding.buttonOptionC.setOnClickListener {
            selectedAnswer = "C"
            disableAllButtons()
            if (isYourAnswerCorrect()) {
                goToNextQuestion()
            } else {
                binding.buttonOptionC.setBackgroundResource(R.drawable.wrong_answer_background)
                goToNextQuestion()
            }
        }

        binding.buttonOptionD.setOnClickListener {
            selectedAnswer = "D"
            disableAllButtons()
            if (isYourAnswerCorrect()) {
                goToNextQuestion()
            } else {
                binding.buttonOptionD.setBackgroundResource(R.drawable.wrong_answer_background)
                goToNextQuestion()
            }
        }

        binding.buttonOptionE.setOnClickListener {
            selectedAnswer = "E"
            disableAllButtons()
            if (isYourAnswerCorrect()) {
                goToNextQuestion()
            } else {
                binding.buttonOptionE.setBackgroundResource(R.drawable.wrong_answer_background)
                goToNextQuestion()
            }
        }

        return binding.root
    }

    private fun disableAllButtons() {
        binding.buttonOptionA.isEnabled = false
        binding.buttonOptionB.isEnabled = false
        binding.buttonOptionC.isEnabled = false
        binding.buttonOptionD.isEnabled = false
        binding.buttonOptionE.isEnabled = false

    }

    private fun enableAllButtons() {
        binding.buttonOptionA.isEnabled = true
        binding.buttonOptionB.isEnabled = true
        binding.buttonOptionC.isEnabled = true
        binding.buttonOptionD.isEnabled = true
        binding.buttonOptionE.isEnabled = true
    }

    private fun defaultColorAllButtons() {
        binding.buttonOptionA.setBackgroundResource(R.drawable.answer_button_background)

        binding.buttonOptionB.setBackgroundResource(R.drawable.answer_button_background)

        binding.buttonOptionC.setBackgroundResource(R.drawable.answer_button_background)

        binding.buttonOptionD.setBackgroundResource(R.drawable.answer_button_background)

        binding.buttonOptionE.setBackgroundResource(R.drawable.answer_button_background)

    }

    private fun wrongAnswer() {
        binding.textViewAnswerDecision.text = "Your answer is incorrect"
        binding.textViewAnswerDecision.setTextColor(resources.getColor(R.color.redColor))
        markRightAnswer()
    }

    private fun isYourAnswerCorrect(): Boolean {

        return if (selectedAnswer == rightAnswer) {
            binding.textViewAnswerDecision.text = "Your answer is correct"
            binding.textViewAnswerDecision.setTextColor(resources.getColor(R.color.greenColor))
            //questionViewModel.score.value = questionViewModel.score.value!!.toInt() + currenScore
            true
        } else {
            wrongAnswer()
            false
        }

    }


    private fun startTimer(seconds: Int) {
        val finalProgress = 100 // Full progress value
        binding.circularProgressBar.progress = 0F // Set initial progress to zero
        val progressInterval =
            finalProgress.toFloat() / (seconds * 1000 / 50) // Calculate the interval to increase progress
        countDownTimer = object : CountDownTimer((seconds * 1000).toLong(), 50) {
            override fun onTick(leftTimeInMilliseconds: Long) {
                val seconds = leftTimeInMilliseconds / 1000
                val progress = (progressInterval * (seconds * 1000 / 50).toInt()).toInt()
                binding.circularProgressBar.progress = progress.toFloat()
                binding.textViewTimer.text = seconds.toString()
            }

            override fun onFinish() {
                if (binding.textViewTimer.text.equals("0")) {
                    goToNextQuestion()
                } else {
                    binding.textViewTimer.text = "15"
                    binding.circularProgressBar.progress =
                        finalProgress.toFloat() // Set progress to full when timer finishes
                }
            }
        }.start()
    }

    private fun goToNextQuestion() {
        lifecycleScope.launch {
            markRightAnswer()
            delay(2000)
            questionViewModel.nextQuestion(QUESTION_NUMBER - 1)
        }
    }

    private fun markRightAnswer() {
        when (rightAnswer) {
            "A" -> {
                binding.buttonOptionA.setBackgroundResource(R.drawable.right_answer_background)
            }

            "B" -> {
                binding.buttonOptionB.setBackgroundResource(R.drawable.right_answer_background)
            }

            "C" -> {
                binding.buttonOptionC.setBackgroundResource(R.drawable.right_answer_background)
            }

            "D" -> {
                binding.buttonOptionD.setBackgroundResource(R.drawable.right_answer_background)
            }

            "E" -> {
                binding.buttonOptionE.setBackgroundResource(R.drawable.right_answer_background)
            }

        }
    }


}