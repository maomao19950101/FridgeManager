package com.fridge.app.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.fridge.app.data.local.Converters

@Entity(tableName = "recipes")
@TypeConverters(Converters::class)
data class Recipe(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val description: String? = null,
    val imageUrl: String? = null,
    val cookingTime: Int,  // 分钟
    val difficulty: Difficulty,
    val servings: Int,
    val ingredients: List<RecipeIngredient>,
    val steps: List<String>,
    val tags: List<String>,
    val cuisineType: String? = null  // 菜系：川菜/粤菜/家常菜等
)

data class RecipeIngredient(
    val name: String,
    val amount: String,  // 如："200g", "2个"
    val isEssential: Boolean = true  // 是否必需
)

enum class Difficulty {
    EASY,       // 简单
    MEDIUM,     // 中等
    HARD        // 困难
}

// 匹配结果 - 使用普通类避免复杂序列化问题
data class RecipeMatchResult(
    val recipe: Recipe,
    val missingIngredients: List<RecipeIngredient>,
    val matchRate: Float,  // 匹配率 0-1
    val canMake: Boolean = missingIngredients.isEmpty()
) {
    companion object {
        fun canMake(recipe: Recipe): RecipeMatchResult {
            return RecipeMatchResult(recipe, emptyList(), 1.0f, true)
        }

        fun needMore(recipe: Recipe, missing: List<RecipeIngredient>, rate: Float): RecipeMatchResult {
            return RecipeMatchResult(recipe, missing, rate, false)
        }
    }
}
