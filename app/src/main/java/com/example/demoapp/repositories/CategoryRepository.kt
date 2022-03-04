package com.example.demoapp.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.example.demoapp.models.Category
import com.example.demoapp.models.api
import com.example.demoapp.models.database

class CategoryRepository {
    fun getCategories(fresh: Boolean = true): LiveData<RepositoryResult<List<Category>>> = liveData {
        val cacheValue = database.categoryDao().findAll()
        emit(RepositoryResult.Cached(cacheValue))

        if (cacheValue.isEmpty() || fresh) {
            repositoryCall(api::getCategories).let {
                when(it) {
                    is RepositoryResult.Fresh -> database.categoryDao().insert(it.value)
                    is RepositoryResult.Error -> {
                        if(it.code == 404) {
                            database.categoryDao().deleteAll()
                        }
                    }
                }

                emit(it)
            }
        }
    }

    fun getCategory(id: Int, fresh: Boolean = true): LiveData<RepositoryResult<Category?>> = liveData {
        val cacheValue = database.categoryDao().findById(id)
        emit(RepositoryResult.Cached(cacheValue))

        if (cacheValue == null || fresh) {
            repositoryCall { api.getCategoryById(id) }.let {
                when(it) {
                    is RepositoryResult.Fresh -> database.categoryDao().insert(it.value)
                    is RepositoryResult.Error -> {
                        if (it.code == 404) {
                            database.categoryDao().deleteById(id)
                        }
                    }
                }

                emit(it)
            }
        }
    }

    fun getCategories(parentId: Int, fresh: Boolean = true): LiveData<RepositoryResult<List<Category>>> = liveData {
        val cacheValue = database.categoryDao().findAllByParent(parentId)
        emit(RepositoryResult.Cached(cacheValue))

        if (cacheValue.isEmpty() || fresh) {
            repositoryCall { api.getCategoriesByParent(parentId) }.let {
                when(it) {
                    is RepositoryResult.Fresh -> database.categoryDao().insert(it.value)
                    is RepositoryResult.Error -> {
                        if(it.code == 404) {
                            database.categoryDao().deleteAllByParent(parentId)
                        }
                    }
                }

                emit(it)
            }
        }
    }

    companion object {
        val global = CategoryRepository()
    }
}