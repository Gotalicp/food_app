package com.example.food_app.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.food_app.FireBaseViewModel
import com.example.food_app.R
import com.example.food_app.databinding.FragmentRegisterBinding

class RegisterFragment : Fragment(R.layout.fragment_register) {

    private var _binding: FragmentRegisterBinding?=null
    private val binding get() = _binding!!

    private val fireBaseViewModel: FireBaseViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRegisterBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            navigationLogin.setOnClickListener {
                findNavController().navigate(R.id.RegisterToLogin)
            }
            btnSignIn.setOnClickListener {
                if (password.text.toString() != "" && repassword.text.toString() == password.text.toString() && email.text.toString() != "") {
                    fireBaseViewModel.createAcc(
                        email.text.toString(),
                        password.text.toString(),
                        view
                    )
                }
            }
        }
    }
}