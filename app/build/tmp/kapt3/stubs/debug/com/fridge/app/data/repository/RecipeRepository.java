package com.fridge.app.data.repository;

import android.app.Application;
import androidx.lifecycle.LiveData;
import com.fridge.app.data.dao.RecipeDao;
import com.fridge.app.data.db.AppDatabase;
import com.fridge.app.data.model.*;
import kotlinx.coroutines.Dispatchers;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000h\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0006\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\t\u0018\u00002\u00020\u0001B\u000f\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004B\r\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u00a2\u0006\u0002\u0010\u0007J\u000e\u0010\u000e\u001a\u00020\u000fH\u0086@\u00a2\u0006\u0002\u0010\u0010J\u0016\u0010\u0011\u001a\u00020\u000f2\u0006\u0010\u0012\u001a\u00020\u000bH\u0086@\u00a2\u0006\u0002\u0010\u0013J\u0016\u0010\u0014\u001a\u00020\u000f2\u0006\u0010\u0015\u001a\u00020\u0016H\u0086@\u00a2\u0006\u0002\u0010\u0017J\u001a\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00190\n2\f\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u001b0\nJ\u0018\u0010\u001c\u001a\u0004\u0018\u00010\u000b2\u0006\u0010\u0015\u001a\u00020\u0016H\u0086@\u00a2\u0006\u0002\u0010\u0017J\u001a\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\u000b0\n2\f\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u001b0\nJ\u0018\u0010\u001e\u001a\u0004\u0018\u00010\u000b2\u0006\u0010\u0015\u001a\u00020\u0016H\u0086@\u00a2\u0006\u0002\u0010\u0017J\u000e\u0010\u001f\u001a\u00020 H\u0086@\u00a2\u0006\u0002\u0010\u0010J$\u0010!\u001a\b\u0012\u0004\u0012\u00020\u00190\n2\f\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u001b0\n2\b\b\u0002\u0010\"\u001a\u00020#J\u001a\u0010$\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000b0\n0\t2\u0006\u0010%\u001a\u00020&J\u001a\u0010\'\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000b0\n0\t2\u0006\u0010(\u001a\u00020)J\u001a\u0010*\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000b0\n0\t2\u0006\u0010+\u001a\u00020 J\u001a\u0010,\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000b0\n0\t2\u0006\u0010-\u001a\u00020&J\u0016\u0010.\u001a\u00020\u00162\u0006\u0010\u0012\u001a\u00020\u000bH\u0086@\u00a2\u0006\u0002\u0010\u0013J\u001a\u0010/\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000b0\n0\t2\u0006\u00100\u001a\u00020&J\u0016\u00101\u001a\u00020\u000f2\u0006\u0010\u0012\u001a\u00020\u000bH\u0086@\u00a2\u0006\u0002\u0010\u0013R\u001d\u0010\b\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000b0\n0\t\u00a2\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u00062"}, d2 = {"Lcom/fridge/app/data/repository/RecipeRepository;", "", "application", "Landroid/app/Application;", "(Landroid/app/Application;)V", "recipeDao", "Lcom/fridge/app/data/dao/RecipeDao;", "(Lcom/fridge/app/data/dao/RecipeDao;)V", "allRecipes", "Landroidx/lifecycle/LiveData;", "", "Lcom/fridge/app/data/model/Recipe;", "getAllRecipes", "()Landroidx/lifecycle/LiveData;", "deleteAllRecipes", "", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "deleteRecipe", "recipe", "(Lcom/fridge/app/data/model/Recipe;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "deleteRecipeById", "id", "", "(JLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "findMatchingRecipes", "Lcom/fridge/app/data/model/RecipeMatchResult;", "availableIngredients", "Lcom/fridge/app/data/model/Ingredient;", "getById", "getMakeableRecipes", "getRecipeById", "getRecipeCount", "", "getRecipeRecommendations", "minMatchRate", "", "getRecipesByCuisine", "cuisineType", "", "getRecipesByDifficulty", "difficulty", "Lcom/fridge/app/data/model/Difficulty;", "getRecipesByMaxTime", "maxTime", "getRecipesByTag", "tag", "insertRecipe", "searchRecipes", "query", "updateRecipe", "app_debug"})
public final class RecipeRepository {
    @org.jetbrains.annotations.NotNull()
    private final com.fridge.app.data.dao.RecipeDao recipeDao = null;
    @org.jetbrains.annotations.NotNull()
    private final androidx.lifecycle.LiveData<java.util.List<com.fridge.app.data.model.Recipe>> allRecipes = null;
    
    public RecipeRepository(@org.jetbrains.annotations.NotNull()
    com.fridge.app.data.dao.RecipeDao recipeDao) {
        super();
    }
    
    public RecipeRepository(@org.jetbrains.annotations.NotNull()
    android.app.Application application) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.LiveData<java.util.List<com.fridge.app.data.model.Recipe>> getAllRecipes() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object getRecipeById(long id, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.fridge.app.data.model.Recipe> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object getById(long id, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.fridge.app.data.model.Recipe> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.LiveData<java.util.List<com.fridge.app.data.model.Recipe>> getRecipesByDifficulty(@org.jetbrains.annotations.NotNull()
    com.fridge.app.data.model.Difficulty difficulty) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.LiveData<java.util.List<com.fridge.app.data.model.Recipe>> getRecipesByMaxTime(int maxTime) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.LiveData<java.util.List<com.fridge.app.data.model.Recipe>> getRecipesByCuisine(@org.jetbrains.annotations.NotNull()
    java.lang.String cuisineType) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.LiveData<java.util.List<com.fridge.app.data.model.Recipe>> searchRecipes(@org.jetbrains.annotations.NotNull()
    java.lang.String query) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.LiveData<java.util.List<com.fridge.app.data.model.Recipe>> getRecipesByTag(@org.jetbrains.annotations.NotNull()
    java.lang.String tag) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object insertRecipe(@org.jetbrains.annotations.NotNull()
    com.fridge.app.data.model.Recipe recipe, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Long> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object updateRecipe(@org.jetbrains.annotations.NotNull()
    com.fridge.app.data.model.Recipe recipe, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object deleteRecipe(@org.jetbrains.annotations.NotNull()
    com.fridge.app.data.model.Recipe recipe, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object deleteRecipeById(long id, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object deleteAllRecipes(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object getRecipeCount(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Integer> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.fridge.app.data.model.RecipeMatchResult> findMatchingRecipes(@org.jetbrains.annotations.NotNull()
    java.util.List<com.fridge.app.data.model.Ingredient> availableIngredients) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.fridge.app.data.model.Recipe> getMakeableRecipes(@org.jetbrains.annotations.NotNull()
    java.util.List<com.fridge.app.data.model.Ingredient> availableIngredients) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.fridge.app.data.model.RecipeMatchResult> getRecipeRecommendations(@org.jetbrains.annotations.NotNull()
    java.util.List<com.fridge.app.data.model.Ingredient> availableIngredients, float minMatchRate) {
        return null;
    }
}