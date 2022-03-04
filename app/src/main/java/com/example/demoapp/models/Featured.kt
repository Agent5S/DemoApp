package com.example.demoapp.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class FeaturedList(
    val title: String,
    val items: List<Featured>
)

@JsonClass(generateAdapter = true)
data class Featured(
    val name: String,
    val imageURL: String,
    val deepLink: String?
)
