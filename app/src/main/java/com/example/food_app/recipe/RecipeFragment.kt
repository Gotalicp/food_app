package com.example.food_app.recipe

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.util.Xml
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.food_app.R
import com.example.food_app.databinding.FragmentRecipeBinding
import com.example.food_app.history.HistoryViewModel
import org.xmlpull.v1.XmlSerializer
import java.io.StringWriter

class RecipeFragment : Fragment(R.layout.fragment_recipe) {

    private var _binding: FragmentRecipeBinding? = null
    private val binding get() = _binding!!

    private val recipeViewModel: RecipeViewModel by activityViewModels()
    private val historyViewModel: HistoryViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRecipeBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recipeViewModel.updateRecipe(arguments?.getInt("id")!!)
        recipeViewModel.updateTasteWidget(id,binding)
        val ingredientAdapter = IngredientAdapter()
        val recipeViewPagerAdapter = RecipeViewPagerAdapter()
        binding.apply {
        recipeViewModel.recipe.observe(viewLifecycleOwner){ recipe ->
            historyViewModel.addToHistory(recipe)
            Glide.with(recipeImage)
                .load(recipe.image)
                .centerCrop()
                .optionalCenterCrop()
                .into(binding.recipeImage)
            name.text = recipe.title

            info.text = "Servings: ${recipe.servings}\nReadyInMinutes: ${recipe.readyInMinutes}\nLikes: ${recipe.likes}\n"+ recipe.summary?.let { createXMLWithCDATA(it)}
            ingredientAdapter.apply {
                recipe.extendedIngredients?.let { this.updateItems(it) } }

            recipeViewPagerAdapter.apply {
                recipe.analyzedRecipe?.first().let {
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
    fun createXMLWithCDATA(cdataContent: String): String {
        val writer = StringWriter()
        val xmlSerializer = Xml.newSerializer()
        xmlSerializer.setOutput(writer)

        // Start the XML document
        xmlSerializer.startDocument("UTF-8", true)

        // Start the root element
        xmlSerializer.startTag("", "root")

        xmlSerializer.text("<![CDATA[$cdataContent]]>")

        xmlSerializer.endTag("", "root")

        xmlSerializer.endDocument()

        return writer.toString()
    }

}