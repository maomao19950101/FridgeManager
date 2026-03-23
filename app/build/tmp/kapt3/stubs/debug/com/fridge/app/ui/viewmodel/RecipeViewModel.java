package com.fridge.app.ui.viewmodel;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.fridge.app.data.db.AppDatabase;
import com.fridge.app.data.model.Difficulty;
import com.fridge.app.data.model.Ingredient;
import com.fridge.app.data.model.Recipe;
import com.fridge.app.data.model.RecipeMatchResult;
import com.fridge.app.data.repository.RecipeRepository;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000l\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0006\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u000e\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\bJ\u0006\u0010\u001b\u001a\u00020\u0019J\u000e\u0010\u001c\u001a\u00020\u00192\u0006\u0010\u001d\u001a\u00020\u001eJ\u0014\u0010\u001f\u001a\u00020 2\f\u0010!\u001a\b\u0012\u0004\u0012\u00020\"0\u0007J\u0014\u0010\u0011\u001a\u00020 2\f\u0010!\u001a\b\u0012\u0004\u0012\u00020\"0\u0007J\u0016\u0010#\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\b0\r2\u0006\u0010\u001d\u001a\u00020\u001eJ\u001a\u0010$\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00070\r2\u0006\u0010%\u001a\u00020&J\u001a\u0010\'\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00070\r2\u0006\u0010(\u001a\u00020)J\u001a\u0010*\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00070\r2\u0006\u0010+\u001a\u00020,J\u0014\u0010-\u001a\u00020 2\f\u0010!\u001a\b\u0012\u0004\u0012\u00020\"0\u0007J\u000e\u0010.\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\bJ\u001a\u0010/\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00070\r2\u0006\u00100\u001a\u00020&J\u000e\u00101\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\bR\u001a\u0010\u0005\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00070\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010\t\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\n0\u00070\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010\u000b\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00070\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001d\u0010\f\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00070\r\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u001d\u0010\u0010\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00070\r\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u000fR\u001d\u0010\u0012\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\n0\u00070\r\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u000fR\u000e\u0010\u0014\u001a\u00020\u0015X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001d\u0010\u0016\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00070\r\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u000f\u00a8\u00062"}, d2 = {"Lcom/fridge/app/ui/viewmodel/RecipeViewModel;", "Landroidx/lifecycle/AndroidViewModel;", "application", "Landroid/app/Application;", "(Landroid/app/Application;)V", "_makeableRecipes", "Landroidx/lifecycle/MutableLiveData;", "", "Lcom/fridge/app/data/model/Recipe;", "_matchingRecipes", "Lcom/fridge/app/data/model/RecipeMatchResult;", "_searchResults", "allRecipes", "Landroidx/lifecycle/LiveData;", "getAllRecipes", "()Landroidx/lifecycle/LiveData;", "makeableRecipes", "getMakeableRecipes", "matchingRecipes", "getMatchingRecipes", "repository", "Lcom/fridge/app/data/repository/RecipeRepository;", "searchResults", "getSearchResults", "delete", "Lkotlinx/coroutines/Job;", "recipe", "deleteAll", "deleteById", "id", "", "findMatchingRecipes", "", "availableIngredients", "Lcom/fridge/app/data/model/Ingredient;", "getRecipeById", "getRecipesByCuisine", "cuisineType", "", "getRecipesByDifficulty", "difficulty", "Lcom/fridge/app/data/model/Difficulty;", "getRecipesByMaxTime", "maxTime", "", "getRecommendations", "insert", "searchRecipes", "query", "update", "app_debug"})
public final class RecipeViewModel extends androidx.lifecycle.AndroidViewModel {
    @org.jetbrains.annotations.NotNull()
    private final com.fridge.app.data.repository.RecipeRepository repository = null;
    @org.jetbrains.annotations.NotNull()
    private final androidx.lifecycle.LiveData<java.util.List<com.fridge.app.data.model.Recipe>> allRecipes = null;
    @org.jetbrains.annotations.NotNull()
    private final androidx.lifecycle.MutableLiveData<java.util.List<com.fridge.app.data.model.Recipe>> _searchResults = null;
    @org.jetbrains.annotations.NotNull()
    private final androidx.lifecycle.LiveData<java.util.List<com.fridge.app.data.model.Recipe>> searchResults = null;
    @org.jetbrains.annotations.NotNull()
    private final androidx.lifecycle.MutableLiveData<java.util.List<com.fridge.app.data.model.RecipeMatchResult>> _matchingRecipes = null;
    @org.jetbrains.annotations.NotNull()
    private final androidx.lifecycle.LiveData<java.util.List<com.fridge.app.data.model.RecipeMatchResult>> matchingRecipes = null;
    @org.jetbrains.annotations.NotNull()
    private final androidx.lifecycle.MutableLiveData<java.util.List<com.fridge.app.data.model.Recipe>> _makeableRecipes = null;
    @org.jetbrains.annotations.NotNull()
    private final androidx.lifecycle.LiveData<java.util.List<com.fridge.app.data.model.Recipe>> makeableRecipes = null;
    
    public RecipeViewModel(@org.jetbrains.annotations.NotNull()
    android.app.Application application) {
        super(null);
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.LiveData<java.util.List<com.fridge.app.data.model.Recipe>> getAllRecipes() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.LiveData<java.util.List<com.fridge.app.data.model.Recipe>> getSearchResults() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.LiveData<java.util.List<com.fridge.app.data.model.RecipeMatchResult>> getMatchingRecipes() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.LiveData<java.util.List<com.fridge.app.data.model.Recipe>> getMakeableRecipes() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.Job insert(@org.jetbrains.annotations.NotNull()
    com.fridge.app.data.model.Recipe recipe) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.Job update(@org.jetbrains.annotations.NotNull()
    com.fridge.app.data.model.Recipe recipe) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.Job delete(@org.jetbrains.annotations.NotNull()
    com.fridge.app.data.model.Recipe recipe) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.Job deleteById(long id) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.LiveData<com.fridge.app.data.model.Recipe> getRecipeById(long id) {
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
    
    public final void findMatchingRecipes(@org.jetbrains.annotations.NotNull()
    java.util.List<com.fridge.app.data.model.Ingredient> availableIngredients) {
    }
    
    public final void getMakeableRecipes(@org.jetbrains.annotations.NotNull()
    java.util.List<com.fridge.app.data.model.Ingredient> availableIngredients) {
    }
    
    public final void getRecommendations(@org.jetbrains.annotations.NotNull()
    java.util.List<com.fridge.app.data.model.Ingredient> availableIngredients) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.Job deleteAll() {
        return null;
    }
}