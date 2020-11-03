package com.beauty.lab.di

import android.content.Context
import com.beauty.lab.models.ArticlesModel

interface LoadingContract {
    interface View {
        fun setArtictesViewModel(it: ArticlesModel)
        fun checkConnection()
    }

    interface Presenter{
        fun getRecipesFromServer()
        fun hasConnection(context: Context): Boolean
        fun disposeDisposable()
    }
}