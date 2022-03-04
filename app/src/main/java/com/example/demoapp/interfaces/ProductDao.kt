package com.example.demoapp.interfaces

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.demoapp.models.Product

@Dao
interface ProductDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: Product)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(items: List<Product>)
    @Update
    suspend fun update(item: Product)
    @Delete
    suspend fun delete(item: Product)
    @Query("DELETE FROM Product WHERE id = :id")
    suspend fun deleteById(id: Int)
    @Query("DELETE FROM Product")
    suspend fun deleteAll()
    @Query("DELETE FROM Product WHERE parentId = :parentId")
    suspend fun deleteAllByParent(parentId: Int)
    @Query("SELECT * FROM Product WHERE id = :id")
    suspend fun findById(id: Int): Product?
    @Query("SELECT * FROM Product where parentId = :parentId")
    suspend fun findAllByParent(parentId: Int): List<Product>
}