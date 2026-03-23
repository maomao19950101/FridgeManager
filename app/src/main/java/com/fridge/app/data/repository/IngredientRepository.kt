package com.fridge.app.data.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.fridge.app.data.dao.IngredientDao
import com.fridge.app.data.db.AppDatabase
import com.fridge.app.data.model.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.Date
import java.util.Calendar

class IngredientRepository(private val ingredientDao: IngredientDao) {
    constructor(application: Application) : this(AppDatabase.getDatabase(application).ingredientDao())

    val allIngredients: LiveData<List<Ingredient>> = ingredientDao.getAllIngredients()

    suspend fun getIngredientById(id: Long): Ingredient? = withContext(Dispatchers.IO) {
        ingredientDao.getIngredientById(id)
    }
    
    // 别名方法
    suspend fun getById(id: Long): Ingredient? {
        return getIngredientById(id)
    }
    
    // 获取所有食材（非LiveData）
    suspend fun getAll(): List<Ingredient> = withContext(Dispatchers.IO) {
        ingredientDao.getAllIngredientsSync()
    }

    fun getIngredientsByCategory(category: IngredientCategory): LiveData<List<Ingredient>> {
        return ingredientDao.getIngredientsByCategory(category)
    }

    fun getValidIngredients(): LiveData<List<Ingredient>> {
        return ingredientDao.getValidIngredients()
    }

    fun getExpiredIngredients(): LiveData<List<Ingredient>> {
        return ingredientDao.getExpiredIngredients()
    }

    suspend fun getExpiringIngredients(days: Int): List<Ingredient> {
        val calendar = java.util.Calendar.getInstance()
        calendar.add(java.util.Calendar.DAY_OF_YEAR, days)
        return withContext(Dispatchers.IO) {
            ingredientDao.getExpiringIngredients(calendar.time)
        }
    }

    fun searchIngredients(query: String): LiveData<List<Ingredient>> {
        return ingredientDao.searchIngredients(query)
    }

    suspend fun insertIngredient(ingredient: Ingredient): Long = withContext(Dispatchers.IO) {
        ingredientDao.insertIngredient(ingredient)
    }

    suspend fun updateIngredient(ingredient: Ingredient) = withContext(Dispatchers.IO) {
        ingredientDao.updateIngredient(ingredient)
    }

    suspend fun update(ingredient: Ingredient) {
        updateIngredient(ingredient)
    }

    suspend fun deleteIngredient(ingredient: Ingredient) = withContext(Dispatchers.IO) {
        ingredientDao.deleteIngredient(ingredient)
    }

    suspend fun delete(ingredient: Ingredient) {
        deleteIngredient(ingredient)
    }

    suspend fun deleteIngredientById(id: Long) = withContext(Dispatchers.IO) {
        ingredientDao.deleteIngredientById(id)
    }

    suspend fun deleteAllIngredients() = withContext(Dispatchers.IO) {
        ingredientDao.deleteAllIngredients()
    }

    suspend fun getIngredientCount(): Int = withContext(Dispatchers.IO) {
        ingredientDao.getIngredientCount()
    }

    suspend fun getExpiringCount(days: Int): Int {
        val calendar = java.util.Calendar.getInstance()
        calendar.add(java.util.Calendar.DAY_OF_YEAR, days)
        return withContext(Dispatchers.IO) {
            ingredientDao.getExpiringCount(calendar.time)
        }
    }

    // 获取按状态分组的食材
    fun getIngredientsByStatus(): Map<IngredientStatus, List<Ingredient>> {
        val ingredients = allIngredients.value ?: return emptyMap()
        return ingredients.groupBy { it.getStatus() }
    }

    // 检查是否有过期食材
    suspend fun hasExpiredIngredients(): Boolean {
        return getExpiredIngredients().value?.isNotEmpty() == true
    }

    // 获取即将过期的食材数量（3天内）
    suspend fun getExpiringSoonCount(): Int {
        return getExpiringCount(3)
    }

    // ==================== 统计方法 ====================

    /**
     * 获取总体统计数据
     */
    suspend fun getOverallStatistics(): OverallStatistics {
        val allIngredients = withContext(Dispatchers.IO) {
            ingredientDao.getAllIngredientsSync()
        }
        val totalCount = allIngredients.size
        
        val expiredCount = allIngredients.count { it.getStatus() == IngredientStatus.EXPIRED }
        val expiringSoonCount = allIngredients.count { it.getStatus() == IngredientStatus.EXPIRING_SOON }
        val warningCount = allIngredients.count { it.getStatus() == IngredientStatus.WARNING }
        val normalCount = allIngredients.count { it.getStatus() == IngredientStatus.NORMAL }
        
        val expiredRate = if (totalCount > 0) {
            (expiredCount.toFloat() / totalCount * 100)
        } else 0f
        
        val categoryCount = allIngredients.map { it.category }.distinct().size

        return OverallStatistics(
            totalIngredients = totalCount,
            expiredCount = expiredCount,
            expiringSoonCount = expiringSoonCount,
            warningCount = warningCount,
            normalCount = normalCount,
            expiredRate = expiredRate,
            categoryCount = categoryCount
        )
    }

