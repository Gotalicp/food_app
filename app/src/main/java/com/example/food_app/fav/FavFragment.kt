package com.example.food_app.fav

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.food_app.FireBaseViewModel
import com.example.food_app.R
import com.example.food_app.data.ExtendedRecipe
import com.example.food_app.data.ItemClickListener
import com.example.food_app.databinding.FragmentFavBinding
import com.example.food_app.search.FavAdapter

class FavFragment : Fragment(R.layout.fragment_fav) {

    private var _binding: FragmentFavBinding? = null
    private val binding get() = _binding!!

    private val favViewModel: FavViewModel by activityViewModels()
    private var fireBaseViewModel = FireBaseViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        favViewModel.getFav()
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFavBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val favAdapter = FavAdapter().apply {
            itemClickListener = object : ItemClickListener<ExtendedRecipe> {
                override fun onItemClicked(item: ExtendedRecipe, itemPosition: Int) {
                    findNavController().navigate(R.id.FavToRecipe, bundleOf("id" to item.id))
                }
            }
        }
        _binding?.apply {
            resultsRecyclerView.apply {
                adapter = favAdapter
                layoutManager = GridLayoutManager(requireContext(), 2)
            }
        }
        favViewModel.results.observe(viewLifecycleOwner){
            favAdapter.updateItems(it)
        }
    }
}