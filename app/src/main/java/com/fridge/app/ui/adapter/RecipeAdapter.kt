package com.fridge.app.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.fridge.app.R
import com.fridge.app.data.model.Difficulty
import com.fridge.app.data.model.Recipe
import com.fridge.app.data.model.RecipeMatchResult

class RecipeAdapter(
    private val onItemClick: (Recipe) -> Unit
) : ListAdapter<RecipeMatchResult, RecipeAdapter.RecipeViewHolder>(RecipeDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_recipe, parent, false)
        return RecipeViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    fun submitRecipeList(recipes: List<Recipe>) {
        submitList(recipes.map { RecipeMatchResult.canMake(it) })
    }

    inner class RecipeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val cardView = itemView as CardView
        private val tvDifficulty: TextView = itemView.findViewById(R.id.tvDifficulty)
        private val tvCookingTime: TextView = itemView.findViewById(R.id.tvCookingTime)
        private val tvRecipeName: TextView = itemView.findViewById(R.id.tvRecipeName)
        private val tvDescription: TextView = itemView.findViewById(R.id.tvDescription)
        private val tvCanMake: TextView = itemView.findViewById(R.id.tvCanMake)
        private val tvMissingIngredients: TextView = itemView.findViewById(R.id.tvMissingIngredients)

        fun bind(matchResult: RecipeMatchResult) {
            val recipe = matchResult.recipe
            tvRecipeName.text = recipe.name
            tvDescription.text = recipe.description.orEmpty()
            tvCookingTime.text = "${recipe.cookingTime} min"
            tvDifficulty.text = getDifficultyText(recipe.difficulty)
            tvDifficulty.setBackgroundResource(getDifficultyBackground(recipe.difficulty))

            if (matchResult.canMake) {
                tvCanMake.visibility = View.VISIBLE
                tvMissingIngredients.visibility = View.GONE
                cardView.alpha = 1.0f
            } else {
                tvCanMake.visibility = View.GONE
                val missing = matchResult.missingIngredients
                if (missing.isNotEmpty()) {
                    tvMissingIngredients.visibility = View.VISIBLE
                    val missingText = missing.take(2).joinToString(", ") { it.name }
                    val moreCount = missing.size - 2
                    tvMissingIngredients.text = if (moreCount > 0) {
                        "Missing: $missingText and $moreCount more"
                    } else {
                        "Missing: $missingText"
                    }
                } else {
                    tvMissingIngredients.visibility = View.GONE
                }
                cardView.alpha = if (matchResult.matchRate > 0.7f) 1.0f else 0.6f
            }

            itemView.setOnClickListener { onItemClick(recipe) }
        }

        private fun getDifficultyText(difficulty: Difficulty): String {
            return when (difficulty) {
                Difficulty.EASY -> "Easy"
                Difficulty.MEDIUM -> "Medium"
                Difficulty.HARD -> "Hard"
            }
        }

        private fun getDifficultyBackground(difficulty: Difficulty): Int {
            return when (difficulty) {
                Difficulty.EASY -> R.drawable.bg_difficulty_easy
                Difficulty.MEDIUM -> R.drawable.bg_difficulty_medium
                Difficulty.HARD -> R.drawable.bg_difficulty_hard
            }
        }
    }

    class RecipeDiffCallback : DiffUtil.ItemCallback<RecipeMatchResult>() {
        override fun areItemsTheSame(oldItem: RecipeMatchResult, newItem: RecipeMatchResult): Boolean {
            return oldItem.recipe.id == newItem.recipe.id
        }

        override fun areContentsTheSame(oldItem: RecipeMatchResult, newItem: RecipeMatchResult): Boolean {
            return oldItem == newItem
        }
    }
}
