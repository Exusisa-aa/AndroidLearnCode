package com.restaurant.app.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.restaurant.app.data.model.Category
import com.restaurant.app.data.model.Dish
import com.restaurant.app.data.repo.RestaurantRepository
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    private val repo = RestaurantRepository()
    private val _categories = MutableLiveData<List<Category>>()
    val categories: LiveData<List<Category>> = _categories
    private val _dishes = MutableLiveData<List<Dish>>()
    val dishes: LiveData<List<Dish>> = _dishes
    private val _loading = MutableLiveData<Boolean>(false)
    val loading: LiveData<Boolean> = _loading
    private var currentCategoryId: Long? = null
    fun loadInitial() {
        _loading.postValue(true)
        viewModelScope.launch {
            val cats = repo.loadCategories()
            _categories.postValue(cats)
            val firstId = cats.firstOrNull()?.id
            if (firstId != null) {
                currentCategoryId = firstId
                val ds = repo.loadDishes(firstId)
                _dishes.postValue(ds)
            } else {
                _dishes.postValue(emptyList())
            }
            _loading.postValue(false)
        }
    }
    fun onCategorySelected(categoryId: Long) {
        if (currentCategoryId == categoryId) return
        currentCategoryId = categoryId
        _loading.postValue(true)
        viewModelScope.launch {
            val ds = repo.loadDishes(categoryId)
            _dishes.postValue(ds)
            _loading.postValue(false)
        }
    }
}
