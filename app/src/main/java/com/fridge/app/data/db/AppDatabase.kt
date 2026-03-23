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
            val sampleRecipes = listOf(
                Recipe(
                    id = 0,
                    name = "Tomato Scrambled Eggs",
                    description = "A simple homestyle dish that's quick to make.",
                    cookingTime = 15,
                    difficulty = Difficulty.EASY,
                    servings = 2,
                    ingredients = listOf(
                        RecipeIngredient("Tomato", "2", true),
                        RecipeIngredient("Egg", "3", true),
                        RecipeIngredient("Scallion", "to taste", false),
                        RecipeIngredient("Salt", "to taste", true)
                    ),
                    steps = listOf(
                        "Cut the tomatoes into wedges and beat the eggs.",
                        "Cook the eggs first and set them aside.",
                        "Stir-fry the tomatoes until they soften and release juice.",
                        "Return the eggs to the pan, season, and serve."
                    ),
                    tags = listOf("Homestyle", "Quick", "Vegetarian"),
                    cuisineType = "Home Cooking"
                ),
                Recipe(
                    id = 0,
                    name = "Kung Pao Chicken",
                    description = "A classic spicy stir-fry with chicken and peanuts.",
                    cookingTime = 30,
                    difficulty = Difficulty.MEDIUM,
                    servings = 3,
                    ingredients = listOf(
                        RecipeIngredient("Chicken Breast", "300g", true),
                        RecipeIngredient("Peanuts", "50g", true),
                        RecipeIngredient("Dried Chili", "10", true),
                        RecipeIngredient("Sichuan Pepper", "1 tbsp", true),
                        RecipeIngredient("Scallion", "to taste", true)
                    ),
                    steps = listOf(
                        "Dice and marinate the chicken for 15 minutes.",
                        "Toast the peanuts and set them aside.",
                        "Stir-fry chili and Sichuan pepper until fragrant.",
                        "Cook the chicken until just done.",
                        "Add the sauce, toss everything together, then finish with peanuts."
                    ),
                    tags = listOf("Sichuan", "Spicy", "Dinner"),
                    cuisineType = "Sichuan"
                )
            )
            sampleRecipes.forEach { recipeDao.insertRecipe(it) }
        }
    }
}
