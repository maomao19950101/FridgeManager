package com.fridge.app.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.fridge.app.R
import com.fridge.app.data.model.Recipe
import com.fridge.app.databinding.FragmentRecipeListBinding
import com.fridge.app.ui.adapter.RecipeAdapter
import com.fridge.app.ui.viewmodel.IngredientViewModel
import com.fridge.app.ui.viewmodel.RecipeViewModel

class RecipeListFragment : Fragment() {

    private var _binding: FragmentRecipeListBinding? = null
    private val binding get() = _binding!!

    private val recipeViewModel: RecipeViewModel by viewModels()
    private val ingredientViewModel: IngredientViewModel by viewModels()
    private lateinit var adapter: RecipeAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecipeListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        setupObservers()
        setupSearch()
        setupFilters()
    }

    private fun setupRecyclerView() {
        adapter = RecipeAdapter(
            onItemClick = { recipe ->
                showRecipeDetail(recipe)
            }
        )

        binding.recyclerViewRecipes.apply {
            layoutManager = GridLayoutManager(requireContext(), 2)
            adapter = this@RecipeListFragment.adapter
        }
    }

    private fun setupObservers() {
        recipeViewModel.allRecipes.observe(viewLifecycleOwner) { recipes ->
            ingredientViewModel.allIngredients.observe(viewLifecycleOwner) { ingredients ->
                recipeViewModel.findMatchingRecipes(ingredients)
            }
        }

        recipeViewModel.matchingRecipes.observe(viewLifecycleOwner) { matches ->
            adapter.submitList(matches)

            if (matches.isEmpty()) {
                binding.emptyViewRecipe.visibility = View.VISIBLE
                binding.recyclerViewRecipes.visibility = View.GONE
            } else {
                binding.emptyViewRecipe.visibility = View.GONE
                binding.recyclerViewRecipes.visibility = View.VISIBLE
            }
        }
    }

    private fun setupSearch() {
        binding.searchViewRecipe.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                val query = newText ?: ""
                recipeViewModel.searchRecipes(query).observe(viewLifecycleOwner) { results ->
                    adapter.submitRecipeList(results)
                }
                return true
            }
        })
    }

    private fun setupFilters() {
        binding.chipGroupFilterRecipe.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.chipAllRecipes -> {
                    recipeViewModel.allRecipes.observe(viewLifecycleOwner) {
                        ingredientViewModel.allIngredients.observe(viewLifecycleOwner) { ingredients ->
                            recipeViewModel.findMatchingRecipes(ingredients)
                        }
                    }
                }
                R.id.chipCanMake -> {
                    ingredientViewModel.allIngredients.observe(viewLifecycleOwner) { ingredients ->
                        recipeViewModel.getMakeableRecipes(ingredients)
                    }
                }
                R.id.chipEasy -> {
                    recipeViewModel.getRecipesByDifficulty(com.fridge.app.data.model.Difficulty.EASY)
                        .observe(viewLifecycleOwner) { recipes ->
                            adapter.submitRecipeList(recipes)
                        }
                }
                R.id.chipQuick -> {
                    recipeViewModel.getRecipesByMaxTime(20)
                        .observe(viewLifecycleOwner) { recipes ->
                            adapter.submitRecipeList(recipes)
                        }
                }
            }
        }
    }

    private fun showRecipeDetail(recipe: Recipe) {
        // 实现菜谱详情展示
        Toast.makeText(requireContext(), "查看菜谱: ${recipe.name}", Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}