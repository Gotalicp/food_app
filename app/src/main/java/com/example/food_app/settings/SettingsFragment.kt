package com.example.food_app.settings

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.food_app.FireBaseViewModel
import com.example.food_app.R
import com.example.food_app.databinding.FragmentSettingsBinding

class SettingsFragment : Fragment(R.layout.fragment_settings) {

    private var _binding: FragmentSettingsBinding?=null
    private val binding get() = _binding!!

    private val fireBaseViewModel: FireBaseViewModel by activityViewModels()
    private val settingViewModel: SettingViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSettingsBinding.inflate(inflater,container,false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            logoutButton.setOnClickListener {
                fireBaseViewModel.logout()
                settingViewModel.logout()
            }
            policyButton.setOnClickListener{
                findNavController().navigate(R.id.SettingToPolicy)
            }
            userButton.setOnClickListener{

            }
            themesButton.setOnClickListener {
            }
        }
    }
}