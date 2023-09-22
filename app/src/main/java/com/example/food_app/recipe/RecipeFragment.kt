package com.example.food_app.recipe

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.food_app.FireBaseViewModel
import com.example.food_app.R
import com.example.food_app.data.ExtendedRecipe
import com.example.food_app.databinding.FragmentRecipeBinding
import com.example.recipe_app.getApiService
import kotlinx.coroutines.CoroutineScope

class RecipeFragment : Fragment(R.layout.fragment_recipe) {

    private var _binding: FragmentRecipeBinding? = null
    private val binding get() = _binding!!

    private val recipeViewModel: RecipeViewModel by activityViewModels()
    private val fireBaseViewModel: FireBaseViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRecipeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recipeViewModel.updateRecipe(arguments?.getInt("id")!!)

        val ingredientAdapter = IngredientAdapter()
        val recipeViewPagerAdapter = RecipeViewPagerAdapter()

        binding.apply {
        recipeViewModel.recipe.observe(viewLifecycleOwner){
            Log.d("firebase", "datachanged")
            Glide.with(recipeImage)
                .load(it.image)
                .centerCrop()
                .optionalCenterCrop()
                .into(binding.recipeImage)

            ingredientAdapter.apply {
                it.extendedIngredients?.let { this.updateItems(it) } }

            recipeViewPagerAdapter.apply {
                it.analyzedRecipe?.first().let {
                    if (it != null) { this.updateItems(it.steps) } } }

            if(recipeViewModel.checkInFav()){
                favoriteButton.setImageResource(R.drawable.addfav)
            }else{favoriteButton.setImageResource(R.drawable.emptyfav)}

            }
            instructionsViewPager.apply {
                adapter = recipeViewPagerAdapter
            }
            ingredientsRecyclerView.apply {
                adapter = ingredientAdapter
                layoutManager = LinearLayoutManager(requireContext())
            }
            likeButton.setOnClickListener{

            }
            favoriteButton.setOnClickListener{
                recipeViewModel.changeFavState()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        recipeViewModel.clearRecipe()
    }
}