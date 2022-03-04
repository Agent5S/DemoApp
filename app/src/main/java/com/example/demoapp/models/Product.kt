package com.example.demoapp.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
@Entity
data class Product(
    @PrimaryKey
    val id: Int,
    val parentId: Int?,
    val name: String,
    val imageURL: String
)