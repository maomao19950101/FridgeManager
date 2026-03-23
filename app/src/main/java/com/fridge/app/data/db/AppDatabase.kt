package com.fridge.app.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.fridge.app.data.dao.IngredientDao
import com.fridge.app.data.dao.RecipeDao
import com.fridge.app.data.local.Converters
import com.fridge.app.data.model.Difficulty
import com.fridge.app.data.model.Ingredient
import com.fridge.app.data.model.Recipe
import com.fridge.app.data.model.RecipeIngredient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(
    entities = [Ingredient::class, Recipe::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun ingredientDao(): IngredientDao
    abstract fun recipeDao(): RecipeDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "fridge_database"
                )
                    .addCallback(DatabaseCallback())
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }

    private class DatabaseCallback : RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                CoroutineScope(Dispatchers.IO).launch {
                    populateDatabase(database.recipeDao())
                }
            }
        }

        suspend fun populateDatabase(recipeDao: RecipeDao) {
            // 预置一些示例菜谱
            val sampleRecipes = listOf(
                Recipe(
                    id = 0,
                    name = "番茄炒蛋",
                    description = "经典家常菜，简单美味",
                    cookingTime = 15,
                    difficulty = Difficulty.EASY,
                    servings = 2,
                    ingredients = listOf(
                        RecipeIngredient("番茄", "2个", true),
                        RecipeIngredient("鸡蛋", "3个", true),
                        RecipeIngredient("葱花", "适量", false),
                        RecipeIngredient("盐", "适量", true)
                    ),
                    steps = listOf(
                        "番茄洗净切块，鸡蛋打散备用",
                        "热锅凉油，倒入蛋液炒熟盛出",
                        "锅中留底油，放入番茄翻炒出汁",
                        "加入炒好的鸡蛋，调味即可出锅"
                    ),
                    tags = listOf("家常菜", "快手菜", "素食"),
                    cuisineType = "家常菜"
                ),
                Recipe(
                    id = 0,
                    name = "宫保鸡丁",
                    description = "川菜经典，麻辣鲜香",
                    cookingTime = 30,
                    difficulty = Difficulty.MEDIUM,
                    servings = 3,
                    ingredients = listOf(
                        RecipeIngredient("鸡胸肉", "300g", true),
                        RecipeIngredient("花生米", "50g", true),
                        RecipeIngredient("干辣椒", "10个", true),
                        RecipeIngredient("花椒", "1勺", true),
                        RecipeIngredient("葱姜蒜", "适量", true)
                    ),
                    steps = listOf(
                        "鸡胸肉切丁，加料酒、生抽腌制15分钟",
                        "花生米炸香备用",
                        "热锅凉油，放入花椒、干辣椒爆香",
                        "放入鸡丁炒至变色",
                        "加入调料汁翻炒，最后撒入花生米"
                    ),
                    tags = listOf("川菜", "辣", "下饭菜"),
                    cuisineType = "川菜"
                )
            )
            sampleRecipes.forEach { recipeDao.insertRecipe(it) }
        }
    }
}