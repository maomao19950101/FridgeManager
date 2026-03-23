package com.fridge.app.data.repository;

import android.app.Application;
import androidx.lifecycle.LiveData;
import com.fridge.app.data.dao.IngredientDao;
import com.fridge.app.data.db.AppDatabase;
import com.fridge.app.data.model.*;
import kotlinx.coroutines.Dispatchers;
import java.util.Date;
import java.util.Calendar;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u008c\u0001\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0007\n\u0002\u0010\t\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\u000f\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004B\r\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u00a2\u0006\u0002\u0010\u0007J\u0016\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u000bH\u0086@\u00a2\u0006\u0002\u0010\u0011J\u000e\u0010\u0012\u001a\u00020\u000fH\u0086@\u00a2\u0006\u0002\u0010\u0013J\u0016\u0010\u0014\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u000bH\u0086@\u00a2\u0006\u0002\u0010\u0011J\u0016\u0010\u0015\u001a\u00020\u000f2\u0006\u0010\u0016\u001a\u00020\u0017H\u0086@\u00a2\u0006\u0002\u0010\u0018J\u0014\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u000b0\nH\u0086@\u00a2\u0006\u0002\u0010\u0013J\u0018\u0010\u001a\u001a\u0004\u0018\u00010\u000b2\u0006\u0010\u0016\u001a\u00020\u0017H\u0086@\u00a2\u0006\u0002\u0010\u0018J\u0014\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u001c0\nH\u0086@\u00a2\u0006\u0002\u0010\u0013J\u0012\u0010\u001d\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000b0\n0\tJ\u0016\u0010\u001e\u001a\u00020\u001f2\u0006\u0010 \u001a\u00020\u001fH\u0086@\u00a2\u0006\u0002\u0010!J\u001c\u0010\"\u001a\b\u0012\u0004\u0012\u00020\u000b0\n2\u0006\u0010 \u001a\u00020\u001fH\u0086@\u00a2\u0006\u0002\u0010!J\u000e\u0010#\u001a\u00020\u001fH\u0086@\u00a2\u0006\u0002\u0010\u0013J\u001e\u0010$\u001a\b\u0012\u0004\u0012\u00020%0\n2\b\b\u0002\u0010&\u001a\u00020\u001fH\u0086@\u00a2\u0006\u0002\u0010!J\u0018\u0010\'\u001a\u0004\u0018\u00010\u000b2\u0006\u0010\u0016\u001a\u00020\u0017H\u0086@\u00a2\u0006\u0002\u0010\u0018J\u000e\u0010(\u001a\u00020\u001fH\u0086@\u00a2\u0006\u0002\u0010\u0013J\u001a\u0010)\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000b0\n0\t2\u0006\u0010*\u001a\u00020+J\u0018\u0010,\u001a\u0014\u0012\u0004\u0012\u00020.\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000b0\n0-J\u001e\u0010/\u001a\b\u0012\u0004\u0012\u0002000\n2\b\b\u0002\u0010&\u001a\u00020\u001fH\u0086@\u00a2\u0006\u0002\u0010!J\u000e\u00101\u001a\u000202H\u0086@\u00a2\u0006\u0002\u0010\u0013J\u000e\u00103\u001a\u000204H\u0086@\u00a2\u0006\u0002\u0010\u0013J\u0014\u00105\u001a\b\u0012\u0004\u0012\u0002060\nH\u0086@\u00a2\u0006\u0002\u0010\u0013J\u0012\u00107\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000b0\n0\tJ\u000e\u00108\u001a\u000209H\u0086@\u00a2\u0006\u0002\u0010\u0013J\u0016\u0010:\u001a\u00020\u00172\u0006\u0010\u0010\u001a\u00020\u000bH\u0086@\u00a2\u0006\u0002\u0010\u0011J\u001a\u0010;\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000b0\n0\t2\u0006\u0010<\u001a\u00020=J\u0016\u0010>\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u000bH\u0086@\u00a2\u0006\u0002\u0010\u0011J\u0016\u0010?\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u000bH\u0086@\u00a2\u0006\u0002\u0010\u0011R\u001d\u0010\b\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000b0\n0\t\u00a2\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006@"}, d2 = {"Lcom/fridge/app/data/repository/IngredientRepository;", "", "application", "Landroid/app/Application;", "(Landroid/app/Application;)V", "ingredientDao", "Lcom/fridge/app/data/dao/IngredientDao;", "(Lcom/fridge/app/data/dao/IngredientDao;)V", "allIngredients", "Landroidx/lifecycle/LiveData;", "", "Lcom/fridge/app/data/model/Ingredient;", "getAllIngredients", "()Landroidx/lifecycle/LiveData;", "delete", "", "ingredient", "(Lcom/fridge/app/data/model/Ingredient;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "deleteAllIngredients", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "deleteIngredient", "deleteIngredientById", "id", "", "(JLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getAll", "getById", "getCategoryStatistics", "Lcom/fridge/app/data/model/CategoryStatistics;", "getExpiredIngredients", "getExpiringCount", "", "days", "(ILkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getExpiringIngredients", "getExpiringSoonCount", "getExpiryRateTrends", "Lcom/fridge/app/data/model/ExpiryRateTrend;", "months", "getIngredientById", "getIngredientCount", "getIngredientsByCategory", "category", "Lcom/fridge/app/data/model/IngredientCategory;", "getIngredientsByStatus", "", "Lcom/fridge/app/data/model/IngredientStatus;", "getMonthlyTrends", "Lcom/fridge/app/data/model/MonthlyTrend;", "getOverallStatistics", "Lcom/fridge/app/data/model/OverallStatistics;", "getStatisticsReport", "Lcom/fridge/app/data/model/StatisticsReport;", "getStatusStatistics", "Lcom/fridge/app/data/model/StatusStatistics;", "getValidIngredients", "hasExpiredIngredients", "", "insertIngredient", "searchIngredients", "query", "", "update", "updateIngredient", "app_debug"})
public final class IngredientRepository {
    @org.jetbrains.annotations.NotNull()
    private final com.fridge.app.data.dao.IngredientDao ingredientDao = null;
    @org.jetbrains.annotations.NotNull()
    private final androidx.lifecycle.LiveData<java.util.List<com.fridge.app.data.model.Ingredient>> allIngredients = null;
    
    public IngredientRepository(@org.jetbrains.annotations.NotNull()
    com.fridge.app.data.dao.IngredientDao ingredientDao) {
        super();
    }
    
    public IngredientRepository(@org.jetbrains.annotations.NotNull()
    android.app.Application application) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.LiveData<java.util.List<com.fridge.app.data.model.Ingredient>> getAllIngredients() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object getIngredientById(long id, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.fridge.app.data.model.Ingredient> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object getById(long id, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.fridge.app.data.model.Ingredient> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object getAll(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.fridge.app.data.model.Ingredient>> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.LiveData<java.util.List<com.fridge.app.data.model.Ingredient>> getIngredientsByCategory(@org.jetbrains.annotations.NotNull()
    com.fridge.app.data.model.IngredientCategory category) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.LiveData<java.util.List<com.fridge.app.data.model.Ingredient>> getValidIngredients() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.LiveData<java.util.List<com.fridge.app.data.model.Ingredient>> getExpiredIngredients() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object getExpiringIngredients(int days, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.fridge.app.data.model.Ingredient>> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.LiveData<java.util.List<com.fridge.app.data.model.Ingredient>> searchIngredients(@org.jetbrains.annotations.NotNull()
    java.lang.String query) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object insertIngredient(@org.jetbrains.annotations.NotNull()
    com.fridge.app.data.model.Ingredient ingredient, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Long> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object updateIngredient(@org.jetbrains.annotations.NotNull()
    com.fridge.app.data.model.Ingredient ingredient, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object update(@org.jetbrains.annotations.NotNull()
    com.fridge.app.data.model.Ingredient ingredient, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object deleteIngredient(@org.jetbrains.annotations.NotNull()
    com.fridge.app.data.model.Ingredient ingredient, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object delete(@org.jetbrains.annotations.NotNull()
    com.fridge.app.data.model.Ingredient ingredient, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object deleteIngredientById(long id, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object deleteAllIngredients(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object getIngredientCount(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Integer> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object getExpiringCount(int days, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Integer> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.Map<com.fridge.app.data.model.IngredientStatus, java.util.List<com.fridge.app.data.model.Ingredient>> getIngredientsByStatus() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object hasExpiredIngredients(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Boolean> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object getExpiringSoonCount(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Integer> $completion) {
        return null;
    }
    
    /**
     * 获取总体统计数据
     */
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object getOverallStatistics(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.fridge.app.data.model.OverallStatistics> $completion) {
        return null;
    }
    
    /**
     * 获取分类统计数据
     */
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object getCategoryStatistics(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.fridge.app.data.model.CategoryStatistics>> $completion) {
        return null;
    }
    
    /**
     * 获取状态统计数据
     */
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object getStatusStatistics(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.fridge.app.data.model.StatusStatistics>> $completion) {
        return null;
    }
    
    /**
     * 获取月度趋势数据（最近12个月）
     */
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object getMonthlyTrends(int months, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.fridge.app.data.model.MonthlyTrend>> $completion) {
        return null;
    }
    
    /**
     * 获取过期率趋势数据（最近12个月）
     */
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object getExpiryRateTrends(int months, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.fridge.app.data.model.ExpiryRateTrend>> $completion) {
        return null;
    }
    
    /**
     * 获取完整统计报表
     */
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object getStatisticsReport(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.fridge.app.data.model.StatisticsReport> $completion) {
        return null;
    }
}