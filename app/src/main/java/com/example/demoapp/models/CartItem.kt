package com.example.demoapp.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CartItem(
    @PrimaryKey
    val productId: Int,
    val amount: Int
)