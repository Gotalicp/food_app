package com.example.food_app.trivia

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.food_app.databinding.FragmentTriviaBinding

class TriviaFragment : Fragment() {

    private var _binding: FragmentTriviaBinding?=null
    private val binding get() = _binding!!

    private val triviaViewModel: TriviaViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTriviaBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            btnTrivia.setOnClickListener {
                triviaViewModel.getTrivia(findNavController())
            }
            btnJoke.setOnClickListener {
                triviaViewModel.getJoke(findNavController())
            }
        }
        triviaViewModel.text.observe(viewLifecycleOwner){
            triviaViewModel.updateScreen(binding)
        }
    }
}