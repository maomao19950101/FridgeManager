package com.fridge.app.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.*;
import com.fridge.app.data.model.Difficulty;
import com.fridge.app.data.model.Recipe;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000D\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u000b\bg\u0018\u00002\u00020\u0001J\b\u0010\u0002\u001a\u00020\u0003H\'J\u0010\u0010\u0004\u001a\u00020\u00032\u0006\u0010\u0005\u001a\u00020\u0006H\'J\u0010\u0010\u0007\u001a\u00020\u00032\u0006\u0010\b\u001a\u00020\tH\'J\u0014\u0010\n\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00060\f0\u000bH\'J\u0012\u0010\r\u001a\u0004\u0018\u00010\u00062\u0006\u0010\b\u001a\u00020\tH\'J\b\u0010\u000e\u001a\u00020\u000fH\'J\u001c\u0010\u0010\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00060\f0\u000b2\u0006\u0010\u0011\u001a\u00020\u0012H\'J\u001c\u0010\u0013\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00060\f0\u000b2\u0006\u0010\u0014\u001a\u00020\u0015H\'J\u001c\u0010\u0016\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00060\f0\u000b2\u0006\u0010\u0017\u001a\u00020\u000fH\'J\u001c\u0010\u0018\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00060\f0\u000b2\u0006\u0010\u0019\u001a\u00020\u0012H\'J\u0010\u0010\u001a\u001a\u00020\t2\u0006\u0010\u0005\u001a\u00020\u0006H\'J\u0016\u0010\u001b\u001a\u00020\u00032\f\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u00060\fH\'J\u001c\u0010\u001d\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00060\f0\u000b2\u0006\u0010\u001e\u001a\u00020\u0012H\'J\u0010\u0010\u001f\u001a\u00020\u00032\u0006\u0010\u0005\u001a\u00020\u0006H\'\u00a8\u0006 "}, d2 = {"Lcom/fridge/app/data/dao/RecipeDao;", "", "deleteAllRecipes", "", "deleteRecipe", "recipe", "Lcom/fridge/app/data/model/Recipe;", "deleteRecipeById", "id", "", "getAllRecipes", "Landroidx/lifecycle/LiveData;", "", "getRecipeById", "getRecipeCount", "", "getRecipesByCuisine", "cuisineType", "", "getRecipesByDifficulty", "difficulty", "Lcom/fridge/app/data/model/Difficulty;", "getRecipesByMaxTime", "maxTime", "getRecipesByTag", "tag", "insertRecipe", "insertRecipes", "recipes", "searchRecipes", "query", "updateRecipe", "app_debug"})
@androidx.room.Dao()
public abstract interface RecipeDao {
    
    @androidx.room.Query(value = "SELECT * FROM recipes ORDER BY name ASC")
    @org.jetbrains.annotations.NotNull()
    public abstract androidx.lifecycle.LiveData<java.util.List<com.fridge.app.data.model.Recipe>> getAllRecipes();
    
    @androidx.room.Query(value = "SELECT * FROM recipes WHERE id = :id")
    @org.jetbrains.annotations.Nullable()
    public abstract com.fridge.app.data.model.Recipe getRecipeById(long id);
    
    @androidx.room.Query(value = "SELECT * FROM recipes WHERE difficulty = :difficulty ORDER BY name ASC")
    @org.jetbrains.annotations.NotNull()
    public abstract androidx.lifecycle.LiveData<java.util.List<com.fridge.app.data.model.Recipe>> getRecipesByDifficulty(@org.jetbrains.annotations.NotNull()
    com.fridge.app.data.model.Difficulty difficulty);
    
    @androidx.room.Query(value = "SELECT * FROM recipes WHERE cookingTime <= :maxTime ORDER BY cookingTime ASC")
    @org.jetbrains.annotations.NotNull()
    public abstract androidx.lifecycle.LiveData<java.util.List<com.fridge.app.data.model.Recipe>> getRecipesByMaxTime(int maxTime);
    
    @androidx.room.Query(value = "SELECT * FROM recipes WHERE cuisineType = :cuisineType ORDER BY name ASC")
    @org.jetbrains.annotations.NotNull()
    public abstract androidx.lifecycle.LiveData<java.util.List<com.fridge.app.data.model.Recipe>> getRecipesByCuisine(@org.jetbrains.annotations.NotNull()
    java.lang.String cuisineType);
    
    @androidx.room.Query(value = "SELECT * FROM recipes WHERE name LIKE \'%\' || :query || \'%\' OR description LIKE \'%\' || :query || \'%\' ORDER BY name ASC")
    @org.jetbrains.annotations.NotNull()
    public abstract androidx.lifecycle.LiveData<java.util.List<com.fridge.app.data.model.Recipe>> searchRecipes(@org.jetbrains.annotations.NotNull()
    java.lang.String query);
    
    @androidx.room.Query(value = "SELECT * FROM recipes WHERE :tag IN (tags) ORDER BY name ASC")
    @org.jetbrains.annotations.NotNull()
    public abstract androidx.lifecycle.LiveData<java.util.List<com.fridge.app.data.model.Recipe>> getRecipesByTag(@org.jetbrains.annotations.NotNull()
    java.lang.String tag);
    
    @androidx.room.Insert(onConflict = 1)
    public abstract long insertRecipe(@org.jetbrains.annotations.NotNull()
    com.fridge.app.data.model.Recipe recipe);
    
    @androidx.room.Insert(onConflict = 1)
    public abstract void insertRecipes(@org.jetbrains.annotations.NotNull()
    java.util.List<com.fridge.app.data.model.Recipe> recipes);
    
    @androidx.room.Update()
    public abstract void updateRecipe(@org.jetbrains.annotations.NotNull()
    com.fridge.app.data.model.Recipe recipe);
    
    @androidx.room.Delete()
    public abstract void deleteRecipe(@org.jetbrains.annotations.NotNull()
    com.fridge.app.data.model.Recipe recipe);
    
    @androidx.room.Query(value = "DELETE FROM recipes WHERE id = :id")
    public abstract void deleteRecipeById(long id);
    
    @androidx.room.Query(value = "DELETE FROM recipes")
    public abstract void deleteAllRecipes();
    
    @androidx.room.Query(value = "SELECT COUNT(*) FROM recipes")
    public abstract int getRecipeCount();
}