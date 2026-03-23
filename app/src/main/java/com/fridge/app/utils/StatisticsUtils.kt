package com.fridge.app.utils

import com.fridge.app.data.model.*
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.utils.ColorTemplate
import java.text.SimpleDateFormat
import java.util.*

/**
 * 统计工具类
 * 提供图表数据转换和格式化功能
 */
object StatisticsUtils {

    /**
     * 为饼图生成数据
     */
    fun createPieData(
        categoryStats: List<CategoryStatistics>,
        minPercentage: Float = 3f  // 小于此百分比的合并为"其他"
    ): PieData {
        val entries = mutableListOf<PieEntry>()
        var otherCount = 0
        var otherPercentage = 0f

        categoryStats.forEach { stat ->
            if (stat.percentage >= minPercentage) {
                entries.add(PieEntry(stat.count.toFloat(), stat.categoryName))
            } else {
                otherCount += stat.count
                otherPercentage += stat.percentage
            }
        }

        // 如果有"其他"分类，添加进去
        if (otherCount > 0) {
            entries.add(PieEntry(otherCount.toFloat(), "其他"))
        }

        val dataSet = PieDataSet(entries, "食材分类").apply {
            colors = categoryStats.map { it.color } + ColorTemplate.COLORFUL_COLORS.toList()
            setDrawValues(true)
            valueTextSize = 12f
            valueTextColor = android.graphics.Color.WHITE
        }

        return PieData(dataSet).apply {
            setValueFormatter(PercentFormatter())
        }
    }

    /**
     * 为环形图生成数据（状态分布）
     */
    fun createStatusPieData(statusStats: List<StatusStatistics>): PieData {
        val entries = statusStats.map { stat ->
            PieEntry(stat.count.toFloat(), stat.statusName)
        }

        val colors = listOf(
            android.graphics.Color.parseColor("#4CAF50"),  // 正常 - 绿色
            android.graphics.Color.parseColor("#FF9800"),  // 注意 - 橙色
            android.graphics.Color.parseColor("#FF5722"),  // 即将过期 - 深橙
            android.graphics.Color.parseColor("#F44336")   // 已过期 - 红色
        )

        val dataSet = PieDataSet(entries, "食材状态").apply {
            this.colors = colors
            setDrawValues(true)
            valueTextSize = 12f
            valueTextColor = android.graphics.Color.WHITE
            // 设置环形图
            sliceSpace = 3f
            selectionShift = 5f
        }

        return PieData(dataSet).apply {
            setValueFormatter(CountFormatter())
        }
    }

    /**
     * 为柱状图生成数据（月度趋势）
     */
    fun createBarData(monthlyTrends: List<MonthlyTrend>): BarData {
        val addedEntries = mutableListOf<BarEntry>()
        val expiredEntries = mutableListOf<BarEntry>()

        monthlyTrends.forEachIndexed { index, trend ->
            addedEntries.add(BarEntry(index.toFloat(), trend.addedCount.toFloat()))
            expiredEntries.add(BarEntry(index.toFloat(), trend.expiredCount.toFloat()))
        }

        val addedDataSet = BarDataSet(addedEntries, "新增食材").apply {
            color = android.graphics.Color.parseColor("#2196F3")
            valueTextSize = 10f
        }

        val expiredDataSet = BarDataSet(expiredEntries, "过期食材").apply {
            color = android.graphics.Color.parseColor("#F44336")
            valueTextSize = 10f
        }

        return BarData(addedDataSet, expiredDataSet).apply {
            barWidth = 0.35f
        }
    }

    /**
     * 为折线图生成数据（过期率趋势）
     */
    fun createLineData(expiryRateTrends: List<ExpiryRateTrend>): LineData {
        val entries = expiryRateTrends.mapIndexed { index, trend ->
            Entry(index.toFloat(), trend.expiryRate)
        }

        val dataSet = LineDataSet(entries, "过期率(%)").apply {
            color = android.graphics.Color.parseColor("#E91E63")
            setCircleColor(android.graphics.Color.parseColor("#E91E63"))
            circleRadius = 4f
            lineWidth = 2f
            setDrawFilled(true)
            fillColor = android.graphics.Color.parseColor("#F8BBD9")
            fillAlpha = 100
            valueTextSize = 10f
            mode = LineDataSet.Mode.CUBIC_BEZIER
        }

        return LineData(dataSet)
    }

