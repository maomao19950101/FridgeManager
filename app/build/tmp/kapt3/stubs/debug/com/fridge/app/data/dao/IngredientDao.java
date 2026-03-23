package com.fridge.app.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.*;
import com.fridge.app.data.model.Ingredient;
import com.fridge.app.data.model.IngredientCategory;
import com.fridge.app.data.model.IngredientStatus;
import java.util.Date;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000V\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u000f\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u000e\n\u0002\b\u0002\bg\u0018\u00002\u00020\u0001J\b\u0010\u0002\u001a\u00020\u0003H\'J\u0010\u0010\u0004\u001a\u00020\u00032\u0006\u0010\u0005\u001a\u00020\u0006H\'J\u0010\u0010\u0007\u001a\u00020\u00032\u0006\u0010\b\u001a\u00020\tH\'J\u0014\u0010\n\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00060\f0\u000bH\'J\u000e\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u00060\fH\'J\u000e\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00060\fH\'J\u000e\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00100\fH\'J\u0010\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0014H\'J\n\u0010\u0015\u001a\u0004\u0018\u00010\u0016H\'J\u0012\u0010\u0017\u001a\u00020\u00122\b\b\u0002\u0010\u0018\u001a\u00020\u0016H\'J\u001e\u0010\u0019\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00060\f0\u000b2\b\b\u0002\u0010\u0018\u001a\u00020\u0016H\'J\u0010\u0010\u001a\u001a\u00020\u00122\u0006\u0010\u001b\u001a\u00020\u0016H\'J\u0018\u0010\u001c\u001a\u00020\u00122\u0006\u0010\u001d\u001a\u00020\u00162\u0006\u0010\u001e\u001a\u00020\u0016H\'J\u0016\u0010\u001f\u001a\b\u0012\u0004\u0012\u00020\u00060\f2\u0006\u0010\u001b\u001a\u00020\u0016H\'J\u0012\u0010 \u001a\u0004\u0018\u00010\u00062\u0006\u0010\b\u001a\u00020\tH\'J\b\u0010!\u001a\u00020\u0012H\'J\u001e\u0010\"\u001a\b\u0012\u0004\u0012\u00020\u00060\f2\u0006\u0010\u001d\u001a\u00020\u00162\u0006\u0010\u001e\u001a\u00020\u0016H\'J\u001c\u0010#\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00060\f0\u000b2\u0006\u0010\u0013\u001a\u00020\u0014H\'J\u001c\u0010$\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00060\f0\u000b2\u0006\u0010\u001b\u001a\u00020\u0016H\'J\u0016\u0010%\u001a\b\u0012\u0004\u0012\u00020&0\f2\u0006\u0010\u001d\u001a\u00020\u0016H\'J \u0010\'\u001a\b\u0012\u0004\u0012\u00020&0\f2\u0006\u0010\u001d\u001a\u00020\u00162\b\b\u0002\u0010\u0018\u001a\u00020\u0016H\'J\u001e\u0010(\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00060\f0\u000b2\b\b\u0002\u0010\u0018\u001a\u00020\u0016H\'J\u0010\u0010)\u001a\u00020\t2\u0006\u0010\u0005\u001a\u00020\u0006H\'J\u0016\u0010*\u001a\u00020\u00032\f\u0010+\u001a\b\u0012\u0004\u0012\u00020\u00060\fH\'J\u001c\u0010,\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00060\f0\u000b2\u0006\u0010-\u001a\u00020.H\'J\u0010\u0010/\u001a\u00020\u00032\u0006\u0010\u0005\u001a\u00020\u0006H\'\u00a8\u00060"}, d2 = {"Lcom/fridge/app/data/dao/IngredientDao;", "", "deleteAllIngredients", "", "deleteIngredient", "ingredient", "Lcom/fridge/app/data/model/Ingredient;", "deleteIngredientById", "id", "", "getAllIngredients", "Landroidx/lifecycle/LiveData;", "", "getAllIngredientsByCategory", "getAllIngredientsSync", "getCategoryCounts", "Lcom/fridge/app/data/dao/CategoryCount;", "getCountByCategory", "", "category", "Lcom/fridge/app/data/model/IngredientCategory;", "getEarliestAddDate", "Ljava/util/Date;", "getExpiredCount", "currentDate", "getExpiredIngredients", "getExpiringCount", "date", "getExpiringCountBetween", "startDate", "endDate", "getExpiringIngredients", "getIngredientById", "getIngredientCount", "getIngredientsAddedBetween", "getIngredientsByCategory", "getIngredientsExpiringBefore", "getMonthlyAddedCounts", "Lcom/fridge/app/data/dao/MonthlyCount;", "getMonthlyExpiredCounts", "getValidIngredients", "insertIngredient", "insertIngredients", "ingredients", "searchIngredients", "query", "", "updateIngredient", "app_debug"})
@androidx.room.Dao()
public abstract interface IngredientDao {
    
