package com.example.demoapp.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.demoapp.models.Product
import com.example.demoapp.repositories.CartItemRepository
import com.example.demoapp.repositories.RepositoryResult
import com.example.demoapp.repositories.ProductRepository

class ProductViewModel : ViewModel() {
    fun getProduct(id: Int, fresh: Boolean = true) = ProductRepository.global.getProduct(id, fresh)
    fun getCartItem(productId: Int) = CartItemRepository.global.getCartItem(productId)
}