    /**
     * 获取分类统计数据
     */
    suspend fun getCategoryStatistics(): List<CategoryStatistics> {
        val allIngredients = withContext(Dispatchers.IO) {
            ingredientDao.getAllIngredientsSync()
        }
        val totalCount = allIngredients.size
        
        if (totalCount == 0) return emptyList()

        return allIngredients
            .groupBy { it.category }
            .map { (category, ingredients) ->
                val count = ingredients.size
                CategoryStatistics(
                    category = category,
                    categoryName = category.getDisplayName(),
                    count = count,
                    percentage = (count.toFloat() / totalCount * 100),
                    color = category.getChartColor()
                )
            }
            .sortedByDescending { it.count }
    }

    /**
     * 获取状态统计数据
     */
    suspend fun getStatusStatistics(): List<StatusStatistics> {
        val allIngredients = withContext(Dispatchers.IO) {
            ingredientDao.getAllIngredientsSync()
        }
        val totalCount = allIngredients.size
        
        if (totalCount == 0) return emptyList()

        return allIngredients
            .groupBy { it.getStatus() }
            .map { (status, ingredients) ->
                val count = ingredients.size
                StatusStatistics(
                    status = status,
                    statusName = status.getDisplayName(),
                    count = count,
                    percentage = (count.toFloat() / totalCount * 100),
                    color = status.getColorRes()
                )
            }
            .sortedByDescending { it.count }
    }

    /**
     * 获取月度趋势数据（最近12个月）
     */
    suspend fun getMonthlyTrends(months: Int = 12): List<MonthlyTrend> {
        val calendar = Calendar.getInstance()
        val endDate = calendar.time
        
        calendar.add(Calendar.MONTH, -months + 1)
        calendar.set(Calendar.DAY_OF_MONTH, 1)
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        val startDate = calendar.time

        val allIngredients = withContext(Dispatchers.IO) {
            ingredientDao.getAllIngredientsSync()
        }
        val currentDate = Date()

        val trends = mutableListOf<MonthlyTrend>()
        
        // 生成最近12个月的月份列表
        for (i in 0 until months) {
            val monthCal = Calendar.getInstance()
            monthCal.add(Calendar.MONTH, -months + 1 + i)
            
            val yearMonth = String.format("%04d-%02d", 
                monthCal.get(Calendar.YEAR), 
                monthCal.get(Calendar.MONTH) + 1
            )
            val monthLabel = "${monthCal.get(Calendar.MONTH) + 1}月"
            
            // 计算该月第一天和最后一天
            monthCal.set(Calendar.DAY_OF_MONTH, 1)
            monthCal.set(Calendar.HOUR_OF_DAY, 0)
            monthCal.set(Calendar.MINUTE, 0)
            monthCal.set(Calendar.SECOND, 0)
            val monthStart = monthCal.time
            
            monthCal.set(Calendar.DAY_OF_MONTH, monthCal.getActualMaximum(Calendar.DAY_OF_MONTH))
            monthCal.set(Calendar.HOUR_OF_DAY, 23)
            monthCal.set(Calendar.MINUTE, 59)
            monthCal.set(Calendar.SECOND, 59)
            val monthEnd = monthCal.time
            
            val addedCount = allIngredients.count { 
                it.addDate >= monthStart && it.addDate <= monthEnd 
            }
            
            val expiredCount = allIngredients.count { 
                it.expireDate != null && 
                it.expireDate >= monthStart && 
                it.expireDate <= monthEnd &&
                it.expireDate < currentDate
            }
            
            trends.add(MonthlyTrend(
                yearMonth = yearMonth,
                monthLabel = monthLabel,
                addedCount = addedCount,
                expiredCount = expiredCount
            ))
        }
        
        return trends
    }

    /**
     * 获取过期率趋势数据（最近12个月）
     */
    suspend fun getExpiryRateTrends(months: Int = 12): List<ExpiryRateTrend> {
        val monthlyTrends = getMonthlyTrends(months)
        val allIngredients = withContext(Dispatchers.IO) {
            ingredientDao.getAllIngredientsSync()
        }
        val currentDate = Date()

        return monthlyTrends.map { trend ->
            // 计算到该月末为止的累计数据
            val calendar = Calendar.getInstance()
            val parts = trend.yearMonth.split("-")
            calendar.set(parts[0].toInt(), parts[1].toInt() - 1, 1)
            calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH))
            calendar.set(Calendar.HOUR_OF_DAY, 23)
            calendar.set(Calendar.MINUTE, 59)
            calendar.set(Calendar.SECOND, 59)
            val monthEnd = calendar.time

            // 统计该月结束时有效的食材（添加日期 <= 月末）
            val activeIngredients = allIngredients.filter { it.addDate <= monthEnd }
            val totalCount = activeIngredients.size
            
            // 统计该月结束时已过期的食材
            val expiredCount = activeIngredients.count { 
                it.expireDate != null && it.expireDate <= monthEnd && it.expireDate < currentDate
            }
            
            val expiryRate = if (totalCount > 0) {
                (expiredCount.toFloat() / totalCount * 100)
            } else 0f

            ExpiryRateTrend(
                yearMonth = trend.yearMonth,
                monthLabel = trend.monthLabel,
                expiryRate = expiryRate,
                totalCount = totalCount,
                expiredCount = expiredCount
            )
        }
    }

    /**
     * 获取完整统计报表
     */
    suspend fun getStatisticsReport(): StatisticsReport {
        return StatisticsReport(
            overall = getOverallStatistics(),
            categoryStats = getCategoryStatistics(),
            statusStats = getStatusStatistics(),
            monthlyTrends = getMonthlyTrends(),
            expiryRateTrends = getExpiryRateTrends()
        )
    }
}
