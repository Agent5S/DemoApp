package com.example.demoapp.models

import androidx.room.Room
import com.example.demoapp.App
import com.example.demoapp.interfaces.APIService
import com.example.demoapp.interfaces.DemoDatabase
import com.squareup.moshi.Moshi
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object Constants {
    const val BASE_URL = "http://192.168.0.7"
}
private val retrofit = Retrofit
    .Builder()
    .baseUrl(Constants.BASE_URL)
    .addConverterFactory(MoshiConverterFactory.create())
    .build()
val api = retrofit.create(APIService::class.java)

val database = Room
    .databaseBuilder(App.context, DemoDatabase::class.java, "demo-database")
    .build()

val moshi = Moshi.Builder().build()
