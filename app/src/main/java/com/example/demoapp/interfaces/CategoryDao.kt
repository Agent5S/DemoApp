package com.example.demoapp.interfaces

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.demoapp.models.Category

@Dao
interface CategoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: Category)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(items: List<Category>)
    @Update
    suspend fun update(item: Category)
    @Delete
    suspend fun delete(item: Category)
    @Query("DELETE FROM Category WHERE id = :id")
    suspend fun deleteById(id: Int)
    @Query("DELETE FROM Category")
    suspend fun deleteAll()
    @Query("DELETE FROM Category WHERE parentId = :parentId")
    suspend fun deleteAllByParent(parentId: Int)
    @Query("SELECT * FROM Category")
    suspend fun  findAll(): List<Category>
    @Query("SELECT * FROM Category WHERE id = :id")
    suspend fun findById(id: Int): Category?
    @Query("SELECT * FROM Category WHERE parentId = :parentId")
    suspend fun findAllByParent(parentId: Int): List<Category>
}