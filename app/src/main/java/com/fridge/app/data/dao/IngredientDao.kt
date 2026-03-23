package com.fridge.app.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.fridge.app.data.model.Ingredient
import com.fridge.app.data.model.IngredientCategory
import com.fridge.app.data.model.IngredientStatus
import java.util.Date

/**
 * 分类统计结果
 */
data class CategoryCount(
    val category: IngredientCategory,
    val count: Int
)

/**
 * 月度统计结果
 */
data class MonthlyCount(
    val month: String,  // 格式：yyyy-MM
    val count: Int
)

@Dao
interface IngredientDao {
    @Query("SELECT * FROM ingredients ORDER BY expireDate ASC")
    fun getAllIngredients(): LiveData<List<Ingredient>>

    // ==================== 统计查询方法 ====================

    /**
     * 按分类统计食材数量
     */
    @Query("SELECT category, COUNT(*) as count FROM ingredients GROUP BY category")
    fun getCategoryCounts(): List<CategoryCount>

    /**
     * 获取指定分类的食材数量
     */
    @Query("SELECT COUNT(*) FROM ingredients WHERE category = :category")
    fun getCountByCategory(category: IngredientCategory): Int

    /**
     * 按状态统计食材数量（需要结合应用逻辑计算）
     * 获取所有食材用于状态统计
     */
    @Query("SELECT * FROM ingredients")
    fun getAllIngredientsSync(): List<Ingredient>

    /**
     * 获取已过期食材数量
     */
    @Query("SELECT COUNT(*) FROM ingredients WHERE expireDate IS NOT NULL AND expireDate < :currentDate")
    fun getExpiredCount(currentDate: Date = Date()): Int

    /**
     * 获取即将过期食材数量（指定天数内）
     */
    @Query("SELECT COUNT(*) FROM ingredients WHERE expireDate IS NOT NULL AND expireDate >= :startDate AND expireDate <= :endDate")
    fun getExpiringCountBetween(startDate: Date, endDate: Date): Int

    /**
     * 按月份统计新增食材数量
     */
    @Query("""
        SELECT 
            strftime('%Y-%m', addDate / 1000, 'unixepoch', 'localtime') as month,
            COUNT(*) as count
        FROM ingredients
        WHERE addDate >= :startDate
        GROUP BY month
        ORDER BY month ASC
    """)
    fun getMonthlyAddedCounts(startDate: Date): List<MonthlyCount>

    /**
     * 按月份统计过期食材数量
     */
    @Query("""
        SELECT 
            strftime('%Y-%m', expireDate / 1000, 'unixepoch', 'localtime') as month,
            COUNT(*) as count
        FROM ingredients
        WHERE expireDate IS NOT NULL AND expireDate < :currentDate AND expireDate >= :startDate
        GROUP BY month
        ORDER BY month ASC
    """)
    fun getMonthlyExpiredCounts(startDate: Date, currentDate: Date = Date()): List<MonthlyCount>

    /**
     * 获取最早的添加日期
     */
    @Query("SELECT MIN(addDate) FROM ingredients")
    fun getEarliestAddDate(): Date?

    /**
     * 获取指定日期范围内添加的食材
     */
    @Query("SELECT * FROM ingredients WHERE addDate >= :startDate AND addDate <= :endDate ORDER BY addDate ASC")
    fun getIngredientsAddedBetween(startDate: Date, endDate: Date): List<Ingredient>

    /**
     * 获取所有分类的食材（同步）
     */
    @Query("SELECT * FROM ingredients ORDER BY category ASC")
    fun getAllIngredientsByCategory(): List<Ingredient>

    @Query("SELECT * FROM ingredients WHERE id = :id")
    fun getIngredientById(id: Long): Ingredient?

    @Query("SELECT * FROM ingredients WHERE category = :category ORDER BY expireDate ASC")
    fun getIngredientsByCategory(category: IngredientCategory): LiveData<List<Ingredient>>

    @Query("SELECT * FROM ingredients WHERE expireDate <= :date ORDER BY expireDate ASC")
    fun getIngredientsExpiringBefore(date: Date): LiveData<List<Ingredient>>

    @Query("SELECT * FROM ingredients WHERE expireDate IS NULL OR expireDate > :currentDate ORDER BY name ASC")
    fun getValidIngredients(currentDate: Date = Date()): LiveData<List<Ingredient>>

    @Query("SELECT * FROM ingredients WHERE expireDate IS NOT NULL AND expireDate <= :date ORDER BY expireDate ASC")
    fun getExpiringIngredients(date: Date): List<Ingredient>

    @Query("SELECT * FROM ingredients WHERE name LIKE '%' || :query || '%' ORDER BY name ASC")
    fun searchIngredients(query: String): LiveData<List<Ingredient>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertIngredient(ingredient: Ingredient): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertIngredients(ingredients: List<Ingredient>)

    @Update
    fun updateIngredient(ingredient: Ingredient)

    @Delete
    fun deleteIngredient(ingredient: Ingredient)

    @Query("DELETE FROM ingredients WHERE id = :id")
    fun deleteIngredientById(id: Long)

    @Query("DELETE FROM ingredients")
    fun deleteAllIngredients()

    @Query("SELECT COUNT(*) FROM ingredients")
    fun getIngredientCount(): Int

    @Query("SELECT COUNT(*) FROM ingredients WHERE expireDate IS NOT NULL AND expireDate <= :date")
    fun getExpiringCount(date: Date): Int

    @Query("SELECT * FROM ingredients WHERE expireDate IS NOT NULL AND expireDate < :currentDate ORDER BY expireDate ASC")
    fun getExpiredIngredients(currentDate: Date = Date()): LiveData<List<Ingredient>>
}
