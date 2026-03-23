package com.fridge.app.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.fridge.app.data.model.Difficulty
import com.fridge.app.data.model.Recipe

@Dao
interface RecipeDao {
    @Query("SELECT * FROM recipes ORDER BY name ASC")
    fun getAllRecipes(): LiveData<List<Recipe>>

    @Query("SELECT * FROM recipes WHERE id = :id")
    suspend fun getRecipeById(id: Long): Recipe?

    @Query("SELECT * FROM recipes WHERE difficulty = :difficulty ORDER BY name ASC")
    fun getRecipesByDifficulty(difficulty: Difficulty): LiveData<List<Recipe>>

    @Query("SELECT * FROM recipes WHERE cookingTime <= :maxTime ORDER BY cookingTime ASC")
    fun getRecipesByMaxTime(maxTime: Int): LiveData<List<Recipe>>

    @Query("SELECT * FROM recipes WHERE cuisineType = :cuisineType ORDER BY name ASC")
    fun getRecipesByCuisine(cuisineType: String): LiveData<List<Recipe>>

    @Query("SELECT * FROM recipes WHERE name LIKE '%' || :query || '%' OR description LIKE '%' || :query || '%' ORDER BY name ASC")
    fun searchRecipes(query: String): LiveData<List<Recipe>>

    @Query("SELECT * FROM recipes WHERE :tag IN (tags) ORDER BY name ASC")
    fun getRecipesByTag(tag: String): LiveData<List<Recipe>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecipe(recipe: Recipe): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecipes(recipes: List<Recipe>)

    @Update
    suspend fun updateRecipe(recipe: Recipe)

    @Delete
    suspend fun deleteRecipe(recipe: Recipe)

    @Query("DELETE FROM recipes WHERE id = :id")
    suspend fun deleteRecipeById(id: Long)

    @Query("DELETE FROM recipes")
    suspend fun deleteAllRecipes()

    @Query("SELECT COUNT(*) FROM recipes")
    suspend fun getRecipeCount(): Int
}