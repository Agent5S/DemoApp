package com.example.demoapp.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.example.demoapp.models.Product
import com.example.demoapp.models.api
import com.example.demoapp.models.database

class ProductRepository {
    fun getProduct(id: Int, fresh: Boolean = true): LiveData<RepositoryResult<Product?>> = liveData {
        val cacheValue = database.productDao().findById(id)
        emit(RepositoryResult.Cached(cacheValue))

        if (cacheValue == null || fresh) {
            repositoryCall { api.getProductById(id) }.let {
                when(it) {
                    is RepositoryResult.Fresh -> database.productDao().insert(it.value)
                    is RepositoryResult.Error -> {
                        if (it.code ==  404) {
                            database.productDao().deleteById(id)
                        }
                    }
                }

                emit(it)
            }
        }
    }

    fun getProducts(parentId: Int, fresh: Boolean = true): LiveData<RepositoryResult<List<Product>>> = liveData {
        val cacheValue = database.productDao().findAllByParent(parentId)
        emit(RepositoryResult.Cached(cacheValue))

        if (cacheValue.isEmpty() || fresh) {
            repositoryCall { api.getProductsByParent(parentId) }.let {
                when(it) {
                    is RepositoryResult.Fresh -> database.productDao().insert(it.value)
                    is RepositoryResult.Error -> {
                        if (it.code == 4040) {
                            database.productDao().deleteAllByParent(parentId)
                        }
                    }
                }

                emit(it)
            }
        }
    }

    companion object {
        val global = ProductRepository()
    }
}