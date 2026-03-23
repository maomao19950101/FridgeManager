package com.fridge.app.data.model;

/**
 * 完整统计报表
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000H\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0012\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001BO\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005\u0012\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\b0\u0005\u0012\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\n0\u0005\u0012\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\f0\u0005\u0012\b\b\u0002\u0010\r\u001a\u00020\u000e\u00a2\u0006\u0002\u0010\u000fJ\t\u0010\u0019\u001a\u00020\u0003H\u00c6\u0003J\u000f\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005H\u00c6\u0003J\u000f\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\b0\u0005H\u00c6\u0003J\u000f\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\n0\u0005H\u00c6\u0003J\u000f\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\f0\u0005H\u00c6\u0003J\t\u0010\u001e\u001a\u00020\u000eH\u00c6\u0003J]\u0010\u001f\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\u000e\b\u0002\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u00052\u000e\b\u0002\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\b0\u00052\u000e\b\u0002\u0010\t\u001a\b\u0012\u0004\u0012\u00020\n0\u00052\u000e\b\u0002\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\f0\u00052\b\b\u0002\u0010\r\u001a\u00020\u000eH\u00c6\u0001J\u0013\u0010 \u001a\u00020!2\b\u0010\"\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010#\u001a\u00020$H\u00d6\u0001J\t\u0010%\u001a\u00020&H\u00d6\u0001R\u0017\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0017\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\f0\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0011R\u0011\u0010\r\u001a\u00020\u000e\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014R\u0017\u0010\t\u001a\b\u0012\u0004\u0012\u00020\n0\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0011R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0017R\u0017\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\b0\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0011\u00a8\u0006\'"}, d2 = {"Lcom/fridge/app/data/model/StatisticsReport;", "", "overall", "Lcom/fridge/app/data/model/OverallStatistics;", "categoryStats", "", "Lcom/fridge/app/data/model/CategoryStatistics;", "statusStats", "Lcom/fridge/app/data/model/StatusStatistics;", "monthlyTrends", "Lcom/fridge/app/data/model/MonthlyTrend;", "expiryRateTrends", "Lcom/fridge/app/data/model/ExpiryRateTrend;", "generatedAt", "", "(Lcom/fridge/app/data/model/OverallStatistics;Ljava/util/List;Ljava/util/List;Ljava/util/List;Ljava/util/List;J)V", "getCategoryStats", "()Ljava/util/List;", "getExpiryRateTrends", "getGeneratedAt", "()J", "getMonthlyTrends", "getOverall", "()Lcom/fridge/app/data/model/OverallStatistics;", "getStatusStats", "component1", "component2", "component3", "component4", "component5", "component6", "copy", "equals", "", "other", "hashCode", "", "toString", "", "app_debug"})
public final class StatisticsReport {
    @org.jetbrains.annotations.NotNull()
    private final com.fridge.app.data.model.OverallStatistics overall = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<com.fridge.app.data.model.CategoryStatistics> categoryStats = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<com.fridge.app.data.model.StatusStatistics> statusStats = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<com.fridge.app.data.model.MonthlyTrend> monthlyTrends = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<com.fridge.app.data.model.ExpiryRateTrend> expiryRateTrends = null;
    private final long generatedAt = 0L;
    
    public StatisticsReport(@org.jetbrains.annotations.NotNull()
    com.fridge.app.data.model.OverallStatistics overall, @org.jetbrains.annotations.NotNull()
    java.util.List<com.fridge.app.data.model.CategoryStatistics> categoryStats, @org.jetbrains.annotations.NotNull()
    java.util.List<com.fridge.app.data.model.StatusStatistics> statusStats, @org.jetbrains.annotations.NotNull()
    java.util.List<com.fridge.app.data.model.MonthlyTrend> monthlyTrends, @org.jetbrains.annotations.NotNull()
    java.util.List<com.fridge.app.data.model.ExpiryRateTrend> expiryRateTrends, long generatedAt) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.fridge.app.data.model.OverallStatistics getOverall() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.fridge.app.data.model.CategoryStatistics> getCategoryStats() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.fridge.app.data.model.StatusStatistics> getStatusStats() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.fridge.app.data.model.MonthlyTrend> getMonthlyTrends() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.fridge.app.data.model.ExpiryRateTrend> getExpiryRateTrends() {
        return null;
    }
    
    public final long getGeneratedAt() {
        return 0L;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.fridge.app.data.model.OverallStatistics component1() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.fridge.app.data.model.CategoryStatistics> component2() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.fridge.app.data.model.StatusStatistics> component3() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.fridge.app.data.model.MonthlyTrend> component4() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.fridge.app.data.model.ExpiryRateTrend> component5() {
        return null;
    }
    
    public final long component6() {
        return 0L;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.fridge.app.data.model.StatisticsReport copy(@org.jetbrains.annotations.NotNull()
    com.fridge.app.data.model.OverallStatistics overall, @org.jetbrains.annotations.NotNull()
    java.util.List<com.fridge.app.data.model.CategoryStatistics> categoryStats, @org.jetbrains.annotations.NotNull()
    java.util.List<com.fridge.app.data.model.StatusStatistics> statusStats, @org.jetbrains.annotations.NotNull()
    java.util.List<com.fridge.app.data.model.MonthlyTrend> monthlyTrends, @org.jetbrains.annotations.NotNull()
    java.util.List<com.fridge.app.data.model.ExpiryRateTrend> expiryRateTrends, long generatedAt) {
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
}