    @androidx.room.Query(value = "SELECT * FROM ingredients ORDER BY expireDate ASC")
    @org.jetbrains.annotations.NotNull()
    public abstract androidx.lifecycle.LiveData<java.util.List<com.fridge.app.data.model.Ingredient>> getAllIngredients();
    
    /**
     * 按分类统计食材数量
     */
    @androidx.room.Query(value = "SELECT category, COUNT(*) as count FROM ingredients GROUP BY category")
    @org.jetbrains.annotations.NotNull()
    public abstract java.util.List<com.fridge.app.data.dao.CategoryCount> getCategoryCounts();
    
    /**
     * 获取指定分类的食材数量
     */
    @androidx.room.Query(value = "SELECT COUNT(*) FROM ingredients WHERE category = :category")
    public abstract int getCountByCategory(@org.jetbrains.annotations.NotNull()
    com.fridge.app.data.model.IngredientCategory category);
    
    /**
     * 按状态统计食材数量（需要结合应用逻辑计算）
     * 获取所有食材用于状态统计
     */
    @androidx.room.Query(value = "SELECT * FROM ingredients")
    @org.jetbrains.annotations.NotNull()
    public abstract java.util.List<com.fridge.app.data.model.Ingredient> getAllIngredientsSync();
    
    /**
     * 获取已过期食材数量
     */
    @androidx.room.Query(value = "SELECT COUNT(*) FROM ingredients WHERE expireDate IS NOT NULL AND expireDate < :currentDate")
    public abstract int getExpiredCount(@org.jetbrains.annotations.NotNull()
    java.util.Date currentDate);
    
    /**
     * 获取即将过期食材数量（指定天数内）
     */
    @androidx.room.Query(value = "SELECT COUNT(*) FROM ingredients WHERE expireDate IS NOT NULL AND expireDate >= :startDate AND expireDate <= :endDate")
    public abstract int getExpiringCountBetween(@org.jetbrains.annotations.NotNull()
    java.util.Date startDate, @org.jetbrains.annotations.NotNull()
    java.util.Date endDate);
    
    /**
     * 按月份统计新增食材数量
     */
    @androidx.room.Query(value = "\n        SELECT \n            strftime(\'%Y-%m\', addDate / 1000, \'unixepoch\', \'localtime\') as month,\n            COUNT(*) as count\n        FROM ingredients\n        WHERE addDate >= :startDate\n        GROUP BY month\n        ORDER BY month ASC\n    ")
    @org.jetbrains.annotations.NotNull()
    public abstract java.util.List<com.fridge.app.data.dao.MonthlyCount> getMonthlyAddedCounts(@org.jetbrains.annotations.NotNull()
    java.util.Date startDate);
    
    /**
     * 按月份统计过期食材数量
     */
    @androidx.room.Query(value = "\n        SELECT \n            strftime(\'%Y-%m\', expireDate / 1000, \'unixepoch\', \'localtime\') as month,\n            COUNT(*) as count\n        FROM ingredients\n        WHERE expireDate IS NOT NULL AND expireDate < :currentDate AND expireDate >= :startDate\n        GROUP BY month\n        ORDER BY month ASC\n    ")
    @org.jetbrains.annotations.NotNull()
    public abstract java.util.List<com.fridge.app.data.dao.MonthlyCount> getMonthlyExpiredCounts(@org.jetbrains.annotations.NotNull()
    java.util.Date startDate, @org.jetbrains.annotations.NotNull()
    java.util.Date currentDate);
    
    /**
     * 获取最早的添加日期
     */
    @androidx.room.Query(value = "SELECT MIN(addDate) FROM ingredients")
    @org.jetbrains.annotations.Nullable()
    public abstract java.util.Date getEarliestAddDate();
    
    /**
     * 获取指定日期范围内添加的食材
     */
    @androidx.room.Query(value = "SELECT * FROM ingredients WHERE addDate >= :startDate AND addDate <= :endDate ORDER BY addDate ASC")
    @org.jetbrains.annotations.NotNull()
    public abstract java.util.List<com.fridge.app.data.model.Ingredient> getIngredientsAddedBetween(@org.jetbrains.annotations.NotNull()
    java.util.Date startDate, @org.jetbrains.annotations.NotNull()
    java.util.Date endDate);
    
