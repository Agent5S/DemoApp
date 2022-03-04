package com.example.demoapp.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.example.demoapp.models.Category
import com.example.demoapp.repositories.RepositoryResult
import com.example.demoapp.repositories.CategoryRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ShopViewModel(): ViewModel() {
    fun getCategories(fresh: Boolean = true) = CategoryRepository.global.getCategories(fresh)
    fun getCategory(id: Int, fresh: Boolean = true) = CategoryRepository.global.getCategory(id, fresh)
    fun getCategories(parentId: Int, fresh: Boolean = true) = CategoryRepository.global.getCategories(parentId, fresh)
}