package com.fridge.app.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.fridge.app.data.db.AppDatabase
import com.fridge.app.data.model.*
import com.fridge.app.data.repository.IngredientRepository
import kotlinx.coroutines.launch

/**
 * 统计ViewModel
 * 提供统计数据给UI层使用
 */
class StatisticsViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: IngredientRepository

    // 总体统计
    private val _overallStats = MutableLiveData<OverallStatistics>()
    val overallStats: LiveData<OverallStatistics> = _overallStats

    // 分类统计
    private val _categoryStats = MutableLiveData<List<CategoryStatistics>>()
    val categoryStats: LiveData<List<CategoryStatistics>> = _categoryStats

    // 状态统计
    private val _statusStats = MutableLiveData<List<StatusStatistics>>()
    val statusStats: LiveData<List<StatusStatistics>> = _statusStats

    // 月度趋势
    private val _monthlyTrends = MutableLiveData<List<MonthlyTrend>>()
    val monthlyTrends: LiveData<List<MonthlyTrend>> = _monthlyTrends

    // 过期率趋势
    private val _expiryRateTrends = MutableLiveData<List<ExpiryRateTrend>>()
    val expiryRateTrends: LiveData<List<ExpiryRateTrend>> = _expiryRateTrends

    // 完整报表
    private val _statisticsReport = MutableLiveData<StatisticsReport>()
    val statisticsReport: LiveData<StatisticsReport> = _statisticsReport

    // 加载状态
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    // 错误信息
    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    init {
        val ingredientDao = AppDatabase.getDatabase(application).ingredientDao()
        repository = IngredientRepository(ingredientDao)
        loadAllStatistics()
    }

    /**
     * 加载所有统计数据
     */
    fun loadAllStatistics() {
        viewModelScope.launch {
            try {
                _isLoading.postValue(true)
                _error.postValue(null)

                // 并行加载所有统计数据
                launch { _overallStats.postValue(repository.getOverallStatistics()) }
                launch { _categoryStats.postValue(repository.getCategoryStatistics()) }
                launch { _statusStats.postValue(repository.getStatusStatistics()) }
                launch { _monthlyTrends.postValue(repository.getMonthlyTrends()) }
                launch { _expiryRateTrends.postValue(repository.getExpiryRateTrends()) }

                _isLoading.postValue(false)
            } catch (e: Exception) {
                _error.postValue("加载统计数据失败: ${e.message}")
                _isLoading.postValue(false)
            }
        }
    }

    /**
     * 加载完整统计报表
     */
    fun loadStatisticsReport() {
        viewModelScope.launch {
            try {
                _isLoading.postValue(true)
                _error.postValue(null)
                _statisticsReport.postValue(repository.getStatisticsReport())
                _isLoading.postValue(false)
            } catch (e: Exception) {
                _error.postValue("加载统计报表失败: ${e.message}")
                _isLoading.postValue(false)
            }
        }
    }

    /**
     * 刷新统计数据
     */
    fun refreshStatistics() {
        loadAllStatistics()
    }

    /**
     * 获取指定时间范围的月度趋势
     */
    fun loadMonthlyTrends(months: Int) {
        viewModelScope.launch {
            try {
                _monthlyTrends.postValue(repository.getMonthlyTrends(months))
            } catch (e: Exception) {
                _error.postValue("加载月度趋势失败: ${e.message}")
            }
        }
    }

    /**
     * 获取指定时间范围的过期率趋势
     */
    fun loadExpiryRateTrends(months: Int) {
        viewModelScope.launch {
            try {
                _expiryRateTrends.postValue(repository.getExpiryRateTrends(months))
            } catch (e: Exception) {
                _error.postValue("加载过期率趋势失败: ${e.message}")
            }
        }
    }

    /**
     * 清除错误信息
     */
    fun clearError() {
        _error.value = null
    }
}
