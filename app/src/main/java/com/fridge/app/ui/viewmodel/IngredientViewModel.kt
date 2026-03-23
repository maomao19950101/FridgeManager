package com.fridge.app.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.fridge.app.data.db.AppDatabase
import com.fridge.app.data.model.Ingredient
import com.fridge.app.data.model.IngredientCategory
import com.fridge.app.data.model.IngredientStatus
import com.fridge.app.data.repository.IngredientRepository
import kotlinx.coroutines.launch

class IngredientViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: IngredientRepository
    val allIngredients: LiveData<List<Ingredient>>

    private val _searchResults = MutableLiveData<List<Ingredient>>()
    val searchResults: LiveData<List<Ingredient>> = _searchResults

    private val _expiringCount = MutableLiveData<Int>()
    val expiringCount: LiveData<Int> = _expiringCount

    private val _expiredCount = MutableLiveData<Int>()
    val expiredCount: LiveData<Int> = _expiredCount

    init {
        val ingredientDao = AppDatabase.getDatabase(application).ingredientDao()
        repository = IngredientRepository(ingredientDao)
        allIngredients = repository.allIngredients
        updateCounts()
    }

    fun insert(ingredient: Ingredient) = viewModelScope.launch {
        repository.insertIngredient(ingredient)
        updateCounts()
    }

    fun update(ingredient: Ingredient) = viewModelScope.launch {
        repository.updateIngredient(ingredient)
        updateCounts()
    }

    fun delete(ingredient: Ingredient) = viewModelScope.launch {
        repository.deleteIngredient(ingredient)
        updateCounts()
    }

    fun deleteById(id: Long) = viewModelScope.launch {
        repository.deleteIngredientById(id)
        updateCounts()
    }

    fun getIngredientById(id: Long): LiveData<Ingredient?> {
        val result = MutableLiveData<Ingredient?>()
        viewModelScope.launch {
            result.postValue(repository.getIngredientById(id))
        }
        return result
    }

    fun getIngredientsByCategory(category: IngredientCategory): LiveData<List<Ingredient>> {
        return repository.getIngredientsByCategory(category)
    }

    fun searchIngredients(query: String): LiveData<List<Ingredient>> {
        return if (query.isBlank()) {
            allIngredients
        } else {
            repository.searchIngredients(query)
        }
    }

    fun getExpiredIngredients(): LiveData<List<Ingredient>> {
        return repository.getExpiredIngredients()
    }

    fun getExpiringSoonIngredients(): LiveData<List<Ingredient>> {
        val result = MutableLiveData<List<Ingredient>>()
        viewModelScope.launch {
            result.postValue(repository.getExpiringIngredients(3))
        }
        return result
    }

    private fun updateCounts() = viewModelScope.launch {
        _expiringCount.postValue(repository.getExpiringSoonCount())
        _expiredCount.postValue(repository.getExpiredIngredients().value?.size ?: 0)
    }

    // 获取按状态分组的食材统计
    fun getStatusStats(ingredients: List<Ingredient>): Map<IngredientStatus, Int> {
        return ingredients.groupingBy { it.getStatus() }.eachCount()
    }

    // 清空所有食材
    fun deleteAll() = viewModelScope.launch {
        repository.deleteAllIngredients()
        updateCounts()
    }
}