    /**
     * 获取月度趋势的标签
     */
    fun getMonthLabels(monthlyTrends: List<MonthlyTrend>): List<String> {
        return monthlyTrends.map { it.monthLabel }
    }

    /**
     * 格式化百分比
     */
    fun formatPercentage(percentage: Float): String {
        return String.format("%.1f%%", percentage)
    }

    /**
     * 格式化数量
     */
    fun formatCount(count: Int): String {
        return when {
            count >= 10000 -> "${count / 10000}万"
            count >= 1000 -> "${count / 1000}千"
            else -> count.toString()
        }
    }

    /**
     * 计算过期率
     */
    fun calculateExpiryRate(total: Int, expired: Int): Float {
        return if (total > 0) {
            (expired.toFloat() / total * 100)
        } else 0f
    }

    /**
     * 计算浪费率（基于过期食材的价值估算）
     */
    fun calculateWasteRate(
        expiredIngredients: List<com.fridge.app.data.model.Ingredient>,
        totalIngredients: Int
    ): Float {
        return if (totalIngredients > 0) {
            (expiredIngredients.size.toFloat() / totalIngredients * 100)
        } else 0f
    }

    /**
     * 获取健康度评分（0-100）
     */
    fun calculateHealthScore(overallStats: OverallStatistics): Int {
        if (overallStats.totalIngredients == 0) return 100

        // 基于过期率计算健康度
        val expiryPenalty = overallStats.expiredRate * 2
        val warningPenalty = overallStats.warningCount.toFloat() / overallStats.totalIngredients * 100 * 0.5f
        val expiringPenalty = overallStats.expiringSoonCount.toFloat() / overallStats.totalIngredients * 100

        val score = 100 - expiryPenalty - warningPenalty - expiringPenalty
        return score.toInt().coerceIn(0, 100)
    }

    /**
     * 获取健康度评级
     */
    fun getHealthRating(score: Int): String {
        return when {
            score >= 90 -> "优秀"
            score >= 75 -> "良好"
            score >= 60 -> "一般"
            score >= 40 -> "需关注"
            else -> "严重"
        }
    }

    /**
     * 获取健康度颜色
     */
    fun getHealthColor(score: Int): Int {
        return when {
            score >= 90 -> android.graphics.Color.parseColor("#4CAF50")
            score >= 75 -> android.graphics.Color.parseColor("#8BC34A")
            score >= 60 -> android.graphics.Color.parseColor("#FFC107")
            score >= 40 -> android.graphics.Color.parseColor("#FF9800")
            else -> android.graphics.Color.parseColor("#F44336")
        }
    }

    /**
     * 生成统计摘要文本
     */
    fun generateSummary(overallStats: OverallStatistics): String {
        val healthScore = calculateHealthScore(overallStats)
        val rating = getHealthRating(healthScore)

        return buildString {
            appendLine("📊 冰箱健康报告")
            appendLine("健康评分: $healthScore 分 ($rating)")
            appendLine("总食材: ${overallStats.totalIngredients} 个")
            appendLine("正常: ${overallStats.normalCount} | 注意: ${overallStats.warningCount}")
            appendLine("即将过期: ${overallStats.expiringSoonCount} | 已过期: ${overallStats.expiredCount}")
            if (overallStats.expiredRate > 0) {
                appendLine("过期率: ${formatPercentage(overallStats.expiredRate)}")
            }
        }
    }

    // ==================== 格式化器类 ====================

    /**
     * 百分比格式化器
     */
    class PercentFormatter : ValueFormatter() {
        override fun getFormattedValue(value: Float): String {
            return String.format("%.1f%%", value)
        }
    }

    /**
     * 数量格式化器
     */
    class CountFormatter : ValueFormatter() {
        override fun getFormattedValue(value: Float): String {
            return "${value.toInt()}个"
        }
    }

    /**
     * 月份标签格式化器
     */
    class MonthLabelFormatter(private val labels: List<String>) : ValueFormatter() {
        override fun getFormattedValue(value: Float): String {
            val index = value.toInt()
            return if (index >= 0 && index < labels.size) {
                labels[index]
            } else ""
        }
    }

    /**
     * 分类名称格式化器（用于X轴）
     */
    class CategoryLabelFormatter(private val categories: List<String>) : ValueFormatter() {
        override fun getFormattedValue(value: Float): String {
            val index = value.toInt()
            return if (index >= 0 && index < categories.size) {
                // 截断过长的名称
                categories[index].take(4)
            } else ""
        }
    }
}
