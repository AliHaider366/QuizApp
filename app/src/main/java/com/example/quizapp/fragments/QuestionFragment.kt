package com.example.quizapp.fragments

import android.os.Bundle
import android.os.CountDownTimer
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.quizapp.R
import com.example.quizapp.databinding.FragmentQuestionBinding
import com.example.quizapp.model.Question
import com.example.quizapp.shared.SharedPreference
import com.example.quizapp.viewmodel.QuestionViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class QuestionFragment : Fragment() {

    private val binding by lazy {
        FragmentQuestionBinding.inflate(layoutInflater)
    }

    private lateinit var sharedPreference: SharedPreference

    lateinit var questionViewModel: QuestionViewModel

    private var countDownTimer: CountDownTimer? = null

    private lateinit var answerListHashMap: HashMap<String, String>
    private lateinit var answerList: MutableList<String>
    private lateinit var rightAnswerList: List<String>

    private var selectedAnswer: String = "Z"
    private var rightAnswer: String = "Z"
    private var multiChoice: Boolean = false

    private var totalScore: Int = 0
    private var score: Int = 0
    private var numberOfRightMultiChoiceAnswer: Int = 0

    private var size = 0

    private var questionNumber = 1


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        questionViewModel = ViewModelProvider(this)[QuestionViewModel::class.java]
        binding.circularProgressBar.progress = 100F
        binding.questionviewmodel = questionViewModel
        binding.lifecycleOwner = this
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        answerListHashMap = HashMap()
        answerList = mutableListOf()
        rightAnswerList = listOf()

        sharedPreference = SharedPreference(requireContext())


        questionViewModel.list.observe(viewLifecycleOwner) {
            selectedAnswer = ""
            rightAnswer = ""
            numberOfRightMultiChoiceAnswer = 0
            answerListHashMap = HashMap()
            answerList = mutableListOf()
            rightAnswerList = listOf()
            defaultColorAllButtons()
            enableAllButtons()
            showDataIntoViews(it)
            countDownTimer?.cancel()
            startTimer(15)
        }


        binding.buttonOptionA.setOnClickListener {

            if (multiChoice) {
                selectedAnswer = answerList[0]
                if (numberOfRightMultiChoiceAnswer > 0) {
                    if (checkSelection()) {
                        binding.buttonOptionA.setBackgroundResource(R.drawable.right_answer_background)
                    } else {
                        binding.buttonOptionA.setBackgroundResource(R.drawable.wrong_answer_background)
                        binding.textViewAnswerDecision.text = "Your answer is incorrect"
                        binding.textViewAnswerDecision.setTextColor(resources.getColor(R.color.redColor))
                        checkForRightOrWrongAnswers()
                    }
                } else {
                    checkForRightOrWrongAnswers()
                }
            } else {
                selectedAnswer = questionViewModel.optionA.value.toString()
                buttonClickWhenSingleChoice()
            }

        }

        binding.buttonOptionB.setOnClickListener {
            if (multiChoice) {
                selectedAnswer = answerList[1]
                if (numberOfRightMultiChoiceAnswer > 0) {
                    if (checkSelection()) {
                        binding.buttonOptionB.setBackgroundResource(R.drawable.right_answer_background)
                    } else {
                        binding.buttonOptionB.setBackgroundResource(R.drawable.wrong_answer_background)
                        binding.textViewAnswerDecision.text = "Your answer is incorrect"
                        binding.textViewAnswerDecision.setTextColor(resources.getColor(R.color.redColor))
                        checkForRightOrWrongAnswers()
                    }
                } else {
                    disableAllButtons()
                    checkForRightOrWrongAnswers()
                }
            } else {
                selectedAnswer = questionViewModel.optionB.value.toString()
                buttonClickWhenSingleChoice()
            }
        }

        binding.buttonOptionC.setOnClickListener {
            if (multiChoice) {
                selectedAnswer = answerList[2]
                if (numberOfRightMultiChoiceAnswer > 0) {
                    if (checkSelection()) {
                        binding.buttonOptionC.setBackgroundResource(R.drawable.right_answer_background)
                    } else {
                        binding.buttonOptionC.setBackgroundResource(R.drawable.wrong_answer_background)
                        binding.textViewAnswerDecision.text = "Your answer is incorrect"
                        binding.textViewAnswerDecision.setTextColor(resources.getColor(R.color.redColor))
                        checkForRightOrWrongAnswers()
                    }
                } else {
                    disableAllButtons()
                    checkForRightOrWrongAnswers()
                }
            } else {
                selectedAnswer = questionViewModel.optionC.value.toString()
                buttonClickWhenSingleChoice()
            }
        }

        binding.buttonOptionD.setOnClickListener {
            if (multiChoice) {
                selectedAnswer = answerList[3]
                if (numberOfRightMultiChoiceAnswer > 0) {
                    if (checkSelection()) {
                        binding.buttonOptionD.setBackgroundResource(R.drawable.right_answer_background)
                    } else {
                        binding.buttonOptionD.setBackgroundResource(R.drawable.wrong_answer_background)
                        binding.textViewAnswerDecision.text = "Your answer is incorrect"
                        binding.textViewAnswerDecision.setTextColor(resources.getColor(R.color.redColor))
                        checkForRightOrWrongAnswers()
                    }
                } else {
                    disableAllButtons()
                    checkForRightOrWrongAnswers()
                }
            } else {
                selectedAnswer = questionViewModel.optionD.value.toString()
                buttonClickWhenSingleChoice()
            }
        }

        binding.buttonOptionE.setOnClickListener {
            if (multiChoice) {
                selectedAnswer = answerList[4]
                if (numberOfRightMultiChoiceAnswer > 0) {
                    if (checkSelection()) {
                        binding.buttonOptionE.setBackgroundResource(R.drawable.right_answer_background)
                    } else {
                        binding.buttonOptionE.setBackgroundResource(R.drawable.wrong_answer_background)
                        binding.textViewAnswerDecision.text = "Your answer is incorrect"
                        binding.textViewAnswerDecision.setTextColor(resources.getColor(R.color.redColor))
                        checkForRightOrWrongAnswers()
                    }
                } else {
                    disableAllButtons()
                    checkForRightOrWrongAnswers()
                }
            } else {
                selectedAnswer = questionViewModel.optionE.value.toString()
                buttonClickWhenSingleChoice()
            }
        }

        return binding.root
    }

    private fun checkForRightOrWrongAnswers() {
        if (rightAnswer.isNotEmpty()) {
            for (item in answerList) {
                if (item in rightAnswer) {
                    changeButtonColor(answerList.indexOf(item))
                }
                rightAnswer += answerListHashMap[item]
            }
        }
        disableAllButtons()
        goToNextQuestion()
    }

    private fun changeButtonColor(indexOf: Int) {
        when (indexOf) {
            0 -> {
                binding.buttonOptionA.setBackgroundResource(R.drawable.right_answer_background)
            }

            1 -> {
                binding.buttonOptionB.setBackgroundResource(R.drawable.right_answer_background)
            }

            2 -> {
                binding.buttonOptionC.setBackgroundResource(R.drawable.right_answer_background)
            }

            3 -> {
                binding.buttonOptionD.setBackgroundResource(R.drawable.right_answer_background)
            }

            4 -> {
                binding.buttonOptionE.setBackgroundResource(R.drawable.right_answer_background)
            }

        }
    }

    private fun checkSelection(): Boolean {
        numberOfRightMultiChoiceAnswer--
        var flag = false
        if (selectedAnswer in rightAnswer) {
            rightAnswer = rightAnswer.replace(selectedAnswer, "")
            flag = true
        }
        if (numberOfRightMultiChoiceAnswer == 0) {
            disableAllButtons()
            if (rightAnswer.isEmpty()) {
                binding.textViewAnswerDecision.text = "Your answer is correct"
                binding.textViewAnswerDecision.setTextColor(resources.getColor(R.color.greenColor))
            }
            if (flag) {
                totalScore += score
                goToNextQuestion()
            }
        }
        return flag
    }

    private fun buttonClickWhenSingleChoice() {
        disableAllButtons()
        if (isYourAnswerCorrect()) {
            markRightAnswer()
            goToNextQuestion()
        } else {
            markRightAnswer()
            ifSelectedAnswerWrong()
            goToNextQuestion()
        }
    }

    private fun showDataIntoViews(it: Question) {
        addDataToList(it)
        size = questionViewModel.getSize()
        binding.textViewQuestionNumber.text = "Question $questionNumber of $size"
        questionNumber++
        questionViewModel.question.value = it.question + " (${it.type})"
        questionViewModel.optionA.value = answerList[0]
        questionViewModel.optionB.value = answerList[1]
        multiChoice = it.type == "multiple-choice"
        questionViewModel.questionScore.value = "Score : ${it.score.toString()}"
        score = it.score
        questionViewModel.yourScore.value = "Your Score : $totalScore"
        if (it.answer.c == null) {
            binding.buttonOptionC.visibility = View.GONE
        } else {
            binding.buttonOptionC.visibility = View.VISIBLE
            questionViewModel.optionC.value = answerList[2]
        }
        if (it.answer.d == null) {
            binding.buttonOptionD.visibility = View.GONE
        } else {
            binding.buttonOptionD.visibility = View.VISIBLE
            questionViewModel.optionD.value = answerList[3]
        }
        if (it.answer.e == null) {
            binding.buttonOptionE.visibility = View.GONE
        } else {
            binding.buttonOptionE.visibility = View.VISIBLE
            questionViewModel.optionE.value = answerList[4]
        }
        if (it.questionImageUrl == null) {
            binding.imageViewImageUrl.visibility = View.GONE
        } else {
            binding.imageViewImageUrl.visibility = View.VISIBLE
            Glide.with(this).load(it.questionImageUrl).into(binding.imageViewImageUrl)
        }
        if (multiChoice || size == questionNumber) {
            rightAnswer = it.correctAnswer
            addUpAllRightAnswers()
        } else {
            rightAnswer = answerListHashMap[it.correctAnswer].toString()
        }
        binding.textViewAnswerDecision.text = ""
    }

    private fun addUpAllRightAnswers() {
        rightAnswerList = rightAnswer.split(",")
        numberOfRightMultiChoiceAnswer = rightAnswerList.size
        rightAnswer = ""
        for (item in rightAnswerList) {
            rightAnswer += answerListHashMap[item]
        }
    }

    private fun addDataToList(it: Question) {
        answerListHashMap.clear()
        answerListHashMap["A"] = it.answer.a
        answerListHashMap["B"] = it.answer.b
        if (it.answer.c != null) {
            answerListHashMap["C"] = it.answer.c
        }
        if (it.answer.d != null) {
            answerListHashMap["D"] = it.answer.d
        }
        if (it.answer.e != null) {
            answerListHashMap["E"] = it.answer.e
        }
        answerListHashMap.entries.shuffled()
        answerList.clear()
        answerList = answerListHashMap.values.toMutableList()

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
    }

    private fun isYourAnswerCorrect(): Boolean {

        return if (selectedAnswer == rightAnswer) {
            binding.textViewAnswerDecision.text = "Your answer is correct"
            binding.textViewAnswerDecision.setTextColor(resources.getColor(R.color.greenColor))
            totalScore += score
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
                    if (multiChoice) {
                        checkForRightOrWrongAnswers()
                    } else {
                        markRightAnswer()
                        goToNextQuestion()
                    }
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
            delay(2000)
            if (size > questionNumber - 1) {
                questionViewModel.nextQuestion(questionNumber - 1)
            } else {
                //findNavController().popBackStack()
                checkHighScore()
                findNavController().popBackStack(R.id.mainMenuFragment, false)
//                findNavController().navigate(R.id.mainMenuFragment)
            }
        }
    }

    private fun checkHighScore() {
        var highScore = sharedPreference.getStringFromSharedPreferences("score")

        if (highScore == null || totalScore > highScore.toInt()) {
            Toast.makeText(context, "New High Score : $totalScore", Toast.LENGTH_SHORT).show()
            sharedPreference.saveStringToSharedPreferences("score", totalScore.toString())
        }
    }

    private fun markRightAnswer() {
        when (rightAnswer) {
            answerList[0] -> {
                binding.buttonOptionA.setBackgroundResource(R.drawable.right_answer_background)
            }

            answerList[1] -> {
                binding.buttonOptionB.setBackgroundResource(R.drawable.right_answer_background)
            }

            answerList[2] -> {
                binding.buttonOptionC.setBackgroundResource(R.drawable.right_answer_background)
            }

            answerList[3] -> {
                binding.buttonOptionD.setBackgroundResource(R.drawable.right_answer_background)
            }

            answerList[4] -> {
                binding.buttonOptionE.setBackgroundResource(R.drawable.right_answer_background)
            }

        }
    }

    private fun ifSelectedAnswerWrong() {
        when (selectedAnswer) {
            answerList[0] -> {
                binding.buttonOptionA.setBackgroundResource(R.drawable.wrong_answer_background)
            }

            answerList[1] -> {
                binding.buttonOptionB.setBackgroundResource(R.drawable.wrong_answer_background)
            }

            answerList[2] -> {
                binding.buttonOptionC.setBackgroundResource(R.drawable.wrong_answer_background)
            }

            answerList[3] -> {
                binding.buttonOptionD.setBackgroundResource(R.drawable.wrong_answer_background)
            }

            answerList[4] -> {
                binding.buttonOptionE.setBackgroundResource(R.drawable.wrong_answer_background)
            }
        }
    }

}