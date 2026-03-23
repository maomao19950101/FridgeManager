package com.fridge.app.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "ingredients")
data class Ingredient(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val category: IngredientCategory,
    val quantity: Float,
    val unit: String,
    val addDate: Date,
    val expireDate: Date? = null,
    val photoUri: String? = null,
    val notes: String? = null
) {
    fun getDaysUntilExpire(): Int? {
        expireDate ?: return null
        val diff = expireDate.time - System.currentTimeMillis()
        return (diff / (1000 * 60 * 60 * 24)).toInt()
    }
    
    fun getStatus(): IngredientStatus {
        val days = getDaysUntilExpire() ?: return IngredientStatus.NORMAL
        return when {
            days < 0 -> IngredientStatus.EXPIRED
            days <= 1 -> IngredientStatus.EXPIRING_SOON
            days <= 3 -> IngredientStatus.WARNING
            else -> IngredientStatus.NORMAL
        }
    }
}

enum class IngredientCategory {
    VEGETABLE,      // 蔬菜
    FRUIT,          // 水果
    MEAT,           // 肉类
    SEAFOOD,        // 海鲜
    EGG,            // 蛋
    DAIRY,          // 乳制品
    GRAIN,          // 米面
    SEASONING,      // 调料
    CANNED,         // 罐头
    FROZEN,         // 冷冻食品
    OTHER           // 其他
}

enum class IngredientStatus {
    NORMAL,         // 正常
    WARNING,        // 注意（3天内过期）
    EXPIRING_SOON,  // 即将过期（1天内）
    EXPIRED         // 已过期
}
