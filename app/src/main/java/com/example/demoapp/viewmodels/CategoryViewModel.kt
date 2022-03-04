package com.example.demoapp.viewmodels

import androidx.lifecycle.ViewModel
import com.example.demoapp.repositories.CategoryRepository
import com.example.demoapp.repositories.ProductRepository

class CategoryViewModel : ViewModel() {
    fun getCategory(id: Int, fresh: Boolean = true) = CategoryRepository.global.getCategory(id, fresh)
    fun getSubCategories(parentId: Int, fresh: Boolean = true) = CategoryRepository.global.getCategories(parentId, fresh)
    fun getProducts(parentId: Int, fresh: Boolean = true) = ProductRepository.global.getProducts(parentId, fresh)

}
