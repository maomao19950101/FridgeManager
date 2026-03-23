package com.fridge.app.data.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;
import com.fridge.app.data.local.Converters;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0011\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u0086\b\u0018\u0000 \u001f2\u00020\u0001:\u0001\u001fB-\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\b\b\u0002\u0010\t\u001a\u00020\n\u00a2\u0006\u0002\u0010\u000bJ\t\u0010\u0014\u001a\u00020\u0003H\u00c6\u0003J\u000f\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005H\u00c6\u0003J\t\u0010\u0016\u001a\u00020\bH\u00c6\u0003J\t\u0010\u0017\u001a\u00020\nH\u00c6\u0003J7\u0010\u0018\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\u000e\b\u0002\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u00052\b\b\u0002\u0010\u0007\u001a\u00020\b2\b\b\u0002\u0010\t\u001a\u00020\nH\u00c6\u0001J\u0013\u0010\u0019\u001a\u00020\n2\b\u0010\u001a\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010\u001b\u001a\u00020\u001cH\u00d6\u0001J\t\u0010\u001d\u001a\u00020\u001eH\u00d6\u0001R\u0011\u0010\t\u001a\u00020\n\u00a2\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0011\u0010\u0007\u001a\u00020\b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0017\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013\u00a8\u0006 "}, d2 = {"Lcom/fridge/app/data/model/RecipeMatchResult;", "", "recipe", "Lcom/fridge/app/data/model/Recipe;", "missingIngredients", "", "Lcom/fridge/app/data/model/RecipeIngredient;", "matchRate", "", "canMake", "", "(Lcom/fridge/app/data/model/Recipe;Ljava/util/List;FZ)V", "getCanMake", "()Z", "getMatchRate", "()F", "getMissingIngredients", "()Ljava/util/List;", "getRecipe", "()Lcom/fridge/app/data/model/Recipe;", "component1", "component2", "component3", "component4", "copy", "equals", "other", "hashCode", "", "toString", "", "Companion", "app_debug"})
public final class RecipeMatchResult {
    @org.jetbrains.annotations.NotNull()
    private final com.fridge.app.data.model.Recipe recipe = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<com.fridge.app.data.model.RecipeIngredient> missingIngredients = null;
    private final float matchRate = 0.0F;
    private final boolean canMake = false;
    @org.jetbrains.annotations.NotNull()
    public static final com.fridge.app.data.model.RecipeMatchResult.Companion Companion = null;
    
    public RecipeMatchResult(@org.jetbrains.annotations.NotNull()
    com.fridge.app.data.model.Recipe recipe, @org.jetbrains.annotations.NotNull()
    java.util.List<com.fridge.app.data.model.RecipeIngredient> missingIngredients, float matchRate, boolean canMake) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.fridge.app.data.model.Recipe getRecipe() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.fridge.app.data.model.RecipeIngredient> getMissingIngredients() {
        return null;
    }
    
    public final float getMatchRate() {
        return 0.0F;
    }
    
    public final boolean getCanMake() {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.fridge.app.data.model.Recipe component1() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.fridge.app.data.model.RecipeIngredient> component2() {
        return null;
    }
    
    public final float component3() {
        return 0.0F;
    }
    
    public final boolean component4() {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.fridge.app.data.model.RecipeMatchResult copy(@org.jetbrains.annotations.NotNull()
    com.fridge.app.data.model.Recipe recipe, @org.jetbrains.annotations.NotNull()
    java.util.List<com.fridge.app.data.model.RecipeIngredient> missingIngredients, float matchRate, boolean canMake) {
        return null;
    }
    
    @java.lang.Override()
    public boolean equals(@org.jetbrains.annotations.Nullable()
    java.lang.Object other) {
        return false;
    }
    
    @java.lang.Override()
    public int hashCode() {
        return 0;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public java.lang.String toString() {
        return null;
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006J$\u0010\u0007\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\t2\u0006\u0010\u000b\u001a\u00020\f\u00a8\u0006\r"}, d2 = {"Lcom/fridge/app/data/model/RecipeMatchResult$Companion;", "", "()V", "canMake", "Lcom/fridge/app/data/model/RecipeMatchResult;", "recipe", "Lcom/fridge/app/data/model/Recipe;", "needMore", "missing", "", "Lcom/fridge/app/data/model/RecipeIngredient;", "rate", "", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.fridge.app.data.model.RecipeMatchResult canMake(@org.jetbrains.annotations.NotNull()
        com.fridge.app.data.model.Recipe recipe) {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.fridge.app.data.model.RecipeMatchResult needMore(@org.jetbrains.annotations.NotNull()
        com.fridge.app.data.model.Recipe recipe, @org.jetbrains.annotations.NotNull()
        java.util.List<com.fridge.app.data.model.RecipeIngredient> missing, float rate) {
            return null;
        }
    }
}