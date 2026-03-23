package com.fridge.app.data.local

import androidx.room.TypeConverter
import com.fridge.app.data.model.Difficulty
import com.fridge.app.data.model.IngredientCategory
import com.fridge.app.data.model.RecipeIngredient
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.Date

class Converters {
    private val gson = Gson()

    // Date 转换
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }

    // IngredientCategory 转换
    @TypeConverter
    fun fromCategory(category: IngredientCategory): String {
        return category.name
    }

    @TypeConverter
    fun toCategory(value: String): IngredientCategory {
        return IngredientCategory.valueOf(value)
    }

    // Difficulty 转换
    @TypeConverter
    fun fromDifficulty(difficulty: Difficulty): String {
        return difficulty.name
    }

    @TypeConverter
    fun toDifficulty(value: String): Difficulty {
        return Difficulty.valueOf(value)
    }

    // List<String> 转换
    @TypeConverter
    fun fromStringList(list: List<String>): String {
        return gson.toJson(list)
    }

    @TypeConverter
    fun toStringList(value: String): List<String> {
        val type = object : TypeToken<List<String>>() {}.type
        return gson.fromJson(value, type) ?: emptyList()
    }

    // List<RecipeIngredient> 转换
    @TypeConverter
    fun fromRecipeIngredientList(list: List<RecipeIngredient>): String {
        return gson.toJson(list)
    }

    @TypeConverter
    fun toRecipeIngredientList(value: String): List<RecipeIngredient> {
        val type = object : TypeToken<List<RecipeIngredient>>() {}.type
        return gson.fromJson(value, type) ?: emptyList()
    }
}