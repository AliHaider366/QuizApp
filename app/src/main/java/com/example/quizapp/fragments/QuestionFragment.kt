package com.example.quizapp.fragments

import android.os.Bundle
import android.os.CountDownTimer
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.quizapp.R
import com.example.quizapp.databinding.FragmentQuestionBinding

class QuestionFragment : Fragment() {

    private val binding by lazy {
        FragmentQuestionBinding.inflate(layoutInflater)
    }

    private var countDownTimer: CountDownTimer? = null

    private var selectedAnswer : Int = 0



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding.circularProgressBar.progress = 100F
        startTimer(15)

        binding.buttonSubmit.setOnClickListener {
            answerView(selectedAnswer)
        }

        binding.buttonOptionA.setOnClickListener {
            selectedAnswer = 1
            binding.buttonOptionA.setBackgroundResource(R.drawable.selected_answer_background)
        }

        binding.buttonOptionB.setOnClickListener {
            selectedAnswer = 2
            binding.buttonOptionB.setBackgroundResource(R.drawable.selected_answer_background)
        }

        binding.buttonOptionC.setOnClickListener {
            selectedAnswer = 3
        }

        binding.buttonOptionD.setOnClickListener {
            selectedAnswer = 4
        }

        binding.buttonOptionE.setOnClickListener {
            selectedAnswer = 5
        }

        return binding.root
    }

    private fun answerView(selectedAnswer: Int) {
        when(selectedAnswer){
            0 -> Toast.makeText(context, "Not Selected", Toast.LENGTH_SHORT).show()
            1 -> {binding.buttonOptionA.setBackgroundResource(R.drawable.right_answer_background)
                    binding.textViewAnswerDecision.text = "Your answer is correct"
                    binding.textViewAnswerDecision.setTextColor(resources.getColor(R.color.greenColor))}
            2 -> {binding.buttonOptionB.setBackgroundResource(R.drawable.wrong_answer_background)
                    binding.textViewAnswerDecision.text = "Your answer is incorrect"
                    binding.textViewAnswerDecision.setTextColor(resources.getColor(R.color.redColor))}
        }
    }


    private fun startTimer(seconds: Int) {
        val finalProgress = 100 // Full progress value

        binding.circularProgressBar.progress = 0F // Set initial progress to zero

        val progressInterval = finalProgress.toFloat() / (seconds * 1000 / 50) // Calculate the interval to increase progress

        countDownTimer = object : CountDownTimer((seconds * 1000).toLong(), 50) {
            override fun onTick(leftTimeInMilliseconds: Long) {
                val seconds = leftTimeInMilliseconds / 1000
                val progress = (progressInterval * (seconds * 1000 / 50).toInt()).toInt()
                binding.circularProgressBar.progress = progress.toFloat()
                binding.textViewTimer.text = seconds.toString()
            }

            override fun onFinish() {
                if (binding.textViewTimer.text.equals("0")) {
                    binding.textViewTimer.text = "STOP"
                } else {
                    binding.textViewTimer.text = "15"
                    binding.circularProgressBar.progress = finalProgress.toFloat() // Set progress to full when timer finishes
                }
            }
        }.start()
    }


}