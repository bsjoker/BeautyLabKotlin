package com.beautylab.rxt.di

import com.beautylab.rxt.models.RecipeModelForRV

interface CatalogContract {
    interface View {
        fun setDataToRV(groupsForRV: ArrayList<RecipeModelForRV>)
        fun startNewActivity(pos: Int)

    }

    interface Presenter {
        fun fillDataForRV()
        fun clickToItem(pos: Int)

    }
}