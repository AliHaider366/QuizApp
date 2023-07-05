package com.example.quizapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.quizapp.R
import com.example.quizapp.databinding.FragmentMainMenuBinding
import com.example.quizapp.shared.SharedPreference

class MainMenuFragment : Fragment() {

    private val binding by lazy{
        FragmentMainMenuBinding.inflate(layoutInflater)
    }

    private lateinit var sharedPreference : SharedPreference


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        sharedPreference = SharedPreference(requireContext())

        var highScore = sharedPreference.getStringFromSharedPreferences("score")

        if(highScore != null){
            binding.textViewHighScore.text = "High Score : $highScore"
        }

        binding.buttonStartGame.setOnClickListener {
            findNavController().navigate(R.id.questionFragment)
        }

        return binding.root
    }

}