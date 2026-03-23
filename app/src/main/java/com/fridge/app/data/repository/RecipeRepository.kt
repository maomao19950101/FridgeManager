package com.fridge.app.data.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.fridge.app.data.dao.RecipeDao
import com.fridge.app.data.db.AppDatabase
import com.fridge.app.data.model.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RecipeRepository(private val recipeDao: RecipeDao) {
    constructor(application: Application) : this(AppDatabase.getDatabase(application).recipeDao())

    val allRecipes: LiveData<List<Recipe>> = recipeDao.getAllRecipes()

    suspend fun getRecipeById(id: Long): Recipe? = withContext(Dispatchers.IO) {
        recipeDao.getRecipeById(id)
    }
    
    // 别名方法
    suspend fun getById(id: Long): Recipe? {
        return getRecipeById(id)
    }

    fun getRecipesByDifficulty(difficulty: Difficulty): LiveData<List<Recipe>> {
        return recipeDao.getRecipesByDifficulty(difficulty)
    }

    fun getRecipesByMaxTime(maxTime: Int): LiveData<List<Recipe>> {
        return recipeDao.getRecipesByMaxTime(maxTime)
    }

    fun getRecipesByCuisine(cuisineType: String): LiveData<List<Recipe>> {
        return recipeDao.getRecipesByCuisine(cuisineType)
    }

    fun searchRecipes(query: String): LiveData<List<Recipe>> {
        return recipeDao.searchRecipes(query)
    }

    fun getRecipesByTag(tag: String): LiveData<List<Recipe>> {
        return recipeDao.getRecipesByTag(tag)
    }

    suspend fun insertRecipe(recipe: Recipe): Long = withContext(Dispatchers.IO) {
        recipeDao.insertRecipe(recipe)
    }

    suspend fun updateRecipe(recipe: Recipe) = withContext(Dispatchers.IO) {
        recipeDao.updateRecipe(recipe)
    }

    suspend fun deleteRecipe(recipe: Recipe) = withContext(Dispatchers.IO) {
        recipeDao.deleteRecipe(recipe)
    }

    suspend fun deleteRecipeById(id: Long) = withContext(Dispatchers.IO) {
        recipeDao.deleteRecipeById(id)
    }

    suspend fun deleteAllRecipes() = withContext(Dispatchers.IO) {
        recipeDao.deleteAllRecipes()
    }

    suspend fun getRecipeCount(): Int = withContext(Dispatchers.IO) {
        recipeDao.getRecipeCount()
    }

    // 根据现有食材匹配可制作的菜谱
    fun findMatchingRecipes(availableIngredients: List<Ingredient>): List<RecipeMatchResult> {
        val recipes = allRecipes.value ?: return emptyList()
        val availableNames = availableIngredients.map { it.name }.toSet()

        return recipes.map { recipe ->
            val recipeIngredients = recipe.ingredients
            val matched = recipeIngredients.filter { ingredient ->
                availableNames.any { available ->
                    ingredient.name.contains(available) || available.contains(ingredient.name)
                }
            }
            val missing = recipeIngredients - matched.toSet()
            val matchRate = matched.size.toFloat() / recipeIngredients.size

            if (missing.isEmpty()) {
                RecipeMatchResult.canMake(recipe)
            } else {
                RecipeMatchResult.needMore(recipe, missing, matchRate)
            }
        }.sortedByDescending { it.matchRate }
    }

    // 获取可以完全制作的菜谱
    fun getMakeableRecipes(availableIngredients: List<Ingredient>): List<Recipe> {
        return findMatchingRecipes(availableIngredients)
            .filter { it.canMake }
            .map { it.recipe }
    }

    // 获取缺少食材较少的菜谱推荐
    fun getRecipeRecommendations(availableIngredients: List<Ingredient>, minMatchRate: Float = 0.5f): List<RecipeMatchResult> {
        return findMatchingRecipes(availableIngredients)
            .filter { !it.canMake && it.matchRate >= minMatchRate }
    }
}
