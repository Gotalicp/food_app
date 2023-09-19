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
import com.example.food_app.databinding.FragmentLoginBinding

class LoginFragment : Fragment(R.layout.fragment_login) {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private val fireBaseViewModel: FireBaseViewModel by activityViewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            navigationRegister.setOnClickListener {
                findNavController().navigate(R.id.LoginToRegister)
            }
            btnLogin.setOnClickListener {
                if (password.text.toString() != "" && email.text.toString() != "") {
                    fireBaseViewModel.logIn(
                        email.text.toString(),
                        password.text.toString(),
                        view
                    )
                }
            }
        }
    }
}