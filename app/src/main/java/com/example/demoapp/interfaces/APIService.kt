package com.example.demoapp.interfaces

import com.example.demoapp.models.Category
import com.example.demoapp.models.FeaturedList
import com.example.demoapp.models.Product
import retrofit2.http.GET
import retrofit2.http.Path

interface APIService {
    @GET("/featured")
    suspend fun getFeaturedLists(): List<FeaturedList>

    //TODO replace with real path (/categories) before publishing
    @GET("/categories/index.json")
    suspend fun getCategories(): List<Category>

    @GET("/categories/{id}")
    suspend fun getCategoryById(@Path("id") id: Int): Category

    //TODO move parentId to query parameters
    @GET("/categories/{parentId}")
    suspend fun getCategoriesByParent(@Path("parentId") parentId: Int): List<Category>

    //TODO move path to /products and parentId to query parameters
    @GET("/categories/{parentId}")
    suspend fun getProductsByParent(@Path("parentId") parentId: Int): List<Product>

    @GET("/products/{id}")
    suspend fun getProductById(@Path("id") id: Int): Product
}