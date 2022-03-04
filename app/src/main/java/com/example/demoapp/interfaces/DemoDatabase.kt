package com.example.demoapp.interfaces

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.demoapp.models.CartItem
import com.example.demoapp.models.Category
import com.example.demoapp.models.Product

@Database(entities = [Category::class, Product::class, CartItem::class], version = 1)
abstract class DemoDatabase: RoomDatabase() {
    abstract fun categoryDao(): CategoryDao
    abstract fun productDao(): ProductDao
    abstract fun cartItemDao(): CartItemDao
}