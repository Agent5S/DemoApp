package com.example.demoapp.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.example.demoapp.App
import com.example.demoapp.models.FeaturedList
import com.example.demoapp.models.api
import java.io.File

class FeaturedRepository {
    fun getFeatured(fresh: Boolean = true): LiveData<RepositoryResult<List<FeaturedList>>> = liveData {
        val fileName = "featured.json"
        val cacheValue = desearializeListFromCache<FeaturedList>(fileName)
        emit(RepositoryResult.Cached(cacheValue ?: listOf()))


        if (cacheValue == null || fresh) {
            repositoryCall(api::getFeaturedLists).let {
                if (it is RepositoryResult.Fresh) {
                    serializeListToCache(it.value, fileName)
                }

                emit(it)
            }
        }
    }

    fun clearCache() {
        val cacheFolder = App.context.cacheDir
        val cacheFile = File(cacheFolder, "featured.json")
        cacheFile.delete()
    }

    companion object {
        val global = FeaturedRepository()
    }
}