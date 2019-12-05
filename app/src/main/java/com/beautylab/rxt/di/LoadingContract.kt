package com.beautylab.rxt.di

import android.content.Context
import com.beautylab.rxt.models.ArticlesModel

interface LoadingContract {
    interface View {
        fun setArtictesViewModel(it: ArticlesModel)
        fun changeIcon(name: String)
        fun startNewActivity(value: Boolean)
    }

    interface Presenter{
        fun getRecipesFromServer()
        fun hasConnection(context: Context): Boolean
        fun initFirebsae()
        fun checkAuditoryParameters()
        fun checkIconChange()
        fun checkCountry(mCountry: String): Boolean
        fun setSubscribeToTopic()
        fun disposeDisposable()
    }
}