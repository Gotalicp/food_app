package com.example.food_app.history

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.food_app.R
import com.example.food_app.data.ExtendedRecipe
import com.example.food_app.data.ItemClickListener
import com.example.food_app.databinding.FragmentHistoryBinding

class HistoryFragment : Fragment() {

    private var _binding: FragmentHistoryBinding? = null
    private val binding get() = _binding!!

    private val historyViewModel: HistoryViewModel by activityViewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val historyAdapter = HistoryAdapter().apply {
            itemClickListener = object : ItemClickListener<ExtendedRecipe> {
                override fun onItemClicked(item: ExtendedRecipe, itemPosition: Int) {
                    findNavController().navigate(R.id.HistoryToRecipe, bundleOf("id" to item.id))
                }
            }
        }
        historyViewModel.cache.observe(viewLifecycleOwner){
            historyAdapter.updateItems(it!!)

        }
        _binding?.apply {
            resultsRecyclerView.apply {
                adapter = historyAdapter
                layoutManager = GridLayoutManager(requireContext(), 4)
            }
        } }
}