package com.example.food_app.recipe

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.food_app.R
import com.example.food_app.databinding.FragmentRecipeBinding
import kotlinx.coroutines.launch

class RecipeFragment : Fragment(R.layout.fragment_recipe) {

    private var _binding: FragmentRecipeBinding? = null
    private val binding get() = _binding!!

    private val recipeViewModel: RecipeViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        recipeViewModel.updateRecipe(arguments?.getInt("id")!!)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRecipeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val ingredientAdapter = IngredientAdapter()
        val recipeViewPagerAdapter = RecipeViewPagerAdapter()
        recipeViewModel.recipe.observe(viewLifecycleOwner){
            ingredientAdapter.apply {
            recipeViewModel.recipe.value?.extendedIngredients?.let { this.updateItems(it) } }
            recipeViewPagerAdapter.apply {
            recipeViewModel.recipe.value?.analyzedRecipe?.get(0).let {
                if (it != null) { this.updateItems(it.steps) } } }
        }
        binding.apply {
            instructionsViewPager.apply {
                adapter = recipeViewPagerAdapter
            }
            ingredientsRecyclerView.apply {
                adapter = ingredientAdapter
                layoutManager = LinearLayoutManager(requireContext())
            }
        }
    }
}