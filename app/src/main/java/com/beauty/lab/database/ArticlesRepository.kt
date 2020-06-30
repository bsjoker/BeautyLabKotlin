package com.beauty.lab.database

import androidx.lifecycle.LiveData
import com.beauty.lab.models.ArticlesModel

class ArticlesRepository(private val articlesDao: ArticlesDao) {
    val allArticles: LiveData<List<ArticlesModel>> = articlesDao.getAll()

    suspend fun insert(articlesModel: ArticlesModel) {
        articlesDao.insert(articlesModel)
    }
}