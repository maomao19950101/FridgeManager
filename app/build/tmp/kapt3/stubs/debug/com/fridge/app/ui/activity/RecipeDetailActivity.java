package com.fridge.app.ui.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.fridge.app.R;
import com.fridge.app.data.model.Difficulty;
import com.fridge.app.data.model.Recipe;
import com.fridge.app.data.model.RecipeIngredient;
import com.fridge.app.data.repository.IngredientRepository;
import com.fridge.app.data.repository.RecipeRepository;
import com.fridge.app.databinding.ActivityRecipeDetailBinding;
import com.google.android.material.chip.Chip;
import kotlinx.coroutines.Dispatchers;
import java.util.concurrent.TimeUnit;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000h\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u0000 02\u00020\u0001:\u00010B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u0015H\u0002J\u0010\u0010\u0016\u001a\u00020\u00052\u0006\u0010\u0014\u001a\u00020\u0015H\u0002J\b\u0010\u0017\u001a\u00020\u0018H\u0002J\b\u0010\u0019\u001a\u00020\u0018H\u0002J\u0012\u0010\u001a\u001a\u00020\u00182\b\u0010\u001b\u001a\u0004\u0018\u00010\u001cH\u0014J\u0010\u0010\u001d\u001a\u00020\u000b2\u0006\u0010\u001e\u001a\u00020\u001fH\u0016J\b\u0010 \u001a\u00020\u0018H\u0002J\u0016\u0010!\u001a\u00020\u00182\f\u0010\"\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004H\u0002J\u0016\u0010#\u001a\u00020\u00182\f\u0010$\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004H\u0002J\b\u0010%\u001a\u00020\u0018H\u0002J\b\u0010&\u001a\u00020\u0018H\u0002J\b\u0010\'\u001a\u00020\u0018H\u0002J\b\u0010(\u001a\u00020\u0018H\u0002J\u001e\u0010)\u001a\u00020\u00182\u0006\u0010*\u001a\u00020\u000b2\f\u0010+\u001a\b\u0012\u0004\u0012\u00020,0\u0004H\u0002J\b\u0010-\u001a\u00020\u0018H\u0002J\u0010\u0010.\u001a\u00020\u00182\u0006\u0010\f\u001a\u00020\rH\u0002J\u0010\u0010/\u001a\u00020\u00182\u0006\u0010\f\u001a\u00020\rH\u0002R\u0014\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\f\u001a\u0004\u0018\u00010\rX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u000fX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0011X\u0082.\u00a2\u0006\u0002\n\u0000\u00a8\u00061"}, d2 = {"Lcom/fridge/app/ui/activity/RecipeDetailActivity;", "Landroidx/appcompat/app/AppCompatActivity;", "()V", "availableIngredients", "", "", "binding", "Lcom/fridge/app/databinding/ActivityRecipeDetailBinding;", "ingredientRepository", "Lcom/fridge/app/data/repository/IngredientRepository;", "isFavorite", "", "recipe", "Lcom/fridge/app/data/model/Recipe;", "recipeId", "", "recipeRepository", "Lcom/fridge/app/data/repository/RecipeRepository;", "getDifficultyColor", "", "difficulty", "Lcom/fridge/app/data/model/Difficulty;", "getDifficultyString", "loadAvailableIngredients", "", "loadRecipe", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "onOptionsItemSelected", "item", "Landroid/view/MenuItem;", "setupListeners", "setupSteps", "steps", "setupTags", "tags", "setupToolbar", "shareRecipe", "startCookingMode", "toggleFavorite", "updateCanMakeStatus", "canMake", "missing", "Lcom/fridge/app/data/model/RecipeIngredient;", "updateFavoriteButton", "updateIngredientsUI", "updateUI", "Companion", "app_debug"})
public final class RecipeDetailActivity extends androidx.appcompat.app.AppCompatActivity {
    private com.fridge.app.databinding.ActivityRecipeDetailBinding binding;
    private com.fridge.app.data.repository.RecipeRepository recipeRepository;
    private com.fridge.app.data.repository.IngredientRepository ingredientRepository;
    private long recipeId = -1L;
    @org.jetbrains.annotations.Nullable()
    private com.fridge.app.data.model.Recipe recipe;
    @org.jetbrains.annotations.NotNull()
    private java.util.List<java.lang.String> availableIngredients;
    private boolean isFavorite = false;
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String EXTRA_RECIPE_ID = "recipe_id";
    @org.jetbrains.annotations.NotNull()
    public static final com.fridge.app.ui.activity.RecipeDetailActivity.Companion Companion = null;
    
    public RecipeDetailActivity() {
        super();
    }
    
    @java.lang.Override()
    protected void onCreate(@org.jetbrains.annotations.Nullable()
    android.os.Bundle savedInstanceState) {
    }
    
    private final void setupToolbar() {
    }
    
    private final void setupListeners() {
    }
    
    private final void loadRecipe() {
    }
    
    private final void loadAvailableIngredients() {
    }
    
    private final void updateUI(com.fridge.app.data.model.Recipe recipe) {
    }
    
    private final void updateIngredientsUI(com.fridge.app.data.model.Recipe recipe) {
    }
    
    private final void updateCanMakeStatus(boolean canMake, java.util.List<com.fridge.app.data.model.RecipeIngredient> missing) {
    }
    
    private final void setupTags(java.util.List<java.lang.String> tags) {
    }
    
    private final void setupSteps(java.util.List<java.lang.String> steps) {
    }
    
    private final void toggleFavorite() {
    }
    
    private final void updateFavoriteButton() {
    }
    
    private final void startCookingMode() {
    }
    
    private final void shareRecipe() {
    }
    
    private final java.lang.String getDifficultyString(com.fridge.app.data.model.Difficulty difficulty) {
        return null;
    }
    
    private final int getDifficultyColor(com.fridge.app.data.model.Difficulty difficulty) {
        return 0;
    }
    
    @java.lang.Override()
    public boolean onOptionsItemSelected(@org.jetbrains.annotations.NotNull()
    android.view.MenuItem item) {
        return false;
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0005"}, d2 = {"Lcom/fridge/app/ui/activity/RecipeDetailActivity$Companion;", "", "()V", "EXTRA_RECIPE_ID", "", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
    }
}