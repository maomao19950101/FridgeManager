package com.fridge.app.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.fridge.app.data.db.AppDatabase
import com.fridge.app.data.model.Difficulty
import com.fridge.app.data.model.Ingredient
import com.fridge.app.data.model.Recipe
import com.fridge.app.data.model.RecipeMatchResult
import com.fridge.app.data.repository.RecipeRepository
import kotlinx.coroutines.launch

class RecipeViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: RecipeRepository
    val allRecipes: LiveData<List<Recipe>>

    private val _searchResults = MutableLiveData<List<Recipe>>()
    val searchResults: LiveData<List<Recipe>> = _searchResults

    private val _matchingRecipes = MutableLiveData<List<RecipeMatchResult>>()
    val matchingRecipes: LiveData<List<RecipeMatchResult>> = _matchingRecipes

    private val _makeableRecipes = MutableLiveData<List<Recipe>>()
    val makeableRecipes: LiveData<List<Recipe>> = _makeableRecipes

    init {
        val recipeDao = AppDatabase.getDatabase(application).recipeDao()
        repository = RecipeRepository(recipeDao)
        allRecipes = repository.allRecipes
    }

    fun insert(recipe: Recipe) = viewModelScope.launch {
        repository.insertRecipe(recipe)
    }

    fun update(recipe: Recipe) = viewModelScope.launch {
        repository.updateRecipe(recipe)
    }

    fun delete(recipe: Recipe) = viewModelScope.launch {
        repository.deleteRecipe(recipe)
    }

    fun deleteById(id: Long) = viewModelScope.launch {
        repository.deleteRecipeById(id)
    }

    fun getRecipeById(id: Long): LiveData<Recipe?> {
        val result = MutableLiveData<Recipe?>()
        viewModelScope.launch {
            result.postValue(repository.getRecipeById(id))
        }
        return result
    }

    fun getRecipesByDifficulty(difficulty: Difficulty): LiveData<List<Recipe>> {
        return repository.getRecipesByDifficulty(difficulty)
    }

    fun getRecipesByMaxTime(maxTime: Int): LiveData<List<Recipe>> {
        return repository.getRecipesByMaxTime(maxTime)
    }

    fun getRecipesByCuisine(cuisineType: String): LiveData<List<Recipe>> {
        return repository.getRecipesByCuisine(cuisineType)
    }

    fun searchRecipes(query: String): LiveData<List<Recipe>> {
        return if (query.isBlank()) {
            allRecipes
        } else {
            repository.searchRecipes(query)
        }
    }

    // 根据现有食材查找可制作的菜谱
    fun findMatchingRecipes(availableIngredients: List<Ingredient>) {
        _matchingRecipes.value = repository.findMatchingRecipes(availableIngredients)
    }

    // 获取可以完全制作的菜谱
    fun getMakeableRecipes(availableIngredients: List<Ingredient>) {
        _makeableRecipes.value = repository.getMakeableRecipes(availableIngredients)
    }

    // 获取推荐菜谱（缺少较少食材的）
    fun getRecommendations(availableIngredients: List<Ingredient>) {
        viewModelScope.launch {
            _matchingRecipes.postValue(
                repository.getRecipeRecommendations(availableIngredients)
            )
        }
    }

    // 清空所有菜谱
    fun deleteAll() = viewModelScope.launch {
        repository.deleteAllRecipes()
    }
}