package com.fridge.app.ui.fragment

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.fridge.app.R
import com.fridge.app.data.model.Ingredient
import com.fridge.app.data.model.IngredientCategory
import com.fridge.app.databinding.DialogAddIngredientBinding
import com.fridge.app.databinding.FragmentIngredientListBinding
import com.fridge.app.ui.adapter.IngredientAdapter
import com.fridge.app.ui.viewmodel.IngredientViewModel
import com.google.android.material.chip.Chip
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import java.util.*

class IngredientListFragment : Fragment() {

    private var _binding: FragmentIngredientListBinding? = null
    private val binding get() = _binding!!

    private val viewModel: IngredientViewModel by viewModels()
    private lateinit var adapter: IngredientAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentIngredientListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        setupObservers()
        setupSearch()
        setupFilters()
    }

    private fun setupRecyclerView() {
        adapter = IngredientAdapter(
            onItemClick = { ingredient ->
                // 显示食材详情
                showIngredientDetail(ingredient)
            },
            onEditClick = { ingredient ->
                showEditIngredientDialog(ingredient)
            },
            onDeleteClick = { ingredient ->
                showDeleteConfirmDialog(ingredient)
            }
        )

        binding.recyclerViewIngredients.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = this@IngredientListFragment.adapter
        }
    }

    private fun setupObservers() {
        viewModel.allIngredients.observe(viewLifecycleOwner) { ingredients ->
            adapter.submitList(ingredients)
            updateStats(ingredients)

            // 显示/隐藏空视图
            if (ingredients.isEmpty()) {
                binding.emptyView.visibility = View.VISIBLE
                binding.recyclerViewIngredients.visibility = View.GONE
            } else {
                binding.emptyView.visibility = View.GONE
                binding.recyclerViewIngredients.visibility = View.VISIBLE
            }
        }
    }

    private fun setupSearch() {
        binding.searchView.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                val query = newText ?: ""
                viewModel.searchIngredients(query).observe(viewLifecycleOwner) { results ->
                    adapter.submitList(results)
                }
                return true
            }
        })
    }

    private fun setupFilters() {
        binding.chipGroupFilter.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.chipAll -> {
                    viewModel.allIngredients.observe(viewLifecycleOwner) { adapter.submitList(it) }
                }
                R.id.chipVegetable -> filterByCategory(IngredientCategory.VEGETABLE)
                R.id.chipMeat -> filterByCategory(IngredientCategory.MEAT)
                R.id.chipFruit -> filterByCategory(IngredientCategory.FRUIT)
                R.id.chipDairy -> filterByCategory(IngredientCategory.DAIRY)
            }
        }
    }

    private fun filterByCategory(category: IngredientCategory) {
        viewModel.getIngredientsByCategory(category).observe(viewLifecycleOwner) { ingredients ->
            adapter.submitList(ingredients)
        }
    }

    private fun updateStats(ingredients: List<Ingredient>) {
        binding.tvTotalCount.text = ingredients.size.toString()

        val expiringCount = ingredients.count { it.getStatus() == com.fridge.app.data.model.IngredientStatus.EXPIRING_SOON }
        val expiredCount = ingredients.count { it.getStatus() == com.fridge.app.data.model.IngredientStatus.EXPIRED }

        binding.tvExpiringCount.text = expiringCount.toString()
        binding.tvExpiredCount.text = expiredCount.toString()
    }

    fun showAddIngredientDialog() {
        showIngredientDialog(null)
    }

    private fun showEditIngredientDialog(ingredient: Ingredient) {
        showIngredientDialog(ingredient)
    }

    private fun showIngredientDialog(ingredient: Ingredient?) {
        val dialogBinding = DialogAddIngredientBinding.inflate(layoutInflater)
        val isEdit = ingredient != null

        // 设置分类下拉框
        val categories = IngredientCategory.values().map { getCategoryDisplayName(it) }
        val categoryAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, categories)
        dialogBinding.spinnerCategory.setAdapter(categoryAdapter)

        // 如果是编辑，填充现有数据
        ingredient?.let { ing ->
            dialogBinding.etIngredientName.setText(ing.name)
            dialogBinding.etQuantity.setText(ing.quantity.toString())
            dialogBinding.etUnit.setText(ing.unit)
            dialogBinding.etNotes.setText(ing.notes)
            dialogBinding.spinnerCategory.setText(getCategoryDisplayName(ing.category), false)
            ing.expireDate?.let { date ->
                val calendar = Calendar.getInstance().apply { time = date }
                dialogBinding.etExpireDate.setText(
                    String.format("%04d-%02d-%02d", 
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH) + 1,
                        calendar.get(Calendar.DAY_OF_MONTH))
                )
            }
        }

        // 日期选择器
        dialogBinding.etExpireDate.setOnClickListener {
            val calendar = Calendar.getInstance()
            DatePickerDialog(requireContext(), { _, year, month, day ->
                dialogBinding.etExpireDate.setText(String.format("%04d-%02d-%02d", year, month + 1, day))
            }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show()
        }

        MaterialAlertDialogBuilder(requireContext())
            .setTitle(if (isEdit) R.string.edit_ingredient else R.string.add_ingredient)
            .setView(dialogBinding.root)
            .setPositiveButton(R.string.save) { _, _ ->
                val name = dialogBinding.etIngredientName.text.toString()
                val quantityStr = dialogBinding.etQuantity.text.toString()
                val unit = dialogBinding.etUnit.text.toString()
                val categoryName = dialogBinding.spinnerCategory.text.toString()
                val expireDateStr = dialogBinding.etExpireDate.text.toString()
                val notes = dialogBinding.etNotes.text.toString()

                if (name.isBlank() || quantityStr.isBlank()) {
                    Toast.makeText(requireContext(), R.string.please_fill_required, Toast.LENGTH_SHORT).show()
                    return@setPositiveButton
                }

                val quantity = quantityStr.toFloatOrNull() ?: 1f
                val category = getCategoryFromDisplayName(categoryName) ?: IngredientCategory.OTHER

                val expireDate = if (expireDateStr.isNotBlank()) {
                    val parts = expireDateStr.split("-")
                    Calendar.getInstance().apply {
                        set(parts[0].toInt(), parts[1].toInt() - 1, parts[2].toInt())
                    }.time
                } else null

                val newIngredient = Ingredient(
                    id = ingredient?.id ?: 0,
                    name = name,
                    category = category,
                    quantity = quantity,
                    unit = unit.ifBlank { "个" },
                    addDate = ingredient?.addDate ?: Date(),
                    expireDate = expireDate,
                    notes = notes.ifBlank { null }
                )

                if (isEdit) {
                    viewModel.update(newIngredient)
                } else {
                    viewModel.insert(newIngredient)
                }
            }
            .setNegativeButton(R.string.cancel, null)
            .show()
    }

    private fun showDeleteConfirmDialog(ingredient: Ingredient) {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(R.string.confirm_delete)
            .setMessage(getString(R.string.delete_ingredient_confirm, ingredient.name))
            .setPositiveButton(R.string.delete) { _, _ ->
                viewModel.delete(ingredient)
            }
            .setNegativeButton(R.string.cancel, null)
            .show()
    }

    private fun showIngredientDetail(ingredient: Ingredient) {
        // 实现详情展示
    }

    private fun getCategoryDisplayName(category: IngredientCategory): String {
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

    private fun getCategoryFromDisplayName(name: String): IngredientCategory? {
        return when (name) {
            "蔬菜" -> IngredientCategory.VEGETABLE
            "水果" -> IngredientCategory.FRUIT
            "肉类" -> IngredientCategory.MEAT
            "海鲜" -> IngredientCategory.SEAFOOD
            "蛋类" -> IngredientCategory.EGG
            "乳制品" -> IngredientCategory.DAIRY
            "米面" -> IngredientCategory.GRAIN
            "调料" -> IngredientCategory.SEASONING
            "罐头" -> IngredientCategory.CANNED
            "冷冻" -> IngredientCategory.FROZEN
            "其他" -> IngredientCategory.OTHER
            else -> null
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}