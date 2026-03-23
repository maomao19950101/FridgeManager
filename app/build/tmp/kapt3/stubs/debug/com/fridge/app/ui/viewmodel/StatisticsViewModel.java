package com.fridge.app.ui.viewmodel;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.fridge.app.data.db.AppDatabase;
import com.fridge.app.data.model.*;
import com.fridge.app.data.repository.IngredientRepository;

/**
 * 统计ViewModel
 * 提供统计数据给UI层使用
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000j\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0006\u0010*\u001a\u00020+J\u0006\u0010,\u001a\u00020+J\u000e\u0010-\u001a\u00020+2\u0006\u0010.\u001a\u00020/J\u000e\u00100\u001a\u00020+2\u0006\u0010.\u001a\u00020/J\u0006\u00101\u001a\u00020+J\u0006\u00102\u001a\u00020+R\u001a\u0010\u0005\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00070\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0016\u0010\t\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\n0\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010\u000b\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\f0\u00070\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000e0\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010\u000f\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00100\u00070\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00120\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00140\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010\u0015\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00160\u00070\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001d\u0010\u0017\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00070\u0018\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u001aR\u0019\u0010\u001b\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\n0\u0018\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u001aR\u001d\u0010\u001d\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\f0\u00070\u0018\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\u001aR\u0017\u0010\u001f\u001a\b\u0012\u0004\u0012\u00020\u000e0\u0018\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001f\u0010\u001aR\u001d\u0010 \u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00100\u00070\u0018\u00a2\u0006\b\n\u0000\u001a\u0004\b!\u0010\u001aR\u0017\u0010\"\u001a\b\u0012\u0004\u0012\u00020\u00120\u0018\u00a2\u0006\b\n\u0000\u001a\u0004\b#\u0010\u001aR\u000e\u0010$\u001a\u00020%X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010&\u001a\b\u0012\u0004\u0012\u00020\u00140\u0018\u00a2\u0006\b\n\u0000\u001a\u0004\b\'\u0010\u001aR\u001d\u0010(\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00160\u00070\u0018\u00a2\u0006\b\n\u0000\u001a\u0004\b)\u0010\u001a\u00a8\u00063"}, d2 = {"Lcom/fridge/app/ui/viewmodel/StatisticsViewModel;", "Landroidx/lifecycle/AndroidViewModel;", "application", "Landroid/app/Application;", "(Landroid/app/Application;)V", "_categoryStats", "Landroidx/lifecycle/MutableLiveData;", "", "Lcom/fridge/app/data/model/CategoryStatistics;", "_error", "", "_expiryRateTrends", "Lcom/fridge/app/data/model/ExpiryRateTrend;", "_isLoading", "", "_monthlyTrends", "Lcom/fridge/app/data/model/MonthlyTrend;", "_overallStats", "Lcom/fridge/app/data/model/OverallStatistics;", "_statisticsReport", "Lcom/fridge/app/data/model/StatisticsReport;", "_statusStats", "Lcom/fridge/app/data/model/StatusStatistics;", "categoryStats", "Landroidx/lifecycle/LiveData;", "getCategoryStats", "()Landroidx/lifecycle/LiveData;", "error", "getError", "expiryRateTrends", "getExpiryRateTrends", "isLoading", "monthlyTrends", "getMonthlyTrends", "overallStats", "getOverallStats", "repository", "Lcom/fridge/app/data/repository/IngredientRepository;", "statisticsReport", "getStatisticsReport", "statusStats", "getStatusStats", "clearError", "", "loadAllStatistics", "loadExpiryRateTrends", "months", "", "loadMonthlyTrends", "loadStatisticsReport", "refreshStatistics", "app_debug"})
public final class StatisticsViewModel extends androidx.lifecycle.AndroidViewModel {
    @org.jetbrains.annotations.NotNull()
    private final com.fridge.app.data.repository.IngredientRepository repository = null;
    @org.jetbrains.annotations.NotNull()
    private final androidx.lifecycle.MutableLiveData<com.fridge.app.data.model.OverallStatistics> _overallStats = null;
    @org.jetbrains.annotations.NotNull()
    private final androidx.lifecycle.LiveData<com.fridge.app.data.model.OverallStatistics> overallStats = null;
    @org.jetbrains.annotations.NotNull()
    private final androidx.lifecycle.MutableLiveData<java.util.List<com.fridge.app.data.model.CategoryStatistics>> _categoryStats = null;
    @org.jetbrains.annotations.NotNull()
    private final androidx.lifecycle.LiveData<java.util.List<com.fridge.app.data.model.CategoryStatistics>> categoryStats = null;
    @org.jetbrains.annotations.NotNull()
    private final androidx.lifecycle.MutableLiveData<java.util.List<com.fridge.app.data.model.StatusStatistics>> _statusStats = null;
    @org.jetbrains.annotations.NotNull()
    private final androidx.lifecycle.LiveData<java.util.List<com.fridge.app.data.model.StatusStatistics>> statusStats = null;
    @org.jetbrains.annotations.NotNull()
    private final androidx.lifecycle.MutableLiveData<java.util.List<com.fridge.app.data.model.MonthlyTrend>> _monthlyTrends = null;
    @org.jetbrains.annotations.NotNull()
    private final androidx.lifecycle.LiveData<java.util.List<com.fridge.app.data.model.MonthlyTrend>> monthlyTrends = null;
    @org.jetbrains.annotations.NotNull()
    private final androidx.lifecycle.MutableLiveData<java.util.List<com.fridge.app.data.model.ExpiryRateTrend>> _expiryRateTrends = null;
    @org.jetbrains.annotations.NotNull()
    private final androidx.lifecycle.LiveData<java.util.List<com.fridge.app.data.model.ExpiryRateTrend>> expiryRateTrends = null;
    @org.jetbrains.annotations.NotNull()
    private final androidx.lifecycle.MutableLiveData<com.fridge.app.data.model.StatisticsReport> _statisticsReport = null;
    @org.jetbrains.annotations.NotNull()
    private final androidx.lifecycle.LiveData<com.fridge.app.data.model.StatisticsReport> statisticsReport = null;
    @org.jetbrains.annotations.NotNull()
    private final androidx.lifecycle.MutableLiveData<java.lang.Boolean> _isLoading = null;
    @org.jetbrains.annotations.NotNull()
    private final androidx.lifecycle.LiveData<java.lang.Boolean> isLoading = null;
    @org.jetbrains.annotations.NotNull()
    private final androidx.lifecycle.MutableLiveData<java.lang.String> _error = null;
    @org.jetbrains.annotations.NotNull()
    private final androidx.lifecycle.LiveData<java.lang.String> error = null;
    
    public StatisticsViewModel(@org.jetbrains.annotations.NotNull()
    android.app.Application application) {
        super(null);
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.LiveData<com.fridge.app.data.model.OverallStatistics> getOverallStats() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.LiveData<java.util.List<com.fridge.app.data.model.CategoryStatistics>> getCategoryStats() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.LiveData<java.util.List<com.fridge.app.data.model.StatusStatistics>> getStatusStats() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.LiveData<java.util.List<com.fridge.app.data.model.MonthlyTrend>> getMonthlyTrends() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.LiveData<java.util.List<com.fridge.app.data.model.ExpiryRateTrend>> getExpiryRateTrends() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.LiveData<com.fridge.app.data.model.StatisticsReport> getStatisticsReport() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.LiveData<java.lang.Boolean> isLoading() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.LiveData<java.lang.String> getError() {
        return null;
    }
    
    /**
     * 加载所有统计数据
     */
    public final void loadAllStatistics() {
    }
    
    /**
     * 加载完整统计报表
     */
    public final void loadStatisticsReport() {
    }
    
    /**
     * 刷新统计数据
     */
    public final void refreshStatistics() {
    }
    
    /**
     * 获取指定时间范围的月度趋势
     */
    public final void loadMonthlyTrends(int months) {
    }
    
    /**
     * 获取指定时间范围的过期率趋势
     */
    public final void loadExpiryRateTrends(int months) {
    }
    
    /**
     * 清除错误信息
     */
    public final void clearError() {
    }
}