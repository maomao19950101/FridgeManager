package com.fridge.app.ui.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.lifecycle.lifecycleScope
import com.fridge.app.R
import com.fridge.app.data.model.Ingredient
import com.fridge.app.data.model.IngredientStatus
import com.fridge.app.data.repository.IngredientRepository
import com.fridge.app.databinding.ActivityIngredientDetailBinding
import com.fridge.app.util.ImageUtils
import com.fridge.app.util.PhotoPicker
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

class IngredientDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityIngredientDetailBinding
    private lateinit var repository: IngredientRepository
    private lateinit var photoPicker: PhotoPicker
    
    private var ingredientId: Long = -1
    private var ingredient: Ingredient? = null
    private var tempPhotoUri: Uri? = null
    
    private val dateFormat = SimpleDateFormat("yyyy年MM月dd日", Locale.getDefault())
    private val editDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    
    private val photoPickerLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        photoPicker.handleActivityResult(result.resultCode, result.data) { uri ->
            uri?.let {
                updateIngredientPhoto(it)
            }
        }
    }
    
    private val editLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            loadIngredient()
            setResult(RESULT_OK)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityIngredientDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        repository = IngredientRepository(application)
        photoPicker = PhotoPicker(this)
        
        ingredientId = intent.getLongExtra(EXTRA_INGREDIENT_ID, -1)
        if (ingredientId == -1L) {
            finish()
            return
        }
        
        setupToolbar()
        setupListeners()
        loadIngredient()
    }
    
    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.title = getString(R.string.ingredient_detail)
    }
    
    private fun setupListeners() {
        binding.apply {
            // 照片点击
            cardPhoto.setOnClickListener {
                ingredient?.photoUri?.let { uri ->
                    previewImage(uri)
                } ?: run {
                    showPhotoPicker()
                }
            }
            
            // 更换照片
            btnChangePhoto.setOnClickListener {
                showPhotoPicker()
            }
            
            // 删除照片
            btnDeletePhoto.setOnClickListener {
                deletePhoto()
            }
            
            // 编辑按钮
            fabEdit.setOnClickListener {
                showEditDialog()
            }
            
            // 删除按钮
            fabDelete.setOnClickListener {
                showDeleteConfirmDialog()
            }
        }
    }
    
    private fun loadIngredient() {
        lifecycleScope.launch {
            ingredient = withContext(Dispatchers.IO) {
                repository.getById(ingredientId)
            }
            ingredient?.let { updateUI(it) }
        }
    }
    
    private fun updateUI(ingredient: Ingredient) {
        binding.apply {
            // 名称
            tvName.text = ingredient.name
            
            // 分类
            tvCategory.text = getCategoryString(ingredient.category)
            tvCategory.setChipBackgroundColorResource(getCategoryColor(ingredient.category))
            
            // 数量
            tvQuantity.text = "${ingredient.quantity} ${ingredient.unit}"
            
            // 状态
            val status = ingredient.getStatus()
            tvStatus.text = getStatusString(status)
            tvStatus.setChipBackgroundColorResource(getStatusColor(status))
            
            // 过期日期
            ingredient.expireDate?.let {
                tvExpireDate.text = dateFormat.format(it)
                val days = ingredient.getDaysUntilExpire()
                tvDaysLeft.text = when {
                    days == null -> ""
                    days < 0 -> getString(R.string.expired_days_ago, kotlin.math.abs(days))
                    days == 0 -> getString(R.string.expires_today)
                    days == 1 -> getString(R.string.expires_tomorrow)
                    else -> getString(R.string.days_left, days)
                }
                tvDaysLeft.setTextColor(getDaysLeftColor(days))
            } ?: run {
                tvExpireDate.text = getString(R.string.no_expire_date)
                tvDaysLeft.text = ""
            }
            
            // 添加日期
            tvAddDate.text = getString(R.string.added_on, dateFormat.format(ingredient.addDate))
            
            // 备注
            if (!ingredient.notes.isNullOrEmpty()) {
                tvNotes.text = ingredient.notes
                layoutNotes.visibility = View.VISIBLE
            } else {
                layoutNotes.visibility = View.GONE
            }
            
            // 照片
            loadPhoto(ingredient.photoUri)
        }
        
        updateTitle(ingredient.name)
    }
    
    private fun loadPhoto(photoUri: String?) {
        binding.apply {
            if (!photoUri.isNullOrEmpty()) {
                try {
                    val uri = Uri.parse(photoUri)
                    ivPhoto.setImageURI(uri)
                    ivPhoto.alpha = 1.0f
                    tvPhotoHint.visibility = View.GONE
                    btnDeletePhoto.visibility = View.VISIBLE
                    btnChangePhoto.text = getString(R.string.change_photo)
                } catch (e: Exception) {
                    showDefaultPhoto()
                }
            } else {
                showDefaultPhoto()
            }
        }
    }
    
    private fun showDefaultPhoto() {
        binding.apply {
            ivPhoto.setImageResource(R.drawable.ic_food)
            ivPhoto.alpha = 0.3f
            tvPhotoHint.visibility = View.VISIBLE
            btnDeletePhoto.visibility = View.GONE
            btnChangePhoto.text = getString(R.string.add_photo)
        }
    }
    
    private fun showPhotoPicker() {
        AlertDialog.Builder(this)
            .setTitle(R.string.select_photo)
            .setItems(arrayOf(
                getString(R.string.take_photo),
                getString(R.string.choose_from_gallery)
            )) { _, which ->
                when (which) {
                    0 -> takePhoto()
                    1 -> chooseFromGallery()
                }
            }
            .setNegativeButton(R.string.cancel, null)
            .show()
    }
    
    private fun takePhoto() {
        lifecycleScope.launch {
            val photoFile = ImageUtils.createImageFile(this@IngredientDetailActivity)
            tempPhotoUri = FileProvider.getUriForFile(
                this@IngredientDetailActivity,
                "${packageName}.fileprovider",
                photoFile
            )
            photoPicker.takePhoto(photoPickerLauncher, tempPhotoUri!!)
        }
    }
    
    private fun chooseFromGallery() {
        photoPicker.chooseFromGallery(photoPickerLauncher)
    }
    
    private fun updateIngredientPhoto(uri: Uri) {
        lifecycleScope.launch {
            ingredient?.let { ing ->
                val updated = ing.copy(photoUri = uri.toString())
                withContext(Dispatchers.IO) {
                    repository.update(updated)
                }
                this@IngredientDetailActivity.ingredient = updated
                loadPhoto(uri.toString())
                setResult(RESULT_OK)
                Snackbar.make(binding.root, R.string.photo_updated, Snackbar.LENGTH_SHORT).show()
            }
        }
    }
    
    private fun deletePhoto() {
        AlertDialog.Builder(this)
            .setTitle(R.string.confirm_delete_photo)
            .setMessage(R.string.delete_photo_confirm_message)
            .setPositiveButton(R.string.delete) { _, _ ->
                lifecycleScope.launch {
                    ingredient?.let { ing ->
                        // 删除原文件
                        ing.photoUri?.let { uriStr ->
                            ImageUtils.deleteImage(this@IngredientDetailActivity, uriStr)
                        }
                        
                        val updated = ing.copy(photoUri = null)
                        withContext(Dispatchers.IO) {
                            repository.update(updated)
                        }
                        this@IngredientDetailActivity.ingredient = updated
                        showDefaultPhoto()
                        setResult(RESULT_OK)
                    }
                }
            }
            .setNegativeButton(R.string.cancel, null)
            .show()
    }
    
    private fun showEditDialog() {
        val dialogBinding = com.fridge.app.databinding.DialogAddIngredientBinding.inflate(layoutInflater)
        
        ingredient?.let { ing ->
            dialogBinding.apply {
                etName.setText(ing.name)
                etQuantity.setText(ing.quantity.toString())
                etUnit.setText(ing.unit)
                etNotes.setText(ing.notes)
                
                ing.expireDate?.let {
                    etExpireDate.setText(editDateFormat.format(it))
                }
            }
        }
        
        AlertDialog.Builder(this)
            .setTitle(R.string.edit_ingredient)
            .setView(dialogBinding.root)
            .setPositiveButton(R.string.save) { _, _ ->
                saveChanges(dialogBinding)
            }
            .setNegativeButton(R.string.cancel, null)
            .show()
    }
    
    private fun saveChanges(dialogBinding: com.fridge.app.databinding.DialogAddIngredientBinding) {
        val name = dialogBinding.etName.text.toString().trim()
        val quantityStr = dialogBinding.etQuantity.text.toString().trim()
        val unit = dialogBinding.etUnit.text.toString().trim()
        val expireDateStr = dialogBinding.etExpireDate.text.toString().trim()
        val notes = dialogBinding.etNotes.text.toString().trim()
        
        if (name.isEmpty() || quantityStr.isEmpty() || unit.isEmpty()) {
            Toast.makeText(this, R.string.please_fill_required, Toast.LENGTH_SHORT).show()
            return
        }
        
        val quantity = quantityStr.toFloatOrNull() ?: 1f
        val expireDate = if (expireDateStr.isNotEmpty()) {
            try {
                editDateFormat.parse(expireDateStr)
            } catch (e: Exception) {
                null
            }
        } else null
        
        lifecycleScope.launch {
            ingredient?.let { ing ->
                val updated = ing.copy(
                    name = name,
                    quantity = quantity,
                    unit = unit,
                    expireDate = expireDate,
                    notes = if (notes.isEmpty()) null else notes
                )
                
                withContext(Dispatchers.IO) {
                    repository.update(updated)
                }
                
                this@IngredientDetailActivity.ingredient = updated
                updateUI(updated)
                setResult(RESULT_OK)
                Snackbar.make(binding.root, R.string.ingredient_updated, Snackbar.LENGTH_SHORT).show()
            }
        }
    }
    
    private fun showDeleteConfirmDialog() {
        ingredient?.let { ing ->
            AlertDialog.Builder(this)
                .setTitle(R.string.confirm_delete)
                .setMessage(getString(R.string.delete_ingredient_confirm, ing.name))
                .setPositiveButton(R.string.delete) { _, _ ->
                    deleteIngredient()
                }
                .setNegativeButton(R.string.cancel, null)
                .show()
        }
    }
    
    private fun deleteIngredient() {
        lifecycleScope.launch {
            ingredient?.let { ing ->
                // 删除照片文件
                ing.photoUri?.let { uriStr ->
                    ImageUtils.deleteImage(this@IngredientDetailActivity, uriStr)
                }
                
                withContext(Dispatchers.IO) {
                    repository.delete(ing)
                }
                
                Toast.makeText(this@IngredientDetailActivity, R.string.ingredient_deleted, Toast.LENGTH_SHORT).show()
                setResult(RESULT_OK)
                finish()
            }
        }
    }
    
    private fun previewImage(uri: String) {
        val intent = Intent(this, ImagePreviewActivity::class.java).apply {
            putExtra(ImagePreviewActivity.EXTRA_IMAGE_URI, uri)
            putExtra(ImagePreviewActivity.EXTRA_TITLE, ingredient?.name)
        }
        startActivity(intent)
    }
    
    private fun updateTitle(name: String) {
        supportActionBar?.title = name
    }
    
    private fun getCategoryString(category: com.fridge.app.data.model.IngredientCategory): String {
        return when (category) {
            com.fridge.app.data.model.IngredientCategory.VEGETABLE -> getString(R.string.category_vegetable)
            com.fridge.app.data.model.IngredientCategory.FRUIT -> getString(R.string.category_fruit)
            com.fridge.app.data.model.IngredientCategory.MEAT -> getString(R.string.category_meat)
            com.fridge.app.data.model.IngredientCategory.SEAFOOD -> getString(R.string.category_seafood)
            com.fridge.app.data.model.IngredientCategory.EGG -> getString(R.string.category_egg)
            com.fridge.app.data.model.IngredientCategory.DAIRY -> getString(R.string.category_dairy)
            com.fridge.app.data.model.IngredientCategory.GRAIN -> getString(R.string.category_grain)
            com.fridge.app.data.model.IngredientCategory.SEASONING -> getString(R.string.category_seasoning)
            com.fridge.app.data.model.IngredientCategory.CANNED -> getString(R.string.category_canned)
            com.fridge.app.data.model.IngredientCategory.FROZEN -> getString(R.string.category_frozen)
            com.fridge.app.data.model.IngredientCategory.OTHER -> getString(R.string.category_other)
        }
    }
    
    private fun getCategoryColor(category: com.fridge.app.data.model.IngredientCategory): Int {
        return when (category) {
            com.fridge.app.data.model.IngredientCategory.VEGETABLE -> R.color.category_vegetable
            com.fridge.app.data.model.IngredientCategory.FRUIT -> R.color.category_fruit
            com.fridge.app.data.model.IngredientCategory.MEAT -> R.color.category_meat
            com.fridge.app.data.model.IngredientCategory.SEAFOOD -> R.color.category_seafood
            com.fridge.app.data.model.IngredientCategory.EGG -> R.color.category_egg
            com.fridge.app.data.model.IngredientCategory.DAIRY -> R.color.category_dairy
            com.fridge.app.data.model.IngredientCategory.GRAIN -> R.color.category_grain
            com.fridge.app.data.model.IngredientCategory.SEASONING -> R.color.category_seasoning
            com.fridge.app.data.model.IngredientCategory.CANNED -> R.color.category_canned
            com.fridge.app.data.model.IngredientCategory.FROZEN -> R.color.category_frozen
            com.fridge.app.data.model.IngredientCategory.OTHER -> R.color.category_other
        }
    }
    
    private fun getStatusString(status: IngredientStatus): String {
        return when (status) {
            IngredientStatus.NORMAL -> getString(R.string.status_normal)
            IngredientStatus.WARNING -> getString(R.string.status_warning)
            IngredientStatus.EXPIRING_SOON -> getString(R.string.status_expiring_soon)
            IngredientStatus.EXPIRED -> getString(R.string.status_expired)
        }
    }
    
    private fun getStatusColor(status: IngredientStatus): Int {
        return when (status) {
            IngredientStatus.NORMAL -> R.color.status_normal
            IngredientStatus.WARNING -> R.color.status_warning
            IngredientStatus.EXPIRING_SOON -> R.color.status_expiring_soon
            IngredientStatus.EXPIRED -> R.color.status_expired
        }
    }
    
    private fun getDaysLeftColor(days: Int?): Int {
        if (days == null) return getColor(R.color.text_primary)
        return when {
            days < 0 -> getColor(R.color.status_expired)
            days <= 1 -> getColor(R.color.status_expiring_soon)
            days <= 3 -> getColor(R.color.status_warning)
            else -> getColor(R.color.status_normal)
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
        const val EXTRA_INGREDIENT_ID = "ingredient_id"
    }
}
