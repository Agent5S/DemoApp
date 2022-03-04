package com.example.demoapp.repositories

import com.squareup.moshi.Types
import com.example.demoapp.App
import com.example.demoapp.utility.Result
import com.example.demoapp.models.moshi
import com.example.demoapp.utility.safeAPICall
import kotlinx.coroutines.Dispatchers
import java.io.BufferedReader
import java.io.File

sealed class RepositoryResult<out T>() {
    class Cached<T>(val value: T): RepositoryResult<T>()
    class Fresh<T>(val value: T): RepositoryResult<T>()
    class Error(val code: Int? = null): RepositoryResult<Nothing>()
}

suspend fun<T> repositoryCall(call: suspend () -> T): RepositoryResult<T> {
    val result =
        safeAPICall(Dispatchers.IO, call)
    return when(result) {
        is Result.Success -> RepositoryResult.Fresh(result.value)
        else -> RepositoryResult.Error((result as? Result.GenericError)?.code)
    }

}

inline fun<reified T> desearializeListFromCache(fileName: String): List<T>? {
    val type = Types.newParameterizedType(List::class.java, T::class.java)
    val adapter = moshi.adapter<List<T>>(type)

    var cacheValue: List<T>? = null
    val cacheFolder = App.context.cacheDir
    val cacheFile = File(cacheFolder, fileName)
    if (cacheFile.exists()) {
        val json = cacheFile.bufferedReader().use(BufferedReader::readText)
        cacheValue = adapter.fromJson(json)
    }

    return cacheValue
}

inline fun<reified T> serializeListToCache(list: List<T>, fileName: String) {
    val type = Types.newParameterizedType(List::class.java, T::class.java)
    val adapter = moshi.adapter<List<T>>(type)

    val cacheFolder = App.context.cacheDir
    val cacheFile = File(cacheFolder, fileName)
    cacheFile.parentFile.mkdirs()

    val json = adapter.toJson(list)
    cacheFile.bufferedWriter().use {
        it.write(json)
    }
}