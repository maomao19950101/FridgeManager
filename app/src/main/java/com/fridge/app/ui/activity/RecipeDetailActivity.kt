package com.fridge.app.ui.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.fridge.app.R
import com.fridge.app.data.model.Difficulty
import com.fridge.app.data.model.Recipe
import com.fridge.app.data.model.RecipeIngredient
import com.fridge.app.data.repository.IngredientRepository
import com.fridge.app.data.repository.RecipeRepository
import com.fridge.app.databinding.ActivityRecipeDetailBinding
import com.google.android.material.chip.Chip
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.concurrent.TimeUnit

class RecipeDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRecipeDetailBinding
    private lateinit var recipeRepository: RecipeRepository
    private lateinit var ingredientRepository: IngredientRepository
    
    private var recipeId: Long = -1
    private var recipe: Recipe? = null
    private var availableIngredients = listOf<String>()
    
    private var isFavorite = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecipeDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        recipeRepository = RecipeRepository(application)
        ingredientRepository = IngredientRepository(application)
        
        recipeId = intent.getLongExtra(EXTRA_RECIPE_ID, -1)
        if (recipeId == -1L) {
            finish()
            return
        }
        
        setupToolbar()
        setupListeners()
        loadRecipe()
        loadAvailableIngredients()
    }
    
    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
    }
    
    private fun setupListeners() {
        binding.apply {
            // 图片点击放大
            ivRecipeImage.setOnClickListener {
                recipe?.imageUrl?.let { imageUrl ->
                    val intent = Intent(this@RecipeDetailActivity, ImagePreviewActivity::class.java).apply {
                        putExtra(ImagePreviewActivity.EXTRA_IMAGE_URI, imageUrl)
                        putExtra(ImagePreviewActivity.EXTRA_TITLE, recipe?.name)
                    }
                    startActivity(intent)
                }
            }
            
            // 收藏按钮
            fabFavorite.setOnClickListener {
                toggleFavorite()
            }
            
            // 开始烹饪
            btnStartCooking.setOnClickListener {
                startCookingMode()
            }
            
            // 分享
            btnShare.setOnClickListener {
                shareRecipe()
            }
        }
    }
    
    private fun loadRecipe() {
        lifecycleScope.launch {
            recipe = withContext(Dispatchers.IO) {
                recipeRepository.getById(recipeId)
            }
            recipe?.let { updateUI(it) }
        }
    }
    
    private fun loadAvailableIngredients() {
        lifecycleScope.launch {
            val ingredients = withContext(Dispatchers.IO) {
                ingredientRepository.getAll()
            }
            availableIngredients = ingredients.map { it.name }
            recipe?.let { updateIngredientsUI(it) }
        }
    }
    
    private fun updateUI(recipe: Recipe) {
        binding.apply {
            // 标题
            tvRecipeName.text = recipe.name
            supportActionBar?.title = recipe.name
            
            // 描述
            if (!recipe.description.isNullOrEmpty()) {
                tvDescription.text = recipe.description
                tvDescription.visibility = View.VISIBLE
            } else {
                tvDescription.visibility = View.GONE
            }
            
            // 图片
            if (!recipe.imageUrl.isNullOrEmpty()) {
                try {
                    ivRecipeImage.setImageURI(Uri.parse(recipe.imageUrl))
                    ivRecipeImage.visibility = View.VISIBLE
                } catch (e: Exception) {
                    ivRecipeImage.setImageResource(R.drawable.ic_recipe_placeholder)
                }
            } else {
                ivRecipeImage.setImageResource(R.drawable.ic_recipe_placeholder)
            }
            
            // 难度
            tvDifficulty.text = getDifficultyString(recipe.difficulty)
            tvDifficulty.setChipBackgroundColorResource(getDifficultyColor(recipe.difficulty))
            
            // 烹饪时间
            tvCookingTime.text = getString(R.string.cooking_time_format, recipe.cookingTime)
            
            // 份量
            tvServings.text = getString(R.string.servings_format, recipe.servings)
            
            // 菜系
            if (!recipe.cuisineType.isNullOrEmpty()) {
                tvCuisineType.text = recipe.cuisineType
                tvCuisineType.visibility = View.VISIBLE
            } else {
                tvCuisineType.visibility = View.GONE
            }
            
            // 标签
            setupTags(recipe.tags)
            
            // 食材
            updateIngredientsUI(recipe)
            
            // 步骤
            setupSteps(recipe.steps)
            
            // 收藏状态
            updateFavoriteButton()
        }
    }
    
    private fun updateIngredientsUI(recipe: Recipe) {
        binding.layoutIngredients.removeAllViews()
        
        val missingIngredients = mutableListOf<RecipeIngredient>()
        
        for (ingredient in recipe.ingredients) {
            val ingredientView = layoutInflater.inflate(
                R.layout.item_recipe_ingredient,
                binding.layoutIngredients,
                false
            )
            
            val tvName = ingredientView.findViewById<TextView>(R.id.tvIngredientName)
            val tvAmount = ingredientView.findViewById<TextView>(R.id.tvAmount)
            val ivCheck = ingredientView.findViewById<View>(R.id.ivCheck)
            
            tvName.text = ingredient.name
            tvAmount.text = ingredient.amount
            
            // 检查是否拥有该食材
            val hasIngredient = availableIngredients.any { 
                ingredient.name.contains(it, ignoreCase = true) || 
                it.contains(ingredient.name, ignoreCase = true)
            }
            
            if (hasIngredient) {
                ivCheck.visibility = View.VISIBLE
                tvName.setTextColor(ContextCompat.getColor(this, R.color.text_primary))
            } else {
                ivCheck.visibility = View.GONE
                if (ingredient.isEssential) {
                    tvName.setTextColor(ContextCompat.getColor(this, R.color.status_expired))
                    missingIngredients.add(ingredient)
                } else {
                    tvName.setTextColor(ContextCompat.getColor(this, R.color.text_secondary))
                }
            }
            
            binding.layoutIngredients.addView(ingredientView)
        }
        
        // 更新可制作状态
        updateCanMakeStatus(missingIngredients.isEmpty(), missingIngredients)
    }
    
    private fun updateCanMakeStatus(canMake: Boolean, missing: List<RecipeIngredient>) {
        binding.apply {
            if (canMake) {
                tvCanMakeStatus.text = getString(R.string.can_make_now)
                tvCanMakeStatus.setTextColor(ContextCompat.getColor(this@RecipeDetailActivity, R.color.status_normal))
                btnStartCooking.isEnabled = true
                btnStartCooking.alpha = 1.0f
            } else {
                val missingCount = missing.count { it.isEssential }
                if (missingCount > 0) {
                    tvCanMakeStatus.text = getString(R.string.missing_ingredients_format, missingCount)
                    tvCanMakeStatus.setTextColor(ContextCompat.getColor(this@RecipeDetailActivity, R.color.status_expired))
                    btnStartCooking.isEnabled = false
                    btnStartCooking.alpha = 0.5f
                } else {
                    tvCanMakeStatus.text = getString(R.string.can_make_optional_missing)
                    tvCanMakeStatus.setTextColor(ContextCompat.getColor(this@RecipeDetailActivity, R.color.status_warning))
                    btnStartCooking.isEnabled = true
                    btnStartCooking.alpha = 1.0f
                }
            }
        }
    }
    
    private fun setupTags(tags: List<String>) {
        binding.chipGroupTags.removeAllViews()
        
        for (tag in tags) {
            val chip = Chip(this).apply {
                text = tag
                isClickable = false
                isCheckable = false
                chipBackgroundColor = ContextCompat.getColorStateList(this@RecipeDetailActivity, R.color.category_chip_background)
                setTextColor(ContextCompat.getColor(this@RecipeDetailActivity, R.color.text_primary))
            }
            binding.chipGroupTags.addView(chip)
        }
    }
    
    private fun setupSteps(steps: List<String>) {
        binding.layoutSteps.removeAllViews()
        
        for ((index, step) in steps.withIndex()) {
            val stepView = layoutInflater.inflate(
                R.layout.item_recipe_step,
                binding.layoutSteps,
                false
            )
            
            val tvStepNumber = stepView.findViewById<TextView>(R.id.tvStepNumber)
            val tvStepContent = stepView.findViewById<TextView>(R.id.tvStepContent)
            
            tvStepNumber.text = (index + 1).toString()
            tvStepContent.text = step
            
            binding.layoutSteps.addView(stepView)
        }
    }
    
    private fun toggleFavorite() {
        isFavorite = !isFavorite
        updateFavoriteButton()
        
        // 保存收藏状态到本地存储
        getSharedPreferences("recipe_favorites", MODE_PRIVATE)
            .edit()
            .putBoolean("recipe_$recipeId", isFavorite)
            .apply()
        
        val message = if (isFavorite) {
            R.string.added_to_favorites
        } else {
            R.string.removed_from_favorites
        }
        com.google.android.material.snackbar.Snackbar.make(binding.root, message, com.google.android.material.snackbar.Snackbar.LENGTH_SHORT).show()
    }
    
    private fun updateFavoriteButton() {
        // 从本地存储读取收藏状态
        isFavorite = getSharedPreferences("recipe_favorites", MODE_PRIVATE)
            .getBoolean("recipe_$recipeId", false)
        
        val icon = if (isFavorite) {
            R.drawable.ic_favorite_filled
        } else {
            R.drawable.ic_favorite_border
        }
        binding.fabFavorite.setImageResource(icon)
    }
    
    private fun startCookingMode() {
        recipe?.let {
            startActivity(Intent(this, CookingModeActivity::class.java))
        }
    }
    
    private fun shareRecipe() {
        recipe?.let { r ->
            val shareText = buildString {
                appendLine("🍳 ${r.name}")
                appendLine()
                r.description?.let { appendLine(it); appendLine() }
                appendLine("⏱ 烹饪时间：${r.cookingTime}分钟")
                appendLine("🍽 份量：${r.servings}人份")
                appendLine("📊 难度：${getDifficultyString(r.difficulty)}")
                appendLine()
                appendLine("📝 食材：")
                r.ingredients.forEach { 
                    appendLine("• ${it.name} ${it.amount}")
                }
                appendLine()
                appendLine("👨‍🍳 步骤：")
                r.steps.forEachIndexed { index, step ->
                    appendLine("${index + 1}. $step")
                }
            }
            
            val shareIntent = Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
                putExtra(Intent.EXTRA_SUBJECT, getString(R.string.share_recipe_subject, r.name))
                putExtra(Intent.EXTRA_TEXT, shareText)
            }
            
            startActivity(Intent.createChooser(shareIntent, getString(R.string.share_via)))
        }
    }
    
    private fun getDifficultyString(difficulty: Difficulty): String {
        return when (difficulty) {
            Difficulty.EASY -> getString(R.string.difficulty_easy)
            Difficulty.MEDIUM -> getString(R.string.difficulty_medium)
            Difficulty.HARD -> getString(R.string.difficulty_hard)
        }
    }
    
    private fun getDifficultyColor(difficulty: Difficulty): Int {
        return when (difficulty) {
            Difficulty.EASY -> R.color.difficulty_easy
            Difficulty.MEDIUM -> R.color.difficulty_medium
            Difficulty.HARD -> R.color.difficulty_hard
        }
    }
    
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressedDispatcher.onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
    
    companion object {
        const val EXTRA_RECIPE_ID = "recipe_id"
    }
}

// 烹饪模式Activity（简化版，仅用于编译通过）
class CookingModeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 实际实现需要创建对应的布局文件
    }
    
    companion object {
        const val EXTRA_RECIPE = "recipe"
    }
}