    /**
     * 获取所有分类的食材（同步）
     */
    @androidx.room.Query(value = "SELECT * FROM ingredients ORDER BY category ASC")
    @org.jetbrains.annotations.NotNull()
    public abstract java.util.List<com.fridge.app.data.model.Ingredient> getAllIngredientsByCategory();
    
    @androidx.room.Query(value = "SELECT * FROM ingredients WHERE id = :id")
    @org.jetbrains.annotations.Nullable()
    public abstract com.fridge.app.data.model.Ingredient getIngredientById(long id);
    
    @androidx.room.Query(value = "SELECT * FROM ingredients WHERE category = :category ORDER BY expireDate ASC")
    @org.jetbrains.annotations.NotNull()
    public abstract androidx.lifecycle.LiveData<java.util.List<com.fridge.app.data.model.Ingredient>> getIngredientsByCategory(@org.jetbrains.annotations.NotNull()
    com.fridge.app.data.model.IngredientCategory category);
    
    @androidx.room.Query(value = "SELECT * FROM ingredients WHERE expireDate <= :date ORDER BY expireDate ASC")
    @org.jetbrains.annotations.NotNull()
    public abstract androidx.lifecycle.LiveData<java.util.List<com.fridge.app.data.model.Ingredient>> getIngredientsExpiringBefore(@org.jetbrains.annotations.NotNull()
    java.util.Date date);
    
    @androidx.room.Query(value = "SELECT * FROM ingredients WHERE expireDate IS NULL OR expireDate > :currentDate ORDER BY name ASC")
    @org.jetbrains.annotations.NotNull()
    public abstract androidx.lifecycle.LiveData<java.util.List<com.fridge.app.data.model.Ingredient>> getValidIngredients(@org.jetbrains.annotations.NotNull()
    java.util.Date currentDate);
    
    @androidx.room.Query(value = "SELECT * FROM ingredients WHERE expireDate IS NOT NULL AND expireDate <= :date ORDER BY expireDate ASC")
    @org.jetbrains.annotations.NotNull()
    public abstract java.util.List<com.fridge.app.data.model.Ingredient> getExpiringIngredients(@org.jetbrains.annotations.NotNull()
    java.util.Date date);
    
    @androidx.room.Query(value = "SELECT * FROM ingredients WHERE name LIKE \'%\' || :query || \'%\' ORDER BY name ASC")
    @org.jetbrains.annotations.NotNull()
    public abstract androidx.lifecycle.LiveData<java.util.List<com.fridge.app.data.model.Ingredient>> searchIngredients(@org.jetbrains.annotations.NotNull()
    java.lang.String query);
    
    @androidx.room.Insert(onConflict = 1)
    public abstract long insertIngredient(@org.jetbrains.annotations.NotNull()
    com.fridge.app.data.model.Ingredient ingredient);
    
    @androidx.room.Insert(onConflict = 1)
    public abstract void insertIngredients(@org.jetbrains.annotations.NotNull()
    java.util.List<com.fridge.app.data.model.Ingredient> ingredients);
    
    @androidx.room.Update()
    public abstract void updateIngredient(@org.jetbrains.annotations.NotNull()
    com.fridge.app.data.model.Ingredient ingredient);
    
    @androidx.room.Delete()
    public abstract void deleteIngredient(@org.jetbrains.annotations.NotNull()
    com.fridge.app.data.model.Ingredient ingredient);
    
    @androidx.room.Query(value = "DELETE FROM ingredients WHERE id = :id")
    public abstract void deleteIngredientById(long id);
    
    @androidx.room.Query(value = "DELETE FROM ingredients")
    public abstract void deleteAllIngredients();
    
    @androidx.room.Query(value = "SELECT COUNT(*) FROM ingredients")
    public abstract int getIngredientCount();
    
    @androidx.room.Query(value = "SELECT COUNT(*) FROM ingredients WHERE expireDate IS NOT NULL AND expireDate <= :date")
    public abstract int getExpiringCount(@org.jetbrains.annotations.NotNull()
    java.util.Date date);
    
    @androidx.room.Query(value = "SELECT * FROM ingredients WHERE expireDate IS NOT NULL AND expireDate < :currentDate ORDER BY expireDate ASC")
    @org.jetbrains.annotations.NotNull()
    public abstract androidx.lifecycle.LiveData<java.util.List<com.fridge.app.data.model.Ingredient>> getExpiredIngredients(@org.jetbrains.annotations.NotNull()
    java.util.Date currentDate);
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 3, xi = 48)
    public static final class DefaultImpls {
    }
}