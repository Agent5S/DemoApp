package com.example.demoapp.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.demoapp.models.FeaturedList
import com.example.demoapp.repositories.RepositoryResult
import com.example.demoapp.repositories.FeaturedRepository
import kotlinx.coroutines.Dispatchers

class HomeViewModel(): ViewModel() {

    fun getList(fresh: Boolean = true) = FeaturedRepository.global.getFeatured(fresh)
}