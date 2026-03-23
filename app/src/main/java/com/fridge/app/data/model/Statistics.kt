package com.fridge.app.data.model

/**
 * 统计数据模型
 * 用于存储和展示冰箱食材的各类统计数据
 */

/**
 * 总体统计信息
 */
data class OverallStatistics(
    val totalIngredients: Int,              // 总食材数
    val expiredCount: Int,                  // 已过期数量
    val expiringSoonCount: Int,             // 即将过期数量（1天内）
    val warningCount: Int,                  // 警告数量（3天内）
    val normalCount: Int,                   // 正常数量
    val expiredRate: Float,                 // 过期率
    val categoryCount: Int                  // 分类数量
)

/**
 * 分类统计数据
 */
data class CategoryStatistics(
    val category: IngredientCategory,       // 食材分类
    val categoryName: String,               // 分类名称
    val count: Int,                         // 该分类食材数量
    val percentage: Float,                  // 占比（0-100）
    val color: Int                          // 图表颜色
)

/**
 * 状态统计数据
 */
data class StatusStatistics(
    val status: IngredientStatus,           // 食材状态
    val statusName: String,                 // 状态名称
    val count: Int,                         // 该状态食材数量
    val percentage: Float,                  // 占比（0-100）
    val color: Int                          // 图表颜色
)

/**
 * 月度趋势数据
 */
data class MonthlyTrend(
    val yearMonth: String,                  // 年月（格式：yyyy-MM）
    val monthLabel: String,                 // 显示标签（格式：M月）
    val addedCount: Int,                    // 新增食材数
    val expiredCount: Int                   // 过期食材数
)

/**
 * 过期率趋势数据
 */
data class ExpiryRateTrend(
    val yearMonth: String,                  // 年月（格式：yyyy-MM）
    val monthLabel: String,                 // 显示标签
    val expiryRate: Float,                  // 过期率（0-100）
    val totalCount: Int,                    // 总数
    val expiredCount: Int                   // 过期数
)

/**
 * 完整统计报表
 */
data class StatisticsReport(
    val overall: OverallStatistics,                                 // 总体统计
    val categoryStats: List<CategoryStatistics>,                    // 分类统计列表
    val statusStats: List<StatusStatistics>,                        // 状态统计列表
    val monthlyTrends: List<MonthlyTrend>,                          // 月度趋势
    val expiryRateTrends: List<ExpiryRateTrend>,                    // 过期率趋势
    val generatedAt: Long = System.currentTimeMillis()              // 生成时间
)

/**
 * 获取分类的显示名称
 */
fun IngredientCategory.getDisplayName(): String {
    return when (this) {
        IngredientCategory.VEGETABLE -> "蔬菜"
        IngredientCategory.FRUIT -> "水果"
        IngredientCategory.MEAT -> "肉类"
        IngredientCategory.SEAFOOD -> "海鲜"
        IngredientCategory.EGG -> "蛋类"
        IngredientCategory.DAIRY -> "乳制品"
        IngredientCategory.GRAIN -> "米面"
        IngredientCategory.SEASONING -> "调料"
        IngredientCategory.CANNED -> "罐头"
        IngredientCategory.FROZEN -> "冷冻食品"
        IngredientCategory.OTHER -> "其他"
    }
}

/**
 * 获取状态的显示名称
 */
fun IngredientStatus.getDisplayName(): String {
    return when (this) {
        IngredientStatus.NORMAL -> "正常"
        IngredientStatus.WARNING -> "注意"
        IngredientStatus.EXPIRING_SOON -> "即将过期"
        IngredientStatus.EXPIRED -> "已过期"
    }
}

/**
 * 获取状态的颜色资源（需要在UI层转换为实际颜色）
 */
fun IngredientStatus.getColorRes(): Int {
    return when (this) {
        IngredientStatus.NORMAL -> android.R.color.holo_green_dark
        IngredientStatus.WARNING -> android.R.color.holo_orange_light
        IngredientStatus.EXPIRING_SOON -> android.R.color.holo_orange_dark
        IngredientStatus.EXPIRED -> android.R.color.holo_red_dark
    }
}

/**
 * 获取分类的图表颜色
 */
fun IngredientCategory.getChartColor(): Int {
    val colors = listOf(
        0xFF4CAF50.toInt(),  // 绿色 - 蔬菜
        0xFFFF9800.toInt(),  // 橙色 - 水果
        0xFFE91E63.toInt(),  // 粉红 - 肉类
        0xFF2196F3.toInt(),  // 蓝色 - 海鲜
        0xFFFFEB3B.toInt(),  // 黄色 - 蛋
        0xFF9C27B0.toInt(),  // 紫色 - 乳制品
        0xFF795548.toInt(),  // 棕色 - 米面
        0xFF607D8B.toInt(),  // 蓝灰 - 调料
        0xFFF44336.toInt(),  // 红色 - 罐头
        0xFF00BCD4.toInt(),  // 青色 - 冷冻
        0xFF9E9E9E.toInt()   // 灰色 - 其他
    )
    return colors.getOrElse(this.ordinal) { colors.last() }
}
