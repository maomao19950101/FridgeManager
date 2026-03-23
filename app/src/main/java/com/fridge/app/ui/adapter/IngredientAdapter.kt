package com.fridge.app.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.fridge.app.R
import com.fridge.app.data.model.Ingredient
import com.fridge.app.data.model.IngredientCategory
import com.fridge.app.data.model.IngredientStatus
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

class IngredientAdapter(
    private val onItemClick: (Ingredient) -> Unit,
    private val onEditClick: (Ingredient) -> Unit,
    private val onDeleteClick: (Ingredient) -> Unit
) : ListAdapter<Ingredient, IngredientAdapter.IngredientViewHolder>(IngredientDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngredientViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_ingredient, parent, false)
        return IngredientViewHolder(view)
    }

    override fun onBindViewHolder(holder: IngredientViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class IngredientViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val statusIndicator: View = itemView.findViewById(R.id.statusIndicator)
        private val ivPhoto: ImageView = itemView.findViewById(R.id.ivIngredientPhoto)
        private val tvName: TextView = itemView.findViewById(R.id.tvIngredientName)
        private val tvCategory: TextView = itemView.findViewById(R.id.tvCategory)
        private val tvQuantity: TextView = itemView.findViewById(R.id.tvQuantity)
        private val tvDaysLeft: TextView = itemView.findViewById(R.id.tvDaysLeft)
        private val tvExpireDate: TextView = itemView.findViewById(R.id.tvExpireDate)
        private val tvAddDate: TextView = itemView.findViewById(R.id.tvAddDate)
        private val btnMore: ImageButton = itemView.findViewById(R.id.btnMore)

        fun bind(ingredient: Ingredient) {
            tvName.text = ingredient.name
            tvCategory.text = getCategoryName(ingredient.category)
            tvQuantity.text = "${ingredient.quantity.toInt()}${ingredient.unit}"

            // 设置状态指示器颜色
            val status = ingredient.getStatus()
            statusIndicator.setBackgroundColor(getStatusColor(status))

            // 设置剩余天数显示
            val daysUntilExpire = ingredient.getDaysUntilExpire()
            when {
                daysUntilExpire == null -> {
                    tvDaysLeft.text = "无期限"
                    tvDaysLeft.setBackgroundResource(R.drawable.bg_days_left_normal)
                }
                daysUntilExpire < 0 -> {
                    tvDaysLeft.text = "已过期"
                    tvDaysLeft.setBackgroundResource(R.drawable.bg_days_left_expired)
                }
                daysUntilExpire == 0 -> {
                    tvDaysLeft.text = "今天"
                    tvDaysLeft.setBackgroundResource(R.drawable.bg_days_left_expiring)
                }
                daysUntilExpire == 1 -> {
                    tvDaysLeft.text = "1天"
                    tvDaysLeft.setBackgroundResource(R.drawable.bg_days_left_expiring)
                }
                else -> {
                    tvDaysLeft.text = "${daysUntilExpire}天"
                    tvDaysLeft.setBackgroundResource(
                        if (daysUntilExpire <= 3) R.drawable.bg_days_left_warning
                        else R.drawable.bg_days_left_normal
                    )
                }
            }

            // 过期日期
            ingredient.expireDate?.let {
                val dateFormat = SimpleDateFormat("MM-dd", Locale.getDefault())
                tvExpireDate.text = dateFormat.format(it)
            } ?: run {
                tvExpireDate.text = "--"
            }

            // 添加日期
            val daysAgo = TimeUnit.MILLISECONDS.toDays(
                System.currentTimeMillis() - ingredient.addDate.time
            )
            tvAddDate.text = when {
                daysAgo == 0L -> "今天添加"
                daysAgo == 1L -> "昨天添加"
                else -> "${daysAgo}天前添加"
            }

            // 分类背景色
            tvCategory.setBackgroundColor(getCategoryColor(ingredient.category))

            // 点击事件
            itemView.setOnClickListener { onItemClick(ingredient) }

            // 更多菜单
            btnMore.setOnClickListener { view ->
                showPopupMenu(view, ingredient)
            }
        }

        private fun showPopupMenu(view: View, ingredient: Ingredient) {
            val popup = PopupMenu(view.context, view)
            popup.menuInflater.inflate(R.menu.menu_ingredient_item, popup.menu)
            popup.setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.action_edit -> {
                        onEditClick(ingredient)
                        true
                    }
                    R.id.action_delete -> {
                        onDeleteClick(ingredient)
                        true
                    }
                    else -> false
                }
            }
            popup.show()
        }
    }

    private fun getCategoryName(category: IngredientCategory): String {
        return when (category) {
            IngredientCategory.VEGETABLE -> "蔬菜"
            IngredientCategory.FRUIT -> "水果"
            IngredientCategory.MEAT -> "肉类"
            IngredientCategory.SEAFOOD -> "海鲜"
            IngredientCategory.EGG -> "蛋类"
            IngredientCategory.DAIRY -> "乳制品"
            IngredientCategory.GRAIN -> "米面"
            IngredientCategory.SEASONING -> "调料"
            IngredientCategory.CANNED -> "罐头"
            IngredientCategory.FROZEN -> "冷冻"
            IngredientCategory.OTHER -> "其他"
        }
    }

    private fun getCategoryColor(category: IngredientCategory): Int {
        // 返回分类颜色，实际项目中应从 resources 获取
        return when (category) {
            IngredientCategory.VEGETABLE -> 0xFF4CAF50.toInt()
            IngredientCategory.FRUIT -> 0xFFFF9800.toInt()
            IngredientCategory.MEAT -> 0xFFE91E63.toInt()
            IngredientCategory.SEAFOOD -> 0xFF03A9F4.toInt()
            IngredientCategory.EGG -> 0xFFFFEB3B.toInt()
            IngredientCategory.DAIRY -> 0xFF9C27B0.toInt()
            IngredientCategory.GRAIN -> 0xFF795548.toInt()
            IngredientCategory.SEASONING -> 0xFF607D8B.toInt()
            IngredientCategory.CANNED -> 0xFFFF5722.toInt()
            IngredientCategory.FROZEN -> 0xFF2196F3.toInt()
            IngredientCategory.OTHER -> 0xFF9E9E9E.toInt()
        }
    }

    private fun getStatusColor(status: IngredientStatus): Int {
        return when (status) {
            IngredientStatus.NORMAL -> 0xFF4CAF50.toInt()
            IngredientStatus.WARNING -> 0xFFFFC107.toInt()
            IngredientStatus.EXPIRING_SOON -> 0xFFFF9800.toInt()
            IngredientStatus.EXPIRED -> 0xFFF44336.toInt()
        }
    }

    class IngredientDiffCallback : DiffUtil.ItemCallback<Ingredient>() {
        override fun areItemsTheSame(oldItem: Ingredient, newItem: Ingredient): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Ingredient, newItem: Ingredient): Boolean {
            return oldItem == newItem
        }
    }
}