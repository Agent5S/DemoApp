package com.example.demoapp.viewmodels

import androidx.lifecycle.ViewModel
import com.example.demoapp.repositories.CartItemRepository

class CartListViewModel : ViewModel() {
    fun getCartItems() = CartItemRepository.global.getCartItems()
}
