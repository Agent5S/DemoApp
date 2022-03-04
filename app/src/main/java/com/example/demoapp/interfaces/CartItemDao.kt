package com.example.demoapp.interfaces

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.demoapp.models.CartItem

@Dao
interface CartItemDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: CartItem)
    @Update
    suspend fun update(item: CartItem)
    @Delete
    suspend fun delete(item: CartItem)
    @Query("DELETE FROM CartItem WHERE productId = :productId")
    suspend fun deleteByProduct(productId: Int)
    @Query("SELECT * FROM CartItem WHERE productId = :productId")
    fun findByProduct(productId: Int): LiveData<CartItem?>
    @Query("SELECT * FROM CartItem")
    fun findAll(): LiveData<List<CartItem>>
}