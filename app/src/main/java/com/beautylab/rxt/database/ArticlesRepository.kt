package com.beautylab.rxt.database

import androidx.lifecycle.LiveData
import com.beautylab.rxt.models.ArticlesModel

class ArticlesRepository(private val articlesDao: ArticlesDao) {
    val allArticles: LiveData<List<ArticlesModel>> = articlesDao.getAll()

    suspend fun insert(articlesModel: ArticlesModel) {
        articlesDao.insert(articlesModel)
    }
}