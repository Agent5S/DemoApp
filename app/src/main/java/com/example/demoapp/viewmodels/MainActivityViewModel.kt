package com.example.demoapp.viewmodels

import androidx.lifecycle.ViewModel
import com.example.demoapp.models.CartItem
import com.example.demoapp.repositories.CartItemRepository

class MainActivityViewModel: ViewModel() {
    fun getCartItems() = CartItemRepository.global.getCartItems()
}