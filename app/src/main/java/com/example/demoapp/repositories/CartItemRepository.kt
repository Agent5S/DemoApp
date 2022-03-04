package com.example.demoapp.repositories

import androidx.lifecycle.LiveData
import com.example.demoapp.models.CartItem
import com.example.demoapp.models.database

class CartItemRepository {
    fun getCartItems(): LiveData<List<CartItem>> = database.cartItemDao().findAll()
    fun getCartItem(productId: Int): LiveData<CartItem?> = database.cartItemDao().findByProduct(productId)

    companion object {
        val global = CartItemRepository()
    }
}