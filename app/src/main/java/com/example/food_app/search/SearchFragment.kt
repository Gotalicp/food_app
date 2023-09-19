package com.example.food_app.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.food_app.R
import com.example.food_app.SearchViewModel
import com.example.food_app.databinding.FragmentSearchBinding

class SearchFragment : Fragment(R.layout.fragment_search) {

    private var _binding: FragmentSearchBinding?=null
    private val binding get() = _binding!!

    private val searchViewModel: SearchViewModel by activityViewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding= FragmentSearchBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val resultAdapter = ResultAdapter()
        val predictionAdapter = PredictionAdapter()
        _binding?.apply {
            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {

                    return true
                }
                override fun onQueryTextChange(newText: String?): Boolean {
                    if (!newText.isNullOrEmpty()) {
                        searchViewModel.updatePrediction(newText)
                    }else{
                        predictionsView.visibility = View.GONE
                        suggestionsText.visibility = View.GONE
                    }
                    return true
                }
            })
            predictionsView.apply {
                adapter = predictionAdapter
                layoutManager = LinearLayoutManager(requireContext())
            }
            searchViewModel.prediction.observe(viewLifecycleOwner){
                _binding!!.predictionsView.visibility = View.VISIBLE
                _binding!!.suggestionsText.visibility = View.VISIBLE
                predictionAdapter.updateItems(it)
            }
        }
    }
}