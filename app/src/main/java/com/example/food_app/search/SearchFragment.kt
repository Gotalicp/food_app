package com.example.food_app.search

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.food_app.R
import com.example.food_app.data.ExtendedRecipe
import com.example.food_app.data.ItemClickListener
import com.example.food_app.databinding.FragmentSearchBinding

class SearchFragment : Fragment(R.layout.fragment_search) {

    private var _binding: FragmentSearchBinding?=null
    private val binding get() = _binding!!

    private val searchViewModel: SearchViewModel by activityViewModels()

    private val handler = Handler(Looper.getMainLooper())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding= FragmentSearchBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            if (arguments != null) {
                searchViewModel.updateResults(arguments?.getString("item")!!, 0)
                text.text = "Results"
                searchView.setQuery(null, false)
                searchViewModel.changeState(View.GONE)
                searchView.clearFocus()
            }
            val resultAdapter = ResultAdapter().apply {
                itemClickListener = object : ItemClickListener<ExtendedRecipe> {
                    override fun onItemClicked(item: ExtendedRecipe, itemPosition: Int) {
                        findNavController().navigate(R.id.SearchToRecipe, bundleOf("id" to item.id))
                    }
                }
            }
            val predictionAdapter = PredictionAdapter().apply {
                itemClickListener = object : PredictionAdapter.ItemClickListener<String> {
                    override fun onItemClicked(item: String, itemPosition: Int) {
                        searchView.setQuery(item,true)
                    }
                }
            }
            predictionsView.apply {
                adapter = predictionAdapter
                layoutManager = LinearLayoutManager(requireContext())
            }
            resultsRecyclerView.apply {
                adapter = resultAdapter
                layoutManager = GridLayoutManager(requireContext(), 2)
            }
            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    if(query!=null) {
                        searchViewModel.updateResults(query, 0)
                        text.text="Results"
                        searchView.setQuery(null,false )
                        searchViewModel.changeState(View.GONE)
                        searchView.clearFocus()
                    }
                    return true
                }
                override fun onQueryTextChange(newText: String?): Boolean {
                    handler.removeCallbacksAndMessages(null)
                    if (!newText.isNullOrEmpty()) {
                        handler.postDelayed({
                            searchViewModel.changeState(View.VISIBLE)
                            searchViewModel.updatePrediction(newText)
                        }, 1000)
                    }else{
                        searchViewModel.changeState(View.GONE)
                    }
                    return true
                }
            })

            searchViewModel.prediction.observe(viewLifecycleOwner){
                predictionAdapter.updateItems(it)
            }
            searchViewModel.results.observe(viewLifecycleOwner){
                resultAdapter.updateItems(it)
            }
            searchViewModel.predictionState.observe(viewLifecycleOwner){
                predictionsView.visibility = it
                suggestionsText.visibility = it
            }

        }
    }